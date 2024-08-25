package com.dudu.objectcache

import androidx.core.util.Pools

interface IObjectCache {
    fun clear()
}

class ObjectCache<K, T : IObjectCache>(val maxCacheSize: Int, maxPoolSize: Int = 10) {

    private val objectPool = Pools.SynchronizedPool<T>(maxPoolSize)
    private var poolSize = 0

    private val objectCache = object : LinkedHashMap<K, T>(maxCacheSize, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, T>?): Boolean {
            if (size > 10) {
                val entry = entries.first()
                addPool(entry.value)
                return true
            }
            return false
        }
    }

    private fun addPool(data: T) {
        data.clear()
        val release = objectPool.release(data)
        if (release) {
            poolSize++
        }
    }

    fun acquire(): T? {
        val acquire = objectPool.acquire()
        acquire?.let {
            poolSize--
            return it
        }
        return null
    }

    fun putCache(key: K, value: T) = objectCache.put(key, value)

    fun getCache(key: K) = objectCache[key]

    fun cacheSize() = objectCache.size

}