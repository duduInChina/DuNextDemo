package com.dudu.common.ext

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

inline fun ComponentActivity.registerResult(
    isSuccessCheck: Int = Activity.RESULT_OK,// 检查成功才会回调
    crossinline callback: (resultCode: Int, data: Intent?) -> Unit
) = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    if (result.resultCode == isSuccessCheck) {
        callback(result.resultCode, result.data)
    }
}

inline fun ComponentActivity.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
){
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(minActiveState){
            block()
        }
    }
}