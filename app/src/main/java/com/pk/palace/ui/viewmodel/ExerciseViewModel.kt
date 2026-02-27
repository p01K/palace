package com.pk.palace.ui.viewmodel

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

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises = _exercises.asStateFlow()

    private val noteFlows = mutableMapOf<Int, StateFlow<Exercise?>>()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        loadExercises()
    }

    fun getNoteByIdStateFlow(id: Int): StateFlow<Exercise?> {
        return noteFlows.getOrPut(id) {
            repository.get(id)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Companion.WhileSubscribed(5000),
                    initialValue = null
                )
        }
    }

    fun loadExercises() {
        viewModelScope.launch {
            _loading.value = true
            _exercises.value = repository.listAll()
            _loading.value = false
        }
    }
}