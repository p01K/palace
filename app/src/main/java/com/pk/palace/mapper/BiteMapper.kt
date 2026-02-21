package com.pk.palace.mapper

import com.pk.palace.dto.ExerciseDTO
import com.pk.palace.model.Content
import com.pk.palace.model.ContentType
import com.pk.palace.model.Exercise
import com.pk.palace.model.Gif
import com.pk.palace.model.Image
import com.pk.palace.model.Text

fun ExerciseDTO.toDomain(): Exercise {
    return Exercise(
        this.id,
        this.title,
        this.description,
        this.category,
        this.targetGroups,
        contentOf(this)
    )
}

fun contentOf(dto: ExerciseDTO): Content  {
    return when(dto.type){
        ContentType.GIF -> Gif(dto.title, dto.description, dto.url)
        ContentType.IMAGE -> Image(dto.title, dto.description, dto.url)
        ContentType.TEXT -> Text(dto.title, dto.description)
    }
}