package com.pk.palace.ui.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.ui.graphics.vector.ImageVector
import com.pk.palace.R
import com.pk.palace.model.ExerciseCategory
import com.pk.palace.model.TargetGroup

fun TargetGroup.toDrawable(): Int {
    return when(this){
        TargetGroup.TRICEPS -> R.drawable.biceps_icon
        TargetGroup.BICEPS -> R.drawable.biceps_icon
        TargetGroup.GLUTES -> R.drawable.glutes_icon
        TargetGroup.HAMSTRINGS -> R.drawable.hamstrings_icon
        TargetGroup.QUADS -> R.drawable.quads_icon
    }
}

fun ExerciseCategory.toIcon(): ImageVector {
    return when (this) {
        ExerciseCategory.STRENGTH -> Icons.Default.FitnessCenter
        ExerciseCategory.MOBILITY -> Icons.Default.SelfImprovement
        ExerciseCategory.WARMUP -> Icons.Default.SelfImprovement
    }
}
