package com.example.behavioraldatacollection.ui.custom.keyboard

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.behavioraldatacollection.data.KeysDataSource
import com.example.behavioraldatacollection.domain.model.Key

@Composable
fun EmailSuggestionsRow(
    textFieldState: MutableState<TextFieldValue>?,
    onKeyPress: (key: Key) -> Unit
) {
    LazyRow(
        modifier = Modifier.wrapContentSize(),
    ) {
        items(KeysDataSource.emailSuggestions) { key ->
            KeyboardButton(
                modifier = Modifier
                    .wrapContentSize(unbounded = true, align = Alignment.TopStart)
                    .height(40.dp),
                key = key,
                requestFocus = false,
                isUppercaseEnable = false,
                isToggle = false,
                wrapContent = true,
                scaleAnimationEnabled = false,
                contentPadding = PaddingValues(4.dp)
            ) {
                onKeyPress(key)
                processKeys(key, textFieldState, false)
            }
        }
    }
}