package com.example.behavioraldatacollection.ui.custom.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.behavioraldatacollection.data.KeysDataSource
import com.example.behavioraldatacollection.data.model.customkeybaord.Key
import com.example.behavioraldatacollection.utilities.append
import com.example.behavioraldatacollection.utilities.clear
import com.example.behavioraldatacollection.utilities.handleCaseMode
import com.example.behavioraldatacollection.utilities.isAbc
import com.example.behavioraldatacollection.utilities.isAction
import com.example.behavioraldatacollection.utilities.isBackspace
import com.example.behavioraldatacollection.utilities.isClear
import com.example.behavioraldatacollection.utilities.isNumeric
import com.example.behavioraldatacollection.utilities.isSpecialCharacters
import com.example.behavioraldatacollection.utilities.isToggleKey
import com.example.behavioraldatacollection.utilities.isUppercase
import com.example.behavioraldatacollection.utilities.toggle
import com.example.behavioraldatacollection.utilities.updateAndRemoveLastChar

@Composable
fun KeyboardView(
    modifier: Modifier = Modifier,
    textFieldState: MutableState<TextFieldValue>?,
    focusFirstKey: Boolean = false,
    enableEmailSuggestions: Boolean = true,
    onAction: ((key: Key) -> Unit)? = null,
    onKeyPress: (key: Key) -> Unit
) {
    val focusKey = remember { mutableStateOf(focusFirstKey) }
    val isUppercase = remember { mutableStateOf(false) }
    val isNumeric = remember { mutableStateOf(false) }
    val isSpecialCharacters = remember { mutableStateOf(false) }
    val alphabets = remember { mutableStateOf(KeysDataSource.normalKeys) }
    val numericKeys = remember { mutableStateOf(KeysDataSource.numericKeys) }
    val specialCharactersKeys = remember { mutableStateOf(KeysDataSource.specialCharactersKeys) }

    val keys by rememberUpdatedState(
        if (isNumeric.value) {
            numericKeys.value
        } else if (isSpecialCharacters.value) {
            specialCharactersKeys.value
        } else {
            alphabets.value
        }
    )
    Column(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium
            )
    ) {
        if (enableEmailSuggestions)
            EmailSuggestionsRow(textFieldState) {}

        LazyVerticalGrid(
            columns = GridCells.Fixed(10)
        ) {
            items(keys.size,
                key = { index ->
                    keys[index].text
                },
                span = { index ->
                    GridItemSpan(keys[index].span)
                }) { index ->
                KeyboardButton(
                    key = keys[index],
                    requestFocus = focusKey.value && index == 0,
                    isUppercaseEnable = isUppercase.value,
                    isToggle = keys[index].isToggleKey(),
                ) {
                    if (it.isUppercase()) {
                        isUppercase.toggle()
                    } else if (it.isAction()) {
                        onAction?.invoke(it)
                    } else if (it.isSpecialCharacters()) {
                        isSpecialCharacters.toggle()
                        isNumeric.value = false
                    } else if (it.isNumeric() || it.isAbc()) {
                        isNumeric.toggle()
                        isSpecialCharacters.value = false
                    } else {
                        onKeyPress(it)
                        processKeys(it, textFieldState, isUppercase.value)
                    }
                }
            }
        }
    }
}

fun processKeys(it: Key, state: MutableState<TextFieldValue>?, isUppercase: Boolean) {
    if (it.isBackspace()) {
        state?.updateAndRemoveLastChar()
    } else if (it.isClear()) {
        state?.clear()
    } else {
        state?.append(it.handleCaseMode(isUppercase))
    }
}

@Preview
@Composable
fun KeyboardViewPreview() {
    KeyboardView(textFieldState = null) {}
}