package com.pk.palace.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pk.palace.ui.viewmodel.ExerciseViewModel

class ExerciseViewModelFactory(
    private val repository: ExerciseRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
            return ExerciseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}