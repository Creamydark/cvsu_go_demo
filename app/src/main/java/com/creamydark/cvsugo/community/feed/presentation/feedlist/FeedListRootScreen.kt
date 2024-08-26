package com.creamydark.cvsugo.community.feed.presentation.feedlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.creamydark.cvsugo.community.components.FeedItem
import com.creamydark.cvsugo.community.components.FeedItem2
import com.creamydark.cvsugo.community.feed.domain.data.PostData
import com.creamydark.cvsugo.community.feed.presentation.feedlist.intent.FeedListScreenIntent
import com.creamydark.cvsugo.community.feed.presentation.feedlist.state.FeedListScreenState
import com.creamydark.cvsugo.community.feed.presentation.feedlist.viewmodel.FeedListViewModel
import com.creamydark.cvsugo.community.feed.utils.FeedItemActions
import com.creamydark.cvsugo.core.presentation.rootscreen.LocalNavController
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.CommunityRoutesItems


@Composable
fun FeedListRootScreen(modifier: Modifier = Modifier, viewModel: FeedListViewModel = hiltViewModel()) {

    val navhostController = LocalNavController.current

    val data = viewModel.itemsData.collectAsLazyPagingItems()

    FeedListScreen(
        modifier = modifier, state = viewModel.state,
        list = data,
        onIntent = {
            intent->
            when (intent) {
                is FeedListScreenIntent.NavigateToDetail -> {
                    navigateToDetail(navhostController, intent.postId)
                }
                else -> Unit
            }
            viewModel.onIntent(intent)
        }
    )
}

@Composable
fun FeedListScreen(
    modifier: Modifier = Modifier,
    state: FeedListScreenState,
    list:LazyPagingItems<PostData>,
    onIntent: (FeedListScreenIntent) -> Unit
) {

    val navController = LocalNavController.current


    val listState = rememberLazyListState()

    // Trigger loading more data when scrolled to the end
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex == list.itemCount - 1 && !state.isRefreshing) {
                    onIntent(FeedListScreenIntent.OnRefresh)
                }
            }
    }

    LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = Icons.Outlined.PeopleOutline,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Explore",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.height(18.dp))

        }
        items(items = list.itemSnapshotList.items, key = { it.postId }){
            postData->

            val showMore = postData.userData.uid == state.currentUser?.uid

            if (postData.attachments.isEmpty()){
                FeedItem(postData = postData, showMore = showMore){
                        feedItemActions: FeedItemActions ->
                    when (feedItemActions) {
                        FeedItemActions.Edit ->{
                            navigateToEditScreen(navController, postData.postId)
                        }
                        FeedItemActions.Delete ->{
                            onIntent(FeedListScreenIntent.OnDeletePost(postData))
                        }
                    }
                }
            }else{
                FeedItem2(
                    postData = postData,
                    showMore = showMore,
                    onItemClick = {
                        onIntent(FeedListScreenIntent.NavigateToDetail(postData.postId))
                    },
                    actions = {
                            feedItemActions: FeedItemActions ->
                        when (feedItemActions) {
                            FeedItemActions.Edit ->{
                                navigateToEditScreen(navController, postData.postId)
                            }
                            FeedItemActions.Delete ->{
                                onIntent(FeedListScreenIntent.OnDeletePost(postData))
                            }
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        list.apply {
            when{
                loadState.append is LoadState.Loading -> {
                    // Display a loading indicator at the bottom
                    item {
                        // Show a loading indicator at the bottom
                        // You can use a CircularProgressIndicator or any other loading indicator
                        // For example:
                        Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp).align(Alignment.Center))
                        }
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    // Handle error state
                    val error = (loadState.refresh as LoadState.Error).error
                    item {
                        // Display an error message
                        Text(text = "Error: $error")
                    }
                }
            }
        }
    }

}

private fun navigateToEditScreen(navController: NavController, postId: String){
    navController.navigate(CommunityRoutesItems.EditPost.route.plus("/$postId")){
        launchSingleTop = true
    }
}
private fun navigateToDetail(navhostController: NavHostController, postId: String){
    navhostController.navigate(CommunityRoutesItems.PostDetail.route.plus("/$postId")){
        launchSingleTop = true
    }
}
