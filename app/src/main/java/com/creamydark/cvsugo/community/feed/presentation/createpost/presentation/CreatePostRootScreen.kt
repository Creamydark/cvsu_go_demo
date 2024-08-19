package com.creamydark.cvsugo.community.feed.presentation.createpost.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.creamydark.cvsugo.community.feed.presentation.createpost.presentation.intent.CreatePostScreenIntent
import com.creamydark.cvsugo.community.feed.presentation.createpost.presentation.state.CreatePostScreenState
import com.creamydark.cvsugo.community.feed.presentation.createpost.presentation.vewmodel.CreatePostScreenViewmodel
import com.stevdzasan.onetap.getUserFromTokenId


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
    getUserFromTokenId("")
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    CreatePostScreen()
}