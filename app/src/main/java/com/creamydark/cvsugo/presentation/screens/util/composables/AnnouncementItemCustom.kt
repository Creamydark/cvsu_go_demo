package com.creamydark.cvsugo.presentation.screens.util.composables

import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AnnouncementItemCustom(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit = {},
    title:String = "Hello",
    message:String = "This is supporting text"
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        },
        leadingContent = {
            icon()
        },
        supportingContent = {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    )
}