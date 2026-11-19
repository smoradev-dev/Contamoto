package com.drox.contamoto.ui.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.flow.conflate

fun Modifier.pressScale(
    interactionSource: MutableInteractionSource,
    targetScale: Float = 0.95f
): Modifier = composed {
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = remember { Animatable(1f) }
    val animSpec = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )

    LaunchedEffect(interactionSource) {
        snapshotFlow { isPressed }
            .conflate()
            .collect { pressed: Boolean ->
                scale.animateTo(
                    targetValue = if (pressed) targetScale else 1f,
                    animationSpec = animSpec
                )
            }
    }

    graphicsLayer {
        scaleX = scale.value
        scaleY = scale.value
    }
}
