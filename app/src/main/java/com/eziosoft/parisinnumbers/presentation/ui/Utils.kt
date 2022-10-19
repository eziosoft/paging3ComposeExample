package com.eziosoft.parisinnumbers.presentation.ui

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer

fun Modifier.rotating(duration: Int, right: Boolean = true): Modifier = composed {
    val transition = rememberInfiniteTransition()
    val angleRatio by transition.animateFloat(
        initialValue = if(right) 0f else 1f,
        targetValue = if(right) 1f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration)
        )
    )

    graphicsLayer { rotationZ = 360 * angleRatio }
}
