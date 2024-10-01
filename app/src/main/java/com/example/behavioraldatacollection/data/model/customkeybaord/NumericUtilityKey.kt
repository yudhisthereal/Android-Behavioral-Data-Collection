package com.example.behavioraldatacollection.data.model.customkeybaord

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.SpaceBar
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.behavioraldatacollection.data.KeysConstants

sealed class NumericUtilityKey(
    override val icon: ImageVector,
    override val text: String,
    override val span: Int = 1
) : UtilityKey(icon, text, span) {

    data object Backspace : NumericUtilityKey(
        Icons.AutoMirrored.Outlined.ArrowBack,
        KeysConstants.BACK_SPACE_KEY
    )

    data object Space : NumericUtilityKey(
        Icons.Outlined.SpaceBar,
        KeysConstants.SPACE_KEY
    )

    data object RightArrow :
        NumericUtilityKey(
            Icons.AutoMirrored.Outlined.ArrowForward,
            KeysConstants.RIGHT_ARROW_KEY
        )
}