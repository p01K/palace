package com.pk.palace.model


enum class NoteCategory {
    FITNESS,
    WELL_BEING,
    APHORISM,
    NOTE
}

enum class ContentType{
    QUOTE,
    VIDEO
}

data class Note(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val type: ContentType = ContentType.QUOTE,
    val category: NoteCategory = NoteCategory.APHORISM
)
