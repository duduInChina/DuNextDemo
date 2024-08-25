package com.dudu.objectcache

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.dudu.common.base.activity.BaseActivity
import com.dudu.common.bean.Title
import com.dudu.common.ext.logD
import com.dudu.common.router.RouterPath
import com.dudu.objectcache.databinding.ActivityObjectCacheBinding
import com.therouter.router.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Route(path = RouterPath.OBJECT_CACHE)
class ObjectCacheActivity : BaseActivity() {

    override val bodyBinding by lazy {
        ActivityObjectCacheBinding.inflate(layoutInflater)
    }
    override val title = Title(com.dudu.common.R.string.object_cache)

    private var tag = 0
    private var tagLoop = 0

    private var isAddLoop = false
    private var isReadLoop = false

    val objectCache = ObjectCache<Int, StringData>(10)

    override fun initView() {

        bodyBinding.add.setOnClickListener {

            // 对象池获取对象
            // 对象加入缓存

            val acquire = ObjectCacheTest.acquire()
            acquire.name = tag.toString()
            ObjectCacheTest.putCache(tag.toString(), acquire)
            tag++
            bodyBinding.run {
                sizeCache.text = ObjectCacheTest.cacheSize().toString()
                sizePool.text = ObjectCacheTest.poolSize.toString()
            }
        }

        bodyBinding.addLoop.setOnClickListener {

            if (!isAddLoop) {
                isAddLoop = true

                flow {
                    while (true) {
                        tagLoop++
                        emit(tagLoop)
                        delay(10)
                    }
                }.onEach {
                    val stringData = objectCache.acquire() ?: StringData()
                    stringData.name = it.toString()
                    objectCache.putCache(it, stringData)
                    bodyBinding.sizeLoop.text = it.toString()
                }.flowOn(Dispatchers.Main).launchIn(lifecycleScope)
            }

        }

        bodyBinding.readLoop.setOnClickListener {

            if (!isReadLoop) {
                isReadLoop = true

                flow {
                    while (true) {
                        emit(1)
                        delay(1000)
                    }
                }.onEach {
                    bodyBinding.sizeLoopRead.text = objectCache.getCache(tagLoop)?.name
                }.flowOn(Dispatchers.Main)
                    .launchIn(lifecycleScope)

            }

        }

    }
}