package com.dudu.common.log

import android.database.Cursor
import androidx.core.database.getStringOrNull
import com.dudu.common.BuildConfig
import com.dudu.common.util.JsonUtil
import com.orhanobut.logger.Logger
import com.tencent.mars.xlog.Log
import java.io.Serializable

/**
 * author : duzicong
 * time   : 2024/06/22
 * desc   : log加载器
 */
class CommonLogLoader : ILogLoader {
    override fun d(msg: String) {
        d(null, msg)
    }

    override fun d(tag: String?, msg: String) {
        tag?.let {
            Logger.t(it).d(msg)
        }?:let {
            Logger.d(msg)
        }
    }

    override fun e(throwable: Throwable?, msg: String) {
        e(null, throwable, msg)
    }

    override fun e(tag: String?, throwable: Throwable?, msg: String) {
        tag?.let {
            Logger.t(it).e(throwable, msg)
            Log.e(it, msg, throwable)
        }?:let {
            Logger.e(throwable, msg)
            Log.e(LogManager.defaultTag, msg, throwable)
        }
    }

    override fun w(msg: String) {
        w(null, msg)
    }

    override fun w(tag: String?, msg: String) {
        tag?.let {
            Logger.t(it).w(msg)
        }?:let {
            Logger.w(msg)
        }
    }

    override fun v(msg: String) {
        v(null, msg)
    }

    override fun v(tag: String?, msg: String) {
        tag?.let {
            Logger.t(it).v(msg)
        }?:let {
            Logger.v(msg)
        }
    }

    override fun i(msg: String) {
        i(null, msg)
    }

    override fun i(tag: String?, msg: String) {
        tag?.let {
            Logger.t(it).i(msg)
        }?:let {
            Logger.i(msg)
        }
    }

    override fun wtf(msg: String) {
        wtf(null, msg)
    }

    override fun wtf(tag: String?, msg: String) {
        tag?.let {
            Logger.t(it).wtf(msg)
        }?:let {
            Logger.wtf(msg)
        }
    }

    override fun x(msg: String) {
        x(null, msg)
    }

    override fun x(tag: String?, msg: String) {
        tag?.let {
            Logger.t(it).i(msg)
            Log.i(it, msg)
        }?:let {
            Logger.i(msg)
            Log.i(LogManager.defaultTag, msg)
        }
    }

    override fun obj(obj: Any) {
        obj(null, obj)
    }

    override fun obj(tag: String?, obj: Any) {
        if(!BuildConfig.DEBUG) return

        if(obj is Cursor) {

            val builder = StringBuilder()
            builder.append("Cursor Data:\n")
            // 遍历Cursor的列
            obj.moveToFirst() // 移动到第一行
            do{
                for (i in 0 until obj.columnCount) {
                    val columnName: String = obj.getColumnName(i)
                    val columnValue: String? = obj.getStringOrNull(obj.getColumnIndex(columnName))
                    builder.append(columnName).append(": ").append(columnValue).append("\n")
                }
                builder.append("\n")
            }while (obj.moveToNext())

            obj.moveToFirst() // 移动到第一行

            d(tag, builder.toString())

        } else if(obj is Serializable){
            tag?.let {
                Logger.t(it).json(JsonUtil.encodeToString(obj))
            }?:let {
                Logger.json(JsonUtil.encodeToString(obj))
            }
        }

    }
}