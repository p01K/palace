package com.pk.palace.dto

import com.pk.palace.model.ContentType
import com.pk.palace.model.NoteCategory
import com.pk.palace.model.TargetGroup

data class ExerciseDTO(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val type: ContentType = ContentType.TEXT,
    val category: NoteCategory = NoteCategory.APHORISM,
    var targetGroups: List<TargetGroup> = emptyList(),
    val url: String = ""
)