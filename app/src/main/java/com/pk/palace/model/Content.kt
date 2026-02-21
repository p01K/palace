package com.pk.palace.model

sealed interface Content

data class Gif(val title: String, val description: String, val url: String) : Content

data class Image(val title: String, val description: String, val url: String) : Content

data class Text(val title: String, val description: String) : Content