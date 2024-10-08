package com.dudu.common.util

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.net.URL

/**
 * author : duzicong
 * time   : 2024/06/15
 * desc   : 沙盒路径获取，文件操作
 *          所有目录默认到SD目录下，没有SD目录再到/data/data/
 */
object FileUtil {

    /**
     * 沙盒Cache路径
     */
    private lateinit var appCachePath: String

    /**
     * 获取app沙盒Cache路径
     * 不用申请权限
     * 有SD卡目录： /sdcard/Android/data/{包名}/cache/{path}，小米11卸载会删除
     * 没有SD卡目录： /data/data/{包名}/cache/{path}，卸载会删除
     * @param endFix 结尾添加字段
     */
    fun getAppCachePath(endFix: String?): String {
        if (!::appCachePath.isInitialized) {
            applicationContext?.let { context ->
                appCachePath = context.externalCacheDir?.let {
                    it.absolutePath
                } ?: let {
                    context.cacheDir.absolutePath
                }
            }
        }
        endFix?.let {
            return appCachePath + File.separator + endFix
        } ?: let {
            return appCachePath
        }

    }

    /**
     * 获取app沙盒File路径
     * @param type null（根目录） 或 Environment.DIRECTORY_PICTURES、Environment.DIRECTORY_MUSIC、Environment.DIRECTORY_MOVIES、Environment.DIRECTORY_DOWNLOADS
     * 如：DIRECTORY_PICTURES，路径 /sdcard/Android/data/{包名}/files/Pictures
     * 注：有SD目录才有type区分，/data/data/下是没有type区分，会存在输出目录不一致情况
     */
    fun getAppFilePath(type: String?, endFix: String?): String {
        val path = applicationContext?.let { context ->
            type?.let {
                context.getExternalFilesDir(type)?.let {
                    it.absolutePath
                } ?: let {
                    context.filesDir.absolutePath
                }
            } ?: let {
                context.externalCacheDir?.let {
                    it.absolutePath
                } ?: let {
                    context.filesDir.absolutePath
                }
            }
        } ?: let {
            ""
        }
        return endFix?.let {
            path + File.separator + endFix
        } ?: let {
            path
        }
    }

    /**
     * 图片共有目录，用于图片裁剪
     */
    fun getPublicPictureDirectory(): String {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path
    }

    /**
     * 文件大小转可读字符
     */
    fun formatFileSize(size: Long): String {
        var sFileSize = "0K"

        if (size <= 0) {
            return sFileSize
        }

        val dFileSize: Double = size.toDouble()
        val kiloByte: Double = dFileSize / 1024
        if (kiloByte < 1 && kiloByte > 0) {
            return "{$size}Byte"
        }

        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            sFileSize = String.format("%.2f", kiloByte)
            return sFileSize + "K"
        }

        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            sFileSize = String.format("%.2f", megaByte)
            return sFileSize + "M"
        }

        val teraByte = gigaByte / 1024
        if (teraByte < 1) {
            sFileSize = String.format("%.2f", gigaByte)
            return sFileSize + "G"
        }

        sFileSize = String.format("%.2f", teraByte)
        return sFileSize + "T"
    }

    /**
     * 根据Url拆分最后的字符作为文件名
     */
    fun fileNameFormUrl(url: String): String? {
        val urlObj = URL(url)
        return urlObj.path.substringAfterLast("/")
    }

    /**
     * 创建新的文件
     * 检查没有父级路径创建
     * 检查已存在文件，删除再创建
     */
    fun createNewFile(path: String): File {
        val file = File(path)
        file.parentFile?.let {
            if (!it.exists()) it.mkdirs()
        }
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
        return file
    }

    /**
     * 文件父目录，就是不包含文件的目录
     */
    fun getParentPath(path: String): String {
        val end = path.lastIndexOf('/')
        return if (end <= 0) "" else path.substring(0, end + 1)
    }

    /**
     * 根据路径返回文件名称
     */
    fun getFileName(path: String): String {
        val length = path.length
        if (length == 0) return ""
        return if (path[length - 1] == '/') {
            val index = path.lastIndexOf('/', length - 2)
            if (index <= 0)
                path.substring(0, length - 1)
            else
                path.substring(index + 1, length - 1)
        } else {
            val index = path.lastIndexOf('/')
            if (index <= 0)
                path
            else
                path.substring(index + 1)
        }
    }

    /**
     * 返回目录下所有文件，创建时间降序排序
     */
    fun getFilesListInDirectory(directoryPath: String, isSorted: Boolean = true): List<File> {
        val dir = File(directoryPath)
        if (!dir.exists() || !dir.isDirectory) {
            return emptyList()
        }
        val files = dir.listFiles()?.toList() ?: emptyList()
        if (isSorted) {
            return files.sortedByDescending { it.lastModified() }
        }
        return files
    }

    /**
     * 保存String到文件，系统Api方案
     * MODE_PRIVATE 覆盖原来内容不存在就创建，MODE_APPEND 追加内容不存在就创建
     * 保存路径 /data/data/{包名}/files/{fileName}
     * 不用申请权限 已测 8.0 和 小米 12
     */
    fun saveStringForOpenFile(context: Context, fileName: String, content: String) {
        val output = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        BufferedWriter(OutputStreamWriter(output)).use {
            it.write(content)
        }
    }

    /**
     * 保存文件到Download目录
     * 方法仅参考，由于版本间有差异，需申请权限
     */
    fun saveDownloadsFile(context: Context, fileName: String, content: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // 使用MediaStore.Downloads.EXTERNAL_CONTENT_URI 必须大于等于Q 10
            // 同样不用权限申请AndrodManifest标注权限也可以创建
            val values = ContentValues()
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            val uri =
                context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                val output = context.contentResolver.openOutputStream(uri)
                BufferedWriter(OutputStreamWriter(output)).use {
                    it.write(content)
                }
            }
        } else {
            // 10 版本以下，需申请权限创建
            // 8.0 跳异常 (Permission denied) 需申请权限  小米 12 则可以直接创建
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            } else {
                val file = File("/sdcard/Download", fileName)
                val output = FileOutputStream(file) // 负责文件创建
                BufferedWriter(OutputStreamWriter(output)).use {
                    it.write(content)
                }
            }
        }
    }

    /**
     * 把文件保存到sdcard目录下
     * 方法仅参考
     */
    fun saveOrderFile(context: Context, fileName: String, content: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 11 以上授予sdcard权限(需跳转授权界面)才会在目录中创建文件
            if (Environment.isExternalStorageManager()) {
                val file = File("/sdcard", fileName)
                val output = FileOutputStream(file)
                BufferedWriter(OutputStreamWriter(output)).use {
                    it.write(content)
                }
            } else {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                context.startActivity(intent)
            }
        } else {
            // 10 版本以下，需申请权限创建
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            } else {
                val file = File("/sdcard", fileName)
                val output = FileOutputStream(file) // 负责文件创建
                BufferedWriter(OutputStreamWriter(output)).use {
                    it.write(content)
                }
            }
        }
    }

    /**
     * 检查没有父级路径创建
     */
    fun parentFileMake(path: String): File{
        val file = File(path)
        file.parentFile?.let {
            if (!it.exists()) it.mkdirs()
        }
        return file
    }

}