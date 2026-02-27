package com.pk.palace.dto


data class WorkoutDTO(
    val id: Int = 0,
    val title: String = "",
    val exercises: List<ExerciseDTO> = emptyList(),
)