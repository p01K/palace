package com.pk.palace.model

@JvmInline
value class ExerciseId(val value: Int)

data class Exercise (
    val id: ExerciseId,
    val title: String,
    val description: String,
    val categories: List<ExerciseCategory>,
    val targetGroups: List<TargetGroup>,
    val content: Content
)
