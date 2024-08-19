package com.creamydark.cvsugo.community.feed.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.ui.graphics.vector.ImageVector

enum class FeedItemActions(val icon: ImageVector) {
    Edit(icon = Icons.Outlined.Edit),
    Delete(icon = Icons.Outlined.Delete)
}