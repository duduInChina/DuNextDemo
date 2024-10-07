package com.dudu.network

import android.os.Debug
import android.util.Log
import com.dudu.common.ext.logD
import com.dudu.network.adapter.FlowCallAdapterFactory
import com.dudu.network.converter.ResultDataConverterFactory
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * Retrofit创建Api实体管理类
 * Created by Dzc on 2023/5/10.
 */
object RetrofitManager {
    private const val TAG = "OkHttp Log : "

    private var debug = BuildConfig.NETWORK_DEBUG // debug启动日志
    private var isDabugOkHttpProFile = BuildConfig.NETWORK_PROFILE // okhttp profile插件，需开启debug

    fun setDebug(debug: Boolean, isDabugOkHttpProFile: Boolean) {
        this.debug = debug
        this.isDabugOkHttpProFile = isDabugOkHttpProFile
    }

    private val serviceMap = hashMapOf<String, Any>()

    /**
     * 创建Api实体
     * 后续优化构想
     * 1、通过解析class注解，完成参数初始化，但每次加载都需要解析
     * 2、通过ksp自动生成静态的解析代码，优化解析
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> createService(clazz: Class<T>, baseUrl: String, timeout: Long? = null): T {
        val className = clazz.name
        return if (serviceMap.containsKey(className)) {
            serviceMap[className] as T
        } else {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient(timeout))
                .addConverterFactory(ResultDataConverterFactory.create())
                .addCallAdapterFactory(FlowCallAdapterFactory.create())
                .build()

            val newService = retrofit.create(clazz)
            serviceMap[className] = newService as Any
            newService
        }

    }

    private fun getClient(timeout: Long? = null): OkHttpClient {
        var builder = OkHttpClient
            .Builder()

        timeout?.let {
            builder.connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
        }

        if(debug){
            builder = builder.addInterceptor(HttpLoggingInterceptor{
                it.logD(TAG)
            }.setLevel(HttpLoggingInterceptor.Level.BODY))

            if(isDabugOkHttpProFile){
                builder = builder.addInterceptor(OkHttpProfilerInterceptor())
            }

        }

        return builder.build()
    }

    fun clearCache() {
        serviceMap.clear()
    }

}