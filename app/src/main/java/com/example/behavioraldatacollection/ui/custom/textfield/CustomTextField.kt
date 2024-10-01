package com.example.behavioraldatacollection.ui.custom.textfield

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    textState: MutableState<TextFieldValue>
) {
    val inputText = remember { textState }

    Box(
        modifier = modifier
            .border(
                BorderStroke(1.dp, Color.LightGray),
                shape = MaterialTheme.shapes.small
            )
            .defaultMinSize(minWidth = 300.dp)
            .padding(16.dp)
    ) {
        BasicTextField(
            value = inputText.value,
            onValueChange = { newText ->
                // Ensure that the number of lines does not exceed 5
                if (newText.text.lines().size <= 5) {
                    inputText.value = newText
                }
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            enabled = false
        )

        // Show placeholder text if the input is empty
        if (inputText.value.text.isEmpty()) {
            Text(text = "Start typing 😀", color = Color.Gray)
        }
    }
}