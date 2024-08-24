package com.dudu.common.util

import android.speech.tts.TextToSpeech
import com.dudu.common.ext.logD
import java.util.Locale

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/08/13
 *     desc   : 语音播报，需要依赖系统设置 无障碍TTS引擎
 * </pre>
 */
object TTSUtils {

    private var mSpeech: TextToSpeech? = null
    private var mIsInit: Boolean = false
    private var initCount = 0 // 初始化次数，防止重复多数初始化

    fun initTTS() {
        if ((mIsInit && mSpeech != null) || initCount >= 4) {
            return
        }
        initCount++
        destory()
        mSpeech = TextToSpeech(applicationContext) { status ->
            when (status) {
                TextToSpeech.SUCCESS -> {
                    mSpeech?.let {
                        val result = it.setLanguage(Locale.CHINA)
                        if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED
                        ) {
                            "TTSUtils: TTS引擎初始化中文失败".logD()
                            mIsInit = false
                        } else {
                            "TTSUtils: TTS引擎初始化中文成功".logD()
                            mIsInit = true
                        }
                    }
                }

                else -> {
                    "TTSUtils: TTS引擎初始化失败 ${status}".logD()
                    mIsInit = false
                }
            }
        }
    }

    /**
     * @param queueMode
     *  TextToSpeech.QUEUE_FLUSH：该模式下在有新任务时候会清除当前语音任务，执行新的语音任务
     *  TextToSpeech.QUEUE_ADD：该模式下会把新的语音任务放到语音任务之后，等前面的语音任务执行完了才会执行新的语音任务。
     *  @param utteranceId 唯一值
     */
    fun speak(
        text: String,
        queueMode: Int = TextToSpeech.QUEUE_FLUSH,
        utteranceId: String = System.currentTimeMillis().toString()
    ): Boolean {
        if (!mIsInit) {
            initTTS()
        } else {
            mSpeech?.let {
                // params 引擎参数
                val result = it.speak(text, queueMode, null, utteranceId)
                return result == TextToSpeech.SUCCESS
            }
        }
        return false
    }

    fun stop() {
        mSpeech?.stop()
    }

    fun isSpeaking(): Boolean {
        return mSpeech?.isSpeaking ?: false
    }

    fun destory() {
        mSpeech?.let {
            it.stop()
            it.shutdown()
        }
    }

}