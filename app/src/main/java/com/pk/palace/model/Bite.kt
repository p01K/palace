package com.pk.palace.model


sealed interface Bite {
    val id: Int
    val title: String
    val description: String
    val category: NoteCategory

    fun contentType(): ContentType
}

data class Note(
    override val id: Int,
    override val title: String,
    override val description: String,
    override val category: NoteCategory
): Bite {
    override fun contentType() = ContentType.QUOTE
}

data class Image(
    override val id: Int,
    override val title: String,
    override val description: String,
    override val category: NoteCategory,
    val url: String
): Bite {
    override fun contentType() = ContentType.IMAGE
}

data class Gif(
    override val id: Int,
    override val title: String,
    override val description: String,
    override val category: NoteCategory,
    val url: String
): Bite {
    override fun contentType() = ContentType.GIF
}