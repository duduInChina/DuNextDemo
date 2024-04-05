package com.dudu.common.initializer

import android.content.Context
import androidx.startup.Initializer
import com.dudu.common.util.applicationContext

/**
 * app启动时通过Contentrovider，初始化其他业务，common进行初始化
 */
class CommonInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        applicationContext ?: let {
            applicationContext = context
        }

    }

    override fun dependencies() = emptyList<Class<Initializer<*>>>()

}