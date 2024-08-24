package com.creamydark.cvsugo.community.feed.presentation.createpost.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.creamydark.cvsugo.community.feed.presentation.createpost.presentation.intent.CreatePostScreenIntent
import com.creamydark.cvsugo.community.feed.presentation.createpost.presentation.state.CreatePostScreenState
import com.creamydark.cvsugo.community.feed.presentation.createpost.presentation.vewmodel.CreatePostScreenViewmodel


@Composable
fun CreatePostRootScreen(modifier: Modifier = Modifier,viewmodel : CreatePostScreenViewmodel = hiltViewModel()) {
    CreatePostScreen(modifier = modifier, state = viewmodel.state,onEvent = viewmodel::onEvent)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CreatePostScreen(
    modifier: Modifier = Modifier,
    state: CreatePostScreenState = CreatePostScreenState(),
    onEvent: (CreatePostScreenIntent) -> Unit = {}
) {



    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->

        onEvent(CreatePostScreenIntent.OnGetImages(uris))

    }

    Box(modifier = modifier
        .fillMaxSize()
        .padding(16.dp),contentAlignment = Alignment.TopStart) {
        Column {
            Box{
                if (state.content.isEmpty()) {
                    Text(
                        text = "body text (optional)",
                        style = TextStyle(color = Color.Gray)
                    )
                }

                BasicTextField(
                    value = state.content,
                    onValueChange = {
                        onEvent(CreatePostScreenIntent.OnContentChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 130.dp),
                    textStyle = TextStyle(color = Color.Black)
                )
            }



            //TODO: attachments
            HorizontalDivider()
            if (state.attachments.isEmpty()){
                IconButton(onClick = { launcher.launch("image/*") }) {
                    Icon(imageVector = Icons.Outlined.Image, contentDescription = "")
                }
            }else{
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    itemsIndexed(items = state.attachments) {
                        index, uri ->
                        ImageAttachmentItem(
                            model = uri,
                            onRemove = {
                                onEvent(CreatePostScreenIntent.OnRemoveImage(index))
                            },
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    item {
                        IconButton(onClick = { launcher.launch("image/*") }) {
                            Icon(imageVector = Icons.Outlined.Add, contentDescription = "")
                        }
                    }
                }
            }

        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onClick = { onEvent(CreatePostScreenIntent.OnPublishClicked) },
            enabled = !state.loadingPublishBTN,
        ) {
            Row {
                if(state.loadingPublishBTN){
                    CircularProgressIndicator(modifier= Modifier.size(24.dp))
                    Spacer(modifier = modifier.width(8.dp))
                }
                Text(text = "Publish")
            }
        }
    }
}

@Composable
private fun ImageAttachmentItem(model: Any?,modifier: Modifier = Modifier,onRemove:()->Unit) {
    Box(
        modifier = modifier
            .sizeIn(maxWidth = 100.dp, maxHeight = 100.dp)
            .background(Color.Gray),
    ){
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = model,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        IconButton(
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.TopEnd)
                .background(Color.Black, shape = CircleShape)
                .size(24.dp)
                .padding(4.dp),
            onClick = { onRemove() },
        ) {
            Icon(tint = Color.White,imageVector = Icons.Outlined.Clear, contentDescription = "")
        }


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    CreatePostScreen()
}