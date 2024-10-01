package com.example.behavioraldatacollection.ui.custom.textfield

import androidx.compose.foundation.focusable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun TvTextField(
    value: MutableState<TextFieldValue>,
    label: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (TextFieldValue) -> Unit
) {
    OutlinedTextField(
        value.value,
        maxLines = 1,
        modifier = Modifier.focusable(false),
        readOnly = true,
        enabled = false,
        textStyle = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Thin
        ),
        label = { Text(text = label) },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        onValueChange = {
            onValueChange(it)
        }
    )
}