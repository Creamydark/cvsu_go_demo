package com.creamydark.cvsugo.community.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CreatePostOnFeedLayout(

) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(Color.White, shape = CircleShape)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Text Field
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Share anything on your mind ....") },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Icons Row
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .weight(0.3f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.Image, contentDescription = "Image")
            }
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.EmojiEmotions, contentDescription = "Emoji")
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Send Button
        IconButton(
            onClick = { /* TODO */ },
            modifier = Modifier
                .background(Color.Blue, shape = CircleShape)
                .padding(8.dp)
        ) {
            Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White)
        }
    }
}


@Preview
@Composable
private fun Prev() {
    CreatePostOnFeedLayout()

}