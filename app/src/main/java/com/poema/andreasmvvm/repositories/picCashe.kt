package com.poema.andreasmvvm.repositories

import android.graphics.Bitmap
import android.util.LruCache

class picCashe() {
    private object HOLDER {
        val INSTANCE = picCashe()
    }
    companion object {
        val instance: picCashe by lazy { HOLDER.INSTANCE }
    }
    val lru: LruCache<Any, Any> = LruCache(1024)

    fun saveBitmapToCache(key: String, bitmap: Bitmap) {
        try {
            picCashe.instance.lru.put(key, bitmap)
        } catch (e: Exception) {
        }
    }
    fun retrieveBitmapFromCache(key: String): Bitmap? {
        try {
            return picCashe.instance.lru.get(key) as Bitmap?
        } catch (e: Exception) {
        }
        return null
    }

}