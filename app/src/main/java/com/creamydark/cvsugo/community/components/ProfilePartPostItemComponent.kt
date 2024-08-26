package com.creamydark.cvsugo.community.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.creamydark.cvsugo.community.feed.domain.data.PostData
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun ProfilePartPostItemComponent(modifier: Modifier = Modifier, userData: UserData, postData: PostData, dropDownActions:@Composable ()-> Unit) {
    Box(modifier = Modifier.fillMaxWidth()){
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier
                .size(38.dp)
                .background(color = Color.Gray, shape = CircleShape)){
                AsyncImage(
                    model = userData.profilePictureUri,
                    contentDescription = "",
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)

                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = userData.username,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = getRelativeTimeString(postData.theTimestamp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                )
            }
        }
        Box(modifier = Modifier.align(Alignment.CenterEnd)){
            dropDownActions()
        }
    }

}

private fun getRelativeTimeString(date: Date): String {
    val now = Date()
    val diffInMillis = now.time - date.time

    val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
    val days = TimeUnit.MILLISECONDS.toDays(diffInMillis)

    return when {
        minutes < 1 -> "Just now"
        minutes < 60 -> "$minutes minutes ago"
        hours < 24 -> "$hours hours ago"
        days < 7 -> "$days days ago"
        else -> SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(date)
    }
}