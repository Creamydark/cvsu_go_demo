package com.creamydark.cvsugo.community.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.creamydark.cvsugo.community.feed.domain.data.PostData
import com.creamydark.cvsugo.community.feed.utils.FeedItemActions
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import com.google.accompanist.pager.HorizontalPagerIndicator
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun FeedItem(
    modifier: Modifier = Modifier,
    postData: PostData,
    showMore: Boolean = false,
    actions: (FeedItemActions) -> Unit = {}
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Column(modifier) {
        ProfilePart(userData = postData.userData, postData = postData){
            if (showMore) {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = "")
                    DropDownActions(expanded = expanded, onDismiss = { expanded = false }) {
                        actions(it)
                        expanded = false
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Card(modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
            Column(
                Modifier.padding(16.dp)
            ) {
                ExpandableText(
                    text = postData.content,
                    modifier = Modifier,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}
@Composable
fun FeedItem2(
    modifier: Modifier = Modifier,
    postData: PostData,
    showMore: Boolean = false,
    actions: (FeedItemActions) -> Unit = {},
    onItemClick: (index: Int) -> Unit = {}
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Column(modifier) {
        ProfilePart(userData = postData.userData, postData = postData){
            if (showMore) {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = "")
                    DropDownActions(expanded = expanded, onDismiss = { expanded = false }) {
                        actions(it)
                        expanded = false
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (postData.content.isNotEmpty()){
            ExpandableText(
                text = postData.content,
                modifier = Modifier,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        if (postData.attachments.size > 1){
            FlowImages(images = postData.attachments, onItemClick = { onItemClick(it) })
        }else{
            AsyncImage(
                model = postData.attachments[0],
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 150.dp)
                    .aspectRatio(0.8f).clip(RoundedCornerShape(12.dp))
                    .clickable { onItemClick(0) },
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}





@Composable
private fun ProfilePart(modifier: Modifier = Modifier, userData: UserData, postData: PostData, dropDownActions:@Composable ()-> Unit) {
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


@Composable
private fun DropDownActions(
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


@Composable
private fun BodyText(postText: String, maxLines: Int = 3) {
    var isExpanded by remember { mutableStateOf(false) }

    Column() {
        Text(
            text = postText,
            maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Unspecified,
            modifier = Modifier
                .heightIn(min = 0.dp, max = if (isExpanded) Dp.Unspecified else 100.dp)
                .clipToBounds()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { isExpanded = !isExpanded }) {
            Text(if (isExpanded) "See Less" else "See More")
        }
    }
}

@Composable
private fun ExpandableText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    fontStyle: FontStyle? = null,
    text: String,
    collapsedMaxLine: Int = 3,
    showMoreText: String = "... Show More",
    showMoreStyle: SpanStyle = SpanStyle(
        fontWeight = FontWeight.W500,
        color = MaterialTheme.colorScheme.tertiary // Change this to your desired color
    ),
    showLessText: String = "\n\nShow Less",
    showLessStyle: SpanStyle = SpanStyle(
        fontWeight = FontWeight.W500,
        color = MaterialTheme.colorScheme.tertiary // Change this to your desired color
    ),
    textAlign: TextAlign? = null,
    fontSize: TextUnit
) {
    // State variables to track the expanded state, clickable state, and last character index.
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableIntStateOf(0) }

    // Box composable containing the Text composable.
    Box(modifier = Modifier
        .clickable(clickable) {
            isExpanded = !isExpanded
        }
        .then(modifier)
    ) {
        // Text composable with buildAnnotatedString to handle "Show More" and "Show Less" buttons.
        Text(
            modifier = textModifier
                .fillMaxWidth()
                .animateContentSize(),
            text = buildAnnotatedString {
                if (clickable) {
                    if (isExpanded) {
                        // Display the full text and "Show Less" button when expanded.
                        append(text)
                        withStyle(style = showLessStyle) { append(showLessText) }
                    } else {
                        // Display truncated text and "Show More" button when collapsed.
                        val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                            .dropLast(showMoreText.length)
                            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                        append(adjustText)
                        withStyle(style = showMoreStyle) { append(showMoreText) }
                    }
                } else {
                    // Display the full text when not clickable.
                    append(text)
                }
            },
            // Set max lines based on the expanded state.
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            fontStyle = fontStyle,
            // Callback to determine visual overflow and enable click ability.
            onTextLayout = { textLayoutResult ->
                if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                    clickable = true
                    lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                }
            },
            style = style,
            textAlign = textAlign,
            fontSize = fontSize
        )
    }
}


@Composable
private fun SlidingImages(modifier: Modifier = Modifier,images:List<String>) {
    val pagerstate = rememberPagerState(pageCount = {images.size})
    Box(modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 150.dp)
        .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
        .clip(RoundedCornerShape(8.dp)),){
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth(),
            state = pagerstate
        ) {
            index->
            AsyncImage(
                modifier = modifier.fillMaxWidth(),
                model = images[index],
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
        }
        if (images.size > 1){
            HorizontalPagerIndicator(modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter), pagerState = pagerstate, pageCount = images.size
            )
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FlowImages(modifier: Modifier = Modifier,images:List<String>,onItemClick: (index: Int) -> Unit) {
    val imagesToDisplay = images.take(3)
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        maxItemsInEachRow = 2
    ) {
        val itemModifier = Modifier
            .heightIn(min = 200.dp,max = 250.dp)
            .clip(RoundedCornerShape(8.dp))
            .weight(1f)
            .background(color = Color.Gray.copy(alpha = 0.5f),shape = RoundedCornerShape(8.dp))

        imagesToDisplay.forEachIndexed {index, item ->
            // if the item is the third item, don't use weight modifier, but rather fillMaxWidth
            AsyncImage(
                model = item,
                contentDescription = "",
                modifier = itemModifier.clickable { onItemClick(index) },
                contentScale = ContentScale.Crop
            )
        }

        if (images.size > 3) {
            // Add "More" button if there are more than 3 images
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .width(80.dp)
                    .fillMaxRowHeight()
                    .clickable {  onItemClick(0) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+${images.size - 3}",
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
private fun PageIndicator(modifier: Modifier = Modifier,pageCount:Int,currentPage:Int) {
    Row(modifier = modifier
        .padding(horizontal = 4.dp)
        .background(color = Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(100))) {
        Text(text = "$currentPage/$pageCount", fontSize = 8.sp)
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
