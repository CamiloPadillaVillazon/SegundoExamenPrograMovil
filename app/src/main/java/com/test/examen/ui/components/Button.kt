package com.test.examen.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    selected: Boolean,
    fieldValue: String,
    modifier: Modifier = Modifier
) {
    val valid = fieldValue.isNotBlank()
    val src = remember { MutableInteractionSource() }
    val pressed = src.collectIsPressedAsState().value
    val hovered = src.collectIsHoveredAsState().value
    val focused = src.collectIsFocusedAsState().value

    val targetScale = when {
        pressed -> 0.97f
        hovered || focused -> 1.02f
        else -> 1f
    }
    val scale = animateFloatAsState(targetValue = targetScale, label = "").value
    val elevation = animateDpAsState(
        targetValue = when {
            pressed -> 0.dp
            hovered || focused || selected -> 6.dp
            else -> 0.dp
        },
        label = ""
    ).value

    val container = when {
        valid && selected -> MaterialTheme.colorScheme.primary
        valid && !selected -> MaterialTheme.colorScheme.primary
        !valid && selected -> MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.surface
    }
    val content = when {
        valid && selected -> MaterialTheme.colorScheme.onPrimary
        valid && !selected -> MaterialTheme.colorScheme.onPrimary
        !valid && selected -> MaterialTheme.colorScheme.onSurfaceVariant
        else -> MaterialTheme.colorScheme.primary
    }
    val border = if (!valid) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null

    val animatedBg = animateColorAsState(container, label = "").value
    val animatedFg = animateColorAsState(content, label = "").value

    Button(
        onClick = onClick,
        enabled = true,
        interactionSource = src,
        shape = MaterialTheme.shapes.large,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = elevation,
            pressedElevation = 0.dp,
            hoveredElevation = elevation,
            focusedElevation = elevation,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = animatedBg,
            contentColor = animatedFg,
            disabledContainerColor = animatedBg,
            disabledContentColor = animatedFg
        ),
        border = border,
        modifier = modifier.scale(scale)
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}
