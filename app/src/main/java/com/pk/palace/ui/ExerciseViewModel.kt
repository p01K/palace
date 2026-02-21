package com.pk.palace.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pk.palace.model.Exercise
import com.pk.palace.repo.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExerciseViewModel(
    private val repository: ExerciseRepository
) : ViewModel() {

    private val _notes = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises = _notes.asStateFlow()

    private val noteFlows = mutableMapOf<Int, StateFlow<Exercise?>>()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        loadNotes()
    }

    fun getNoteByIdStateFlow(id: Int): StateFlow<Exercise?> {
        return noteFlows.getOrPut(id) {
            repository.get(id)
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
            _notes.value = repository.listAll()
            _loading.value = false
        }
    }
}