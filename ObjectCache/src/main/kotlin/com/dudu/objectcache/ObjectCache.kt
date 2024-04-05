package com.dudu.objectcache

import androidx.core.util.Pools.SynchronizedPool

object ObjectCache {

    // 对象池，使用数组维护，存储空对象 https://www.jianshu.com/p/6de50d608b19
    private val objectPool = SynchronizedPool<StringData>(10)
    var poolSize = 0

    // 对象池中获取写入对象
    fun acquire(): StringData {
        val acquire = objectPool.acquire()
        acquire?.let {
            poolSize--
            println("复用对象：" + it.name)
            return it
        } ?: let {
            println("创建对象")
            return StringData()
        }
    }

    // 加入对象池
    fun addPool(data: StringData) {
        data.clear()
        val release = objectPool.release(data)
        if (release) {
            poolSize++
            println("成功加入对象池")
        } else {
            println("失败对象池已满")
        }
    }

    // 缓存对象
    private val objectCache = object : LinkedHashMap<String, StringData>(10, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, StringData>?): Boolean {
            if(size > 10){
                val entry = entries.first()
                println("淘汰数据:" + eldest?.value?.name)
                addPool(entry.value)
                return true
            }
            return false
        }
    }

    fun getCache(key: String) = objectCache[key]

    fun putCache(key: String, value: StringData) = objectCache.put(key, value)

    fun cacheSize() = objectCache.size

}
