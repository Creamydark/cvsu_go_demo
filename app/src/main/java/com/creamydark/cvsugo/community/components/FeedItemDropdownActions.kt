package com.creamydark.cvsugo.community.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.creamydark.cvsugo.community.feed.utils.FeedItemActions



@Composable
fun FeedItemDropdownActions(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onDismiss: () -> Unit,
    onItemSelected:(FeedItemActions) -> Unit
) {
    DropdownMenu(modifier = modifier, expanded = expanded, onDismissRequest = { onDismiss() }) {
        FeedItemActions.entries.forEach {
                option ->
            DropdownMenuItem(
                leadingIcon = {
                    Icon(imageVector = option.icon, contentDescription = "")
                },
                text = { Text(text = option.name) },
                onClick = { onItemSelected(option) }
            )
        }
    }
}