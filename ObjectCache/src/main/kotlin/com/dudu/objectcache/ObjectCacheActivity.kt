package com.dudu.objectcache

import androidx.viewbinding.ViewBinding
import com.dudu.common.base.activity.BaseActivity
import com.dudu.common.bean.Title
import com.dudu.common.router.RouterPath
import com.dudu.objectcache.databinding.ActivityObjectCacheBinding
import com.therouter.router.Route

@Route(path = RouterPath.OBJECT_CACHE)
class ObjectCacheActivity : BaseActivity() {

    override val bodyBinding by lazy {
        ActivityObjectCacheBinding.inflate(layoutInflater)
    }
    override val title = Title(com.dudu.common.R.string.object_cache)

    private var tag = 0

    override fun initView() {

        bodyBinding.add.setOnClickListener {

            // 对象池获取对象
            // 对象加入缓存

            val acquire = ObjectCache.acquire()
            acquire.name = tag.toString()
            ObjectCache.putCache(tag.toString(), acquire)
            tag++
            bodyBinding.run {
                sizeCache.text = ObjectCache.cacheSize().toString()
                sizePool.text = ObjectCache.poolSize.toString()
            }
        }

    }
}