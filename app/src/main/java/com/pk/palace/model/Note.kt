package com.pk.palace.model

enum class ContentType{
    QUOTE,
    VIDEO
}

data class Note(val id: Int, val title: String, val description: String, val type: ContentType)
