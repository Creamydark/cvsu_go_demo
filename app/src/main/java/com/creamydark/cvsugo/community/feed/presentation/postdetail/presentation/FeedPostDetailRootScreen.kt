package com.creamydark.cvsugo.community.feed.presentation.postdetail.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.creamydark.cvsugo.community.feed.presentation.postdetail.state.FeedPostDetailScreenState
import com.creamydark.cvsugo.community.feed.presentation.postdetail.viewmodel.FeedPostDetailScreenViewmodel

@Composable
fun FeedPostDetailRootScreen(modifier: Modifier = Modifier, viewModel: FeedPostDetailScreenViewmodel) {
    FeedPostDetailScreen(
        modifier = modifier, state = viewModel.state
    )
}

@Composable
fun FeedPostDetailScreen(modifier: Modifier = Modifier,state: FeedPostDetailScreenState= FeedPostDetailScreenState()) {

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        val i = state.post?.attachments?: emptyList()

        items(items = i){
            url ->
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = url,
                contentDescription = url,
                contentScale = ContentScale.Fit
            )
        }

    }
}