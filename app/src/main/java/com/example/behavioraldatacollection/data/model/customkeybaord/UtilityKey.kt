package com.example.behavioraldatacollection.data.model.customkeybaord

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardBackspace
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardCapslock
import androidx.compose.material.icons.outlined.SpaceBar
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.behavioraldatacollection.data.KeysConstants

sealed class UtilityKey(
    open val icon: ImageVector,
    override val text: String,
    override val span: Int = 1
) : Key {
    data object Uppercase : UtilityKey(
        Icons.Outlined.KeyboardCapslock,
        KeysConstants.UPPER_CASE_KEY
    )

    data object Backspace : UtilityKey(
        Icons.AutoMirrored.Outlined.KeyboardBackspace,
        KeysConstants.BACK_SPACE_KEY
    )

    data object Clear : UtilityKey(
        Icons.Outlined.Delete,
        KeysConstants.CLEAR_KEY,
        2
    )

    data object Space : UtilityKey(
        Icons.Outlined.SpaceBar,
        KeysConstants.SPACE_KEY,
        3
    )

    data object ActionArrow : UtilityKey(
        Icons.Outlined.Check,
        KeysConstants.SEARCH_KEY,
        2
    )
}