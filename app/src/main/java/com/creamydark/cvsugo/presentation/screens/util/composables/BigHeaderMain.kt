package com.creamydark.cvsugo.presentation.screens.util.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BigHeaderMain(modifier: Modifier = Modifier,text:String) {
    Text(
        modifier = modifier.padding(vertical = 22.dp),
        text = text,
        style = MaterialTheme.typography.headlineLarge,
        fontSize = 42.sp,
        lineHeight = 42.sp,
        textAlign = TextAlign.Left,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
    )
}