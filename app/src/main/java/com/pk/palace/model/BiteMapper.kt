package com.pk.palace.model

import com.pk.palace.dto.NoteDTO

fun NoteDTO.toDomain(): Bite {
    return when(this.type){
        ContentType.IMAGE -> Image(this.id, this.title, this.description, this.category, this.url)
        ContentType.QUOTE -> Note(this.id, this.title, this.description, this.category)
        ContentType.VIDEO -> TODO("Should implement this")
    }
}