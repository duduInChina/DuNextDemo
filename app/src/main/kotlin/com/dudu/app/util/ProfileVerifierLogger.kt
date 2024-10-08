/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dudu.app.util

import androidx.profileinstaller.ProfileVerifier
import com.dudu.common.ext.logD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 基准配置文件
 * 启动优化
 */
class ProfileVerifierLogger @Inject constructor() {
    companion object {
        private const val TAG = "ProfileInstaller"
    }

    operator fun invoke() = CoroutineScope(SupervisorJob()).launch {
        val status = ProfileVerifier.getCompilationStatusAsync().await()
        "Status code: ${status.profileInstallResultCode}".logD(TAG)
        when {
            status.isCompiledWithProfile -> "App compiled with profile"
            status.hasProfileEnqueuedForCompilation() -> "Profile enqueued for compilation"
            else -> "Profile not compiled nor enqueued"
        }.logD(TAG)
    }
}
