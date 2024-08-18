package com.creamydark.cvsugo.community.feed.presentation.feedlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.creamydark.cvsugo.community.feed.presentation.feedlist.intent.FeedListScreenState
import com.creamydark.cvsugo.community.feed.presentation.feedlist.viewmodel.FeedListViewModel


@Composable
fun FeedListRootScreen(modifier: Modifier = Modifier, viewModel: FeedListViewModel = hiltViewModel()) {
    FeedListScreen(modifier = modifier, state = viewModel.state)
}

@Composable
fun FeedListScreen(modifier: Modifier = Modifier,state: FeedListScreenState) {
    if (state.feedList.isEmpty()){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(text = "Feed is empty")
        }
    }else if (state.loading){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(text = "Loading")
        }
    } else{
        LazyColumn(modifier = modifier) {
            state.feedList.forEach {
                postData ->
            }
        }
    }
}