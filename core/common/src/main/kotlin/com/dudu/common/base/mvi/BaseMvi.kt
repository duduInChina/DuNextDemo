package com.dudu.common.base.mvi

import androidx.annotation.Keep

// keep在R8代码混淆压缩时必须保留被标记的类或方法，以防止代码出现因混淆而导致的崩溃。
/** 用户与ui的交互事件 */
@Keep
interface Action

/** ui响应的状态 */
@Keep
interface State

/** ui响应的事件 */
@Keep
interface Effect