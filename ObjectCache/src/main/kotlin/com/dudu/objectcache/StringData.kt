package com.dudu.objectcache

data class StringData(var name: String = "") : IObjectCache{
    override fun clear() {
        name = ""
    }
}
