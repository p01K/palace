package com.pk.palace.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pk.palace.model.Note
import com.pk.palace.repo.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        loadNotes()
    }

    fun getNoteById(id: Int): Flow<Note?> = repository.getNoteById(id)

    fun loadNotes() {
        viewModelScope.launch {
            _loading.value = true
            _notes.value = repository.getNotes()
            _loading.value = false
        }
    }
}