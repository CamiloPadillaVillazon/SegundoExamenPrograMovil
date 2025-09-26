package com.test.examen.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingClick: (() -> Unit)? = null,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {},
    isPassword: Boolean = false,
    isError: Boolean = false,
    supportingText: String? = null,
) {
    val src = remember { MutableInteractionSource() }
    val focused = src.collectIsFocusedAsState().value
    val borderFocused = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
    val borderUnfocused = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
    val labelFocused = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { if (placeholder != null) Text(placeholder, style = MaterialTheme.typography.bodyMedium) },
        leadingIcon = { if (leadingIcon != null) Icon(leadingIcon, null, tint = if (focused) labelFocused else borderUnfocused) },
        trailingIcon = {
            if (trailingIcon != null && onTrailingClick != null) {
                IconButton(onClick = onTrailingClick) { Icon(trailingIcon, null, tint = if (focused) labelFocused else borderUnfocused) }
            } else if (trailingIcon != null) {
                Icon(trailingIcon, null, tint = if (focused) labelFocused else borderUnfocused)
            }
        },
        singleLine = singleLine,
        textStyle = LocalTextStyle.current.merge(MaterialTheme.typography.bodyLarge),
        shape = MaterialTheme.shapes.medium,
        interactionSource = src,
        isError = isError,
        supportingText = { if (supportingText != null) Text(supportingText, style = MaterialTheme.typography.labelMedium, color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = borderFocused,
            unfocusedBorderColor = borderUnfocused,
            focusedLabelColor = labelFocused,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            errorBorderColor = MaterialTheme.colorScheme.error,
            errorLabelColor = MaterialTheme.colorScheme.error,
            errorCursorColor = MaterialTheme.colorScheme.error,
            errorTextColor = MaterialTheme.colorScheme.onSurface
        ),
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onAny = { onImeAction() }
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = modifier
    )
}
