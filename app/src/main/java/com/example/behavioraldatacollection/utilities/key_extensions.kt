package com.example.behavioraldatacollection.utilities

import com.example.behavioraldatacollection.data.KeysDataSource
import com.example.behavioraldatacollection.data.model.customkeybaord.NumericUtilityKey
import com.example.behavioraldatacollection.data.model.customkeybaord.TextUtilityKey
import com.example.behavioraldatacollection.data.model.customkeybaord.UtilityKey
import com.example.behavioraldatacollection.data.model.customkeybaord.Key

fun Key.isBackspace() = this is UtilityKey.Backspace || this is NumericUtilityKey.Backspace
fun Key.isUppercase() = this is UtilityKey.Uppercase
fun Key.isToggleKey() = KeysDataSource.toggleKeys.contains(this)
fun Key.isAction() = this is UtilityKey.ActionArrow
fun Key.isNumeric() = this is TextUtilityKey.Numeric
fun Key.isAbc() = this is TextUtilityKey.ABC
fun Key.isClear() = this is UtilityKey.Clear
fun Key.isSpecialCharacters() = this is TextUtilityKey.SpecialCharacters
fun Key.handleCaseMode(isUppercaseEnabled: Boolean) =
    if (isUppercaseEnabled)
        text.uppercase()
    else
        text.lowercase()