package com.pk.palace.model


data class Exercise (
    val id: Int,
    val title: String,
    val description: String,
    val category: NoteCategory,
    val categories: List<ExerciseCategory>,
    val targetGroups: List<TargetGroup>,
    val content: Content
)
