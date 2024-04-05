package com.dudu.common.base.mvi

import androidx.lifecycle.viewModelScope
import com.dudu.common.base.viewmodel.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class IntentViewModel<A : Action, S : State, E : Effect> : BaseViewModel() {

    abstract fun initialState(): S

    /** 订阅事件的传入 onAction()分发处理事件 */
    protected abstract fun onAction(action: A)

    // 页面事件的 Channel 分发，事件不会被丢弃，如在没订阅者情况发布事件，缓冲满了就会暂停，等待订阅者出现，且事件只能消费一次（一对一），默认是粘性的
    private val _action = Channel<A>(Channel.UNLIMITED)

    init {
        viewModelScope.launch {
            _action.consumeAsFlow().collect {
                /*replayState：很多时候我们需要通过上个state的数据来处理这次数据，所以我们要获取当前状态传递*/
                onAction(it)
            }
        }
    }

    //发送页面事件
    fun sendAction(action: A) = viewModelScope.launch {
        _action.send(action)
    }


    // StateFlow默认数据防抖，相同值不会触发，数据粘性注册回流
    private var _state: MutableStateFlow<S> = MutableStateFlow(initialState())
    val state: StateFlow<S> = _state

    // 更新State页面状态 (data class 类型)
    // updateUiState { copy(articleUiState = ArticleUiState.SUCCESS(articleResult.data)) }
    fun updateState(reducer: S.() -> S) {
        _state.update { reducer(_state.value) }
    }

    // 更新State页面状态 (sealed class 类型)
    fun sendState(s: S) {
        _state.value = s
    }


    // SharedFlow处理粘性和背压的问题，但是 SharedFlow 只能接收流，不能自由取值，没订阅者情况，事件会放弃，事件可以多处消费（一对多）
    // [effect]事件带来的副作用，通常是一次性事件 例如：弹Toast、导航Fragment等
    private val _effect = MutableSharedFlow<E>()
    val effect: SharedFlow<E> by lazy { _effect.asSharedFlow() }

    // 两种方式发射，在协程外用viewModelScope发射
    protected fun sendEffect(builder: suspend () -> E?) = viewModelScope.launch {
        builder()?.let { _effect.emit(it) }
    }

    // 两种方式发射,suspend 协程中直接发射
    protected suspend fun sendEffect(effect: E) = _effect.emit(effect)
}