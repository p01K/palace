package com.pk.palace.ui.util

import android.content.Context
import coil.ImageLoader
import coil.decode.ImageDecoderDecoder

object ImageLoaderProvider {

    private var imageLoader: ImageLoader? = null

    fun get(context: Context): ImageLoader {
        return imageLoader ?: synchronized(this) {
            imageLoader ?: buildImageLoader(context).also { imageLoader = it }
        }
    }

    private fun buildImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .components {
                add(ImageDecoderDecoder.Factory())
            }
            .build()
    }
}