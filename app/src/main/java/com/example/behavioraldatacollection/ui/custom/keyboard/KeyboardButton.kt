package com.example.behavioraldatacollection.ui.custom.keyboard

import androidx.compose.animation.core.*
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.behavioraldatacollection.data.model.customkeybaord.Digit
import com.example.behavioraldatacollection.data.model.customkeybaord.Key
import com.example.behavioraldatacollection.data.model.customkeybaord.TextUtilityKey
import com.example.behavioraldatacollection.data.model.customkeybaord.UtilityKey
import com.example.behavioraldatacollection.utilities.handleCaseMode
import com.example.behavioraldatacollection.utilities.toggle
import kotlinx.coroutines.launch

@Composable
fun KeyboardButton(
    modifier: Modifier = Modifier,
    key: Key,
    requestFocus: Boolean,
    isUppercaseEnable: Boolean = false,
    isToggle: Boolean = false,
    wrapContent: Boolean = false,
    scaleAnimationEnabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onClick: (key: Key) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isToggleEnable = remember { mutableStateOf(isToggle) }
    val selected = remember { mutableStateOf(isFocused) }
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val conditionalModifier = remember {
        if (wrapContent)
            modifier
        else
            modifier.aspectRatio((key.span.toFloat() / 1F))
    }
    val scale = animateFloatAsState(
        targetValue = if ((selected.value || isFocused) && scaleAnimationEnabled) 1.2f else 1f,
        animationSpec = tween(
            durationMillis = 10,
            easing = LinearEasing
        ), label = ""
    )

    Button(
        onClick = {
            if (isToggle) {
                isToggleEnable.toggle()
            }
            onClick(key)
            coroutineScope.launch {
                focusRequester.requestFocus()
            }
        },
        contentPadding = contentPadding,
        shape = MaterialTheme.shapes.extraSmall,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFocused || isToggleEnable.value) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
            contentColor = if (isFocused || isToggleEnable.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
        ),
        elevation = ButtonDefaults.buttonElevation(
            pressedElevation = 0.dp,
            defaultElevation = 10.dp,
            focusedElevation = 30.dp
        ),
        modifier = conditionalModifier
            .scale(scale.value)
            .zIndex(if (isFocused) 10f else 1f)
            .focusRequester(focusRequester)
            .focusable(interactionSource = interactionSource)
            .padding(1.dp)
    ) {
        when (key) {
            is TextUtilityKey -> {
                Text(text = key.text, style = MaterialTheme.typography.bodySmall)
            }
            is UtilityKey -> {
                Icon(
                    key.icon,
                    contentDescription = key.text,
                    modifier = Modifier.size(16.dp)
                )
            }
            else -> {
                Text(
                    text = key.handleCaseMode(isUppercaseEnable),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (requestFocus) {
            focusRequester.requestFocus()
        }
    }
}

@Preview
@Composable
fun KeyboardButtonPreview() {
    KeyboardButton(key = Digit.Zero, requestFocus = false) {}
}