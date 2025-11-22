package com.pk.palace.repo

import com.pk.palace.model.ContentType
import com.pk.palace.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class NoteRepository {
    private val _notes = MutableStateFlow(listOf<Note>())
    val notes = _notes.asStateFlow()

    init {
        loadNotes()
    }

    fun getNoteById(id: Int): Flow<Note?> = notes.map { notes ->  notes.find { n ->  n.id==id } }
    fun getNotes(): List<Note> {
        return notes.value
    }


    private fun loadNotes() {
        _notes.value = listOf(
            Note(1, "Mechanical Sympathy", "What is it really ?", ContentType.QUOTE),
            Note(2, "Object Mother", "What is it really ?", ContentType.QUOTE),
            Note(3, "Jean Michele Jar", "Name one song", ContentType.QUOTE),
            Note(4, "Seneca", "One good quote ...", ContentType.QUOTE)
        )
    }
}