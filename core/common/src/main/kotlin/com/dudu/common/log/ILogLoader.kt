package com.dudu.common.log

/**
 * author : duzicong
 * time   : 2024/06/22
 * desc   : log加载器抽象层
 */
interface ILogLoader {
    fun d(msg: String)
    fun d(tag: String?, msg: String)
    fun e(throwable: Throwable?, msg: String)
    fun e(tag: String?, throwable: Throwable?, msg: String)
    fun w(msg: String)
    fun w(tag: String?, msg: String)
    fun v(msg: String)
    fun v(tag: String?, msg: String)
    fun i(msg: String)
    fun i(tag: String?, msg: String)
    fun wtf(msg: String)
    fun wtf(tag: String?, msg: String)
    // 利用xLog记录到文件
    fun x(msg: String)
    fun x(tag: String?, msg: String)

    /**
     * obj转换json输出log
     */
    fun obj(obj: Any)
    fun obj(tag: String?, obj: Any)
}