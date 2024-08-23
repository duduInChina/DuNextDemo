package cn.fooltech.usbserial

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.core.content.ContextCompat
import com.dudu.usbserial.driver.UsbSerialPort
import com.dudu.usbserial.driver.UsbSerialProber
import com.dudu.usbserial.util.HexDump
import com.dudu.usbserial.util.SerialInputOutputManager
import java.io.IOException
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/07/29
 *     desc   : Usb链接统一管理类，处理粘包问题
 * </pre>
 */
class UsbSerialManager(
    private val context: Context,
    private val params: UsbSerialParams,
    private val receiver: (data: ArrayList<Byte>) -> Unit,
    private val message: (String) -> Unit
) {

    val TAG = "UsbSerialManager"

    companion object {
        const val ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION"
    }

    /**
     * 链接状态
     */
    var connected: Boolean = false

    /**
     * 链式队列，队列容量不足或为0时自动阻塞
     */
    private val dataQueue = LinkedBlockingQueue<Byte>()
    private val singleExecutor: Executor by lazy {
        Executors.newSingleThreadExecutor()
    }
    private var payload = ArrayList<Byte>()
    private val readRunnable by lazy {
        Runnable {
            while (usbSerialPort?.isOpen == true || dataQueue.isNotEmpty()) {
                val byte = dataQueue.take()
                // 检查帧头，帧尾，然后发送体测数据
                if (byte == params.preamble) {
                    // 帧头
                    payload.clear()
                    payload.add(byte)
                    // 长度
                    val length = dataQueue.take()
                    payload.add(length)
                    message("长度：${HexDump.toHexString(length)}")
                    for (i in 0 until length) {
                        payload.add(dataQueue.take())
                    }
                    if (payload.size > 0) {
                        val dumpHexString = HexDump.dumpHexString(payload.toByteArray())
                        message("粘包：" + dumpHexString)
                        val copy = arrayListOf<Byte>()
                        copy.addAll(payload)
                        receiver(copy)
                    }
                    payload.clear()
                }
            }
        }
    }

    // 设备链接端点
    private var usbSerialPort: UsbSerialPort? = null
    private var usbIoManager: SerialInputOutputManager? = null

    /**
     * 链接设备
     */
    fun connect(success: (() -> Unit)? = null) {
        if (connected) {
            message("已连接")
            return
        }
        var device: UsbDevice? = null
        val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
        for (v in usbManager.deviceList.values){
            if ((params.deviceId != 0 && v.deviceId == params.deviceId) ||
                (params.vendorId != 0 && v.vendorId == params.vendorId)) {
                device = v
                break
            }
        }
        if (device == null) {
            message("没找到选择的设备")
            return
        }

        val driver = UsbSerialProber.getDefaultProber().probeDevice(device)
        if (driver == null) {
            message("没找到设备驱动")
            return
        }

        val connection = usbManager.openDevice(driver.device)
        if (connection == null && !usbManager.hasPermission(driver.device)) {
            // 没有权限
            val intent = Intent(ACTION_USB_PERMISSION)
            intent.setPackage(context.packageName)
            val usbPermissionIntent =
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE)
            usbManager.requestPermission(driver.device, usbPermissionIntent)
            return
        }
        if (connection == null) {
            if (!usbManager.hasPermission(driver.device))
                message("链接失败：权限")
            else
                message("链接失败：打开")
            return
        }

        try {
            // 设备可能出现多个端点，usb通常只有一个，如果出现再处理
            usbSerialPort = driver.ports[0]
            usbSerialPort?.let { usbSerialPort ->
                usbSerialPort.open(connection)
                try {
                    usbSerialPort.setParameters(
                        params.baudRate,
                        params.dataBits,
                        params.stopBits,
                        params.parity
                    )
                } catch (e: UnsupportedOperationException) {
                    message("设置波特率异常");
                }
                usbIoManager = SerialInputOutputManager(
                    usbSerialPort,
                    object : SerialInputOutputManager.Listener {
                        override fun onNewData(data: ByteArray?) {
                            data?.let {
                                receive(it)
                            }
                        }

                        override fun onRunError(e: java.lang.Exception?) {
                            message("发送失败: " + e!!.message)
                            disconnect()
                        }
                    })
                usbIoManager!!.start()
                singleExecutor.execute(readRunnable)
                message("connected")
                success?.let { it() }
                connected = true
            }

        } catch (e: Exception) {
            e.printStackTrace()
            message("链接失败: " + e.message)
            disconnect()
        }
    }

    private fun receive(data: ByteArray) {
        val spn = SpannableStringBuilder()
        spn.append("receive " + data.size + " bytes\n")
        if (data.size > 0) spn.append(HexDump.dumpHexString(data)).append("\n")
        message(spn.toString())

        // 记录到queue
        for (byte in data) {
            dataQueue.put(byte)
        }
    }

    fun disconnect() {
        connected = false
        usbIoManager?.let {
            it.listener = null
            it.stop()
        }
        usbIoManager = null
        try {
            usbSerialPort?.let {
                it.close()
            }
        } catch (ignored: IOException) {
        }
        usbSerialPort = null
    }

    fun write(data: ByteArray, timeout: Int = 1000) {
        if (!connected) {
            message("未链接设备")
            return
        }
        try {
            usbSerialPort?.write(data, timeout)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            message("发送失败: " + e.message)
            disconnect()
        }
    }

}

/**
 * usb配置参数
 */
data class UsbSerialParams(

    var deviceId: Int = 0, // 链接的设备id
    var vendorId: Int = 0, // 链接的供应商id, 选取第一个设备

    var baudRate: Int = 9600, // 波特率
    var dataBits: Int = 8, // 数据位
    var stopBits: Int = UsbSerialPort.STOPBITS_1, // 停止位
    var parity: Int = UsbSerialPort.PARITY_NONE, //

    val preamble: Byte = 0xA5.toByte(), // 帧头-用于粘包
    val endpos: Byte = 0xAA.toByte(), // 帧尾
)