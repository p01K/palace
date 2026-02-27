package com.pk.palace.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pk.palace.model.Workout
import com.pk.palace.repo.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WorkoutViewModel(
    private val repository: WorkoutRepository
) : ViewModel() {

    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts = _workouts.asStateFlow()

    private val workoutFlows = mutableMapOf<Int, StateFlow<Workout?>>()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        loadWorkouts()
    }

    fun getWorkoutByIdStateFlow(id: Int): StateFlow<Workout?> {
        return workoutFlows.getOrPut(id) {
            repository.get(id)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Companion.WhileSubscribed(5000),
                    initialValue = null
                )
        }
    }

    fun loadWorkouts() {
        viewModelScope.launch {
            _loading.value = true
            _workouts.value = repository.listAll()
            _loading.value = false
        }
    }
}