package com.pk.palace.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pk.palace.model.Bite
import com.pk.palace.repo.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _notes = MutableStateFlow<List<Bite>>(emptyList())
    val notes = _notes.asStateFlow()

    private val noteFlows = mutableMapOf<Int, StateFlow<Bite?>>()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        loadNotes()
    }

    fun getNoteByIdStateFlow(id: Int): StateFlow<Bite?> {
        return noteFlows.getOrPut(id) {
            repository.getBite(id)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = null
                )
        }
    }

    fun loadNotes() {
        viewModelScope.launch {
            _loading.value = true
            _notes.value = repository.getBites()
            _loading.value = false
        }
    }
}