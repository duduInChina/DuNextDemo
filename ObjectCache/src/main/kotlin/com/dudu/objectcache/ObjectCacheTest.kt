package com.dudu.objectcache

import androidx.core.util.Pools.SynchronizedPool
import com.dudu.common.ext.logD

/**
 * 使用流程：
 * 获取一个初始对象：ObjectCache.acquire()
 * 对象中添加属性
 * 添加对象到缓存：ObjectCache.putCache(key, value)
 * 持续添加对象到缓存：达到上限，出现淘汰对象，清空对象属性，加到空属性对象池
 */
object ObjectCacheTest {

    // 空属性对象池，使用数组维护，存储空对象 https://www.jianshu.com/p/6de50d608b19
    private val objectPool = SynchronizedPool<StringData>(10)

    // 空属性对象池数量
    var poolSize = 0

    // 当前缓存的有数据对象
    private val objectCache = object : LinkedHashMap<String, StringData>(10, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, StringData>?): Boolean {
            if (size > 10) {
                val entry = entries.first()
                "淘汰数据:${eldest?.value?.name}".logD()
                addPool(entry.value)
                return true
            }
            return false
        }
    }

    // 加入对象池，加入一个使用结束淘汰的对象，并清空对象属性
    private fun addPool(data: StringData) {
        data.clear()
        val release = objectPool.release(data)
        if (release) {
            poolSize++
            "成功加入对象池：${data.name}".logD()
        } else {
            "失败对象池已满".logD()
        }
    }

    // 对象池中获取写入对象，释放一个初始状态的对象出去
    fun acquire(): StringData {
        val acquire = objectPool.acquire()
        acquire?.let {
            poolSize--
            "复用对象：${it.name}".logD()
            return it
        } ?: let {
            "创建对象".logD()
            return StringData()
        }
    }

    fun putCache(key: String, value: StringData) = objectCache.put(key, value)

    fun getCache(key: String) = objectCache[key]

    fun cacheSize() = objectCache.size

}
