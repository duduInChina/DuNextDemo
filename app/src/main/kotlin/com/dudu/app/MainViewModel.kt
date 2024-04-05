package com.dudu.app

import com.dudu.common.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// HiltViewModel 的作用是简化 ViewModel 的依赖注入配置，提供依赖注入支持，并确保 ViewModel 的生命周期与相关组件一致，
// 从而帮助开发者更轻松地管理和使用 ViewModel。

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
}