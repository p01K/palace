package com.pk.palace.dto

import com.pk.palace.model.ContentType
import com.pk.palace.model.NoteCategory

data class NoteDTO(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val type: ContentType = ContentType.QUOTE,
    val category: NoteCategory = NoteCategory.APHORISM,
    val url: String = ""
)