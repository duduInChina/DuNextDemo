package com.dudu.log

import android.text.TextUtils
import androidx.viewbinding.ViewBinding
import com.dudu.common.base.activity.BaseActivity
import com.dudu.common.bean.Title
import com.dudu.common.bean.TitleType
import com.dudu.common.ext.logD
import com.dudu.common.ext.logE
import com.dudu.common.ext.logI
import com.dudu.common.ext.logV
import com.dudu.common.ext.logW
import com.dudu.common.ext.logX
import com.dudu.common.log.XCrashLogManager
import com.dudu.common.router.RouterPath
import com.dudu.log.databinding.ActivityLogBinding
import com.therouter.router.Route

/**
 * author : duzicong
 * time   : 2024/06/22
 * desc   :
 */
@Route(path = RouterPath.LOG)
class LogActivity : BaseActivity() {

    override val title = Title(titleType = TitleType.COLL, text = "日志")

    private val trackerInfo = """
            参考Tracker无埋点记录处理
            application.registerActivityLifecycleCallbacks，注册activity生命周期监听
            fragmentManager.registerFragmentLifecycleCallbacks，注册fragment生命周期监听，在上面获取到activity.create即可判断注册监听
            在生命周期中即可记录页面曝光时间，记录该条曝光日志到数据库
            无埋点监听View点击事件的两种方式：
            • 基于AOP监听onClick，@Aspect
            • 通过生命周期监听，获取根View，轮询子View，setAccessibilityDelegate，获得监听
            同样根据View的特征，记录日志到数据库
            Handler时间轮询，提交日志记录
        """.trimIndent()

    override val bodyBinding by lazy {
        ActivityLogBinding.inflate(layoutInflater)
    }

    override fun initView() {
        bodyBinding.trackerInfo.text = trackerInfo

        bodyBinding.apply {
            logD.setOnClickListener {
                "logD".logD(getTag())
            }
            logE.setOnClickListener {
                "logE".logE(getTag())
            }
            logW.setOnClickListener {
                "logW".logW(getTag())
            }
            logV.setOnClickListener {
                "logV".logV(getTag())
            }
            logI.setOnClickListener {
                "logI".logI(getTag())
            }
            logX.setOnClickListener {
                "logX".logX(getTag())
            }
            javaCrash.setOnClickListener {
                XCrashLogManager.testJavaCrash(false)
            }

            nativeCrash.setOnClickListener {
                XCrashLogManager.testNativeCrash(false)
            }

            anrCrash.setOnClickListener {
                XCrashLogManager.testAnrCrash()
            }
            sendCrash.setOnClickListener {
                XCrashLogManager.sendAllTombstones()
            }
        }
    }

    private fun getTag(): String? {
        val tag = bodyBinding.editTag.text.toString()
        if (!TextUtils.isEmpty(tag)) {
            return tag
        } else {
            return null
        }
    }

}