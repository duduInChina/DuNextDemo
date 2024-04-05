package com.dudu.objectcache

data class StringData(var name: String = "") {
    fun clear() {
        name = ""
    }
}
