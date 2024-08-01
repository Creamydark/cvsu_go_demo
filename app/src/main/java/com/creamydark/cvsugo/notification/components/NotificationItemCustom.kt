package com.creamydark.cvsugo.notification.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun NotificationItemCustom(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit = {},
    title:String = "Hello",
    message:String = "This is supporting text",
    onCLicked:()->Unit={}
) {
    ListItem(
        modifier = modifier.clickable {
            onCLicked()
        },
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