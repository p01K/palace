package com.pk.palace.ui.compose

import com.pk.palace.R
import com.pk.palace.model.ExerciseCategory
import com.pk.palace.model.TargetGroup

fun TargetGroup.toDrawable(): Int {
    return when(this){
        TargetGroup.TRICEPS -> R.drawable.biceps_icon
        TargetGroup.BICEPS -> R.drawable.biceps_icon2
        TargetGroup.GLUTES -> R.drawable.glutes_icon_transparent
        TargetGroup.HIPS -> R.drawable.glutes_icon_transparent
        TargetGroup.HAMSTRINGS -> R.drawable.hamstrings_icon
        TargetGroup.QUADS -> R.drawable.quads_icon_transparent
        TargetGroup.CHEST -> TODO()
        TargetGroup.SHOULDERS -> R.drawable.shoulders_icon_transparent
        else -> TODO()
    }
}


fun ExerciseCategory.toDrawable(): Int {
    return when(this){
        ExerciseCategory.STRENGTH -> R.drawable.strength_icon
        ExerciseCategory.WARMUP -> R.drawable.warmup_icon
        ExerciseCategory.MOBILITY -> R.drawable.mobility_icon
    }
}


