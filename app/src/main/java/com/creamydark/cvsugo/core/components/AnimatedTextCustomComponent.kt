package com.creamydark.cvsugo.core.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedTextCustomComponent(
    modifier: Modifier = Modifier,
    text: String,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = text,
        transitionSpec = {
            (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                slideOutHorizontally { width -> -width } + fadeOut())
        }, label = ""
    ) { currentText ->
        Text(
            text = currentText,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}