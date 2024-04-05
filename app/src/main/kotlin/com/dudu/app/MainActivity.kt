package com.dudu.app

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.dudu.app.databinding.ActivityMainBinding
import com.dudu.common.R
import com.dudu.common.base.activity.BaseActivity
import com.dudu.common.bean.Title
import com.dudu.common.bean.TitleType
import com.therouter.TheRouter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// @AndroidEntryPoint 的作用是标记 Android 组件以启用 Hilt 的依赖注入功能，简化依赖注入配置，并为组件提供依赖注入支持。

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override val title = Title(R.string.app_name, false, TitleType.COLL)
    override val bodyBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        bodyBinding.recyclerView.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
        adapter.addData(mainDataList)
        adapter.setOnItemClick { bean, _, _ ->
            bean.run {
                routerPath?.let {
                    TheRouter.build(it).navigation()
//                    startActivity(Intent(this@MainActivity, ))
                } ?: intent?.let {
                    startActivity(it)
                }
            }
        }
    }
}