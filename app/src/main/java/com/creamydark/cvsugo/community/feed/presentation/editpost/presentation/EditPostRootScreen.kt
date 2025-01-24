package com.creamydark.cvsugo.community.feed.presentation.editpost.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import com.creamydark.cvsugo.community.components.ProfilePartPostItemComponent
import com.creamydark.cvsugo.community.feed.domain.data.PostData
import com.creamydark.cvsugo.community.feed.presentation.editpost.event.EditPostScreenEvent
import com.creamydark.cvsugo.community.feed.presentation.editpost.intent.EditPostScreenIntent
import com.creamydark.cvsugo.community.feed.presentation.editpost.state.EditPostScreenState
import com.creamydark.cvsugo.community.feed.presentation.editpost.viewmodel.EditPostScreenViewmodel
import com.creamydark.cvsugo.core.presentation.rootscreen.LocalNavController
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditPostRootScreen(modifier: Modifier = Modifier,viewmodel: EditPostScreenViewmodel) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val navController = LocalNavController.current

    LaunchedEffect(key1 = lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED){
            viewmodel.receiver.collectLatest {
                result->
                when (result) {
                    EditPostScreenEvent.UpdateFailure -> {
                        Toast.makeText(navController.context, "Failed to update post", Toast.LENGTH_SHORT).show()
                    }
                    EditPostScreenEvent.UpdateSuccess -> {
                        navController.navigateUp()
                    }

                    EditPostScreenEvent.LoadingFailed -> {
                        Toast.makeText(navController.context, "Failed to load post", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    EditPostScreen(
        modifier = modifier,
        state = viewmodel.state,
        intent = viewmodel::onIntent
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun EditPostScreen(
    modifier: Modifier = Modifier,
    state: EditPostScreenState = EditPostScreenState(),
    intent: (EditPostScreenIntent) -> Unit = {},
) {

    val attachments = state.post?.attachments ?: emptyList()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.loading,
        onRefresh = { intent(EditPostScreenIntent.OnRefresh) }
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState,false)){
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                // Add a button to toggle Edit Mode
                ProfilePartPostItemComponent(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    userData = state.post?.userData ?: UserData(),
                    postData = state.post ?: PostData(),
                    dropDownActions = {
                        // Example toggle button
                        IconButton(enabled = !state.updating, onClick = { intent(EditPostScreenIntent.OnPublishClicked) }) {
                            if (state.updating) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            }else{
                                Icon(imageVector = Icons.Outlined.Done, contentDescription = "")
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.padding(16.dp)){

                    if ((state.post?.content?.isEmpty() == true)) {
                        Text(
                            text = "body text (optional)",
                            style = TextStyle(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                        )
                    }

                    BasicTextField(
                        value = state.post?.content ?: "",
                        onValueChange = {
                            intent(EditPostScreenIntent.OnContentChanged(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 130.dp),
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(items = attachments) { url ->
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = url,
                    contentDescription = url,
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = state.loading,
            state = pullRefreshState
        )
    }
}