package com.example.behavioraldatacollection.data.model.customkeybaord

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Numbers
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.behavioraldatacollection.data.KeysConstants

sealed class TextUtilityKey(
    override val icon: ImageVector,
    override val text: String,
    override val span: Int = 1
) : UtilityKey(icon, text, span) {

    data object Numeric : TextUtilityKey(
        Icons.Outlined.Numbers,
        KeysConstants.NUMERIC_KEY
    )

    data object SpecialCharacters : TextUtilityKey(
        Icons.Outlined.Numbers,
        KeysConstants.SPECIAL_CHARACTERS_KEY,
    )

    data object ABC : TextUtilityKey(
        Icons.Outlined.Numbers,
        KeysConstants.ABC_KEY,
    )
}