
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.creamydark.cvsugo.community.components.ExpandableTextComponent
import com.creamydark.cvsugo.community.components.FeedItemDropdownActions
import com.creamydark.cvsugo.community.components.ProfilePartPostItemComponent
import com.creamydark.cvsugo.community.feed.domain.data.PostData
import com.creamydark.cvsugo.community.feed.presentation.postdetail.presentation.event.FeedPostDetailScreenEvent
import com.creamydark.cvsugo.community.feed.presentation.postdetail.presentation.intent.FeedPostDetailScreenIntent
import com.creamydark.cvsugo.community.feed.presentation.postdetail.presentation.state.FeedPostDetailScreenState
import com.creamydark.cvsugo.community.feed.presentation.postdetail.presentation.viewmodel.FeedPostDetailScreenViewmodel
import com.creamydark.cvsugo.community.feed.utils.FeedItemActions
import com.creamydark.cvsugo.core.presentation.rootscreen.LocalNavController
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.CommunityRoutesItems
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import kotlinx.coroutines.flow.collectLatest


@Composable
fun FeedPostDetailRootScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedPostDetailScreenViewmodel
) {
    val navController = LocalNavController.current

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(key1 = lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED){
            viewModel.receiver.collectLatest {
                intent->
                when (intent) {
                    FeedPostDetailScreenEvent.DeleteFailure -> {
                        Toast.makeText(context, "Failed to delete post", Toast.LENGTH_SHORT).show()
                    }
                    FeedPostDetailScreenEvent.DeleteSuccess -> {
                        navController.navigateUp()
                    }
                }
            }
        }
    }

    FeedPostDetailScreen(
        modifier = modifier,
        state = viewModel.state,
        intent = viewModel::onIntent
    )

}


private fun navigateToEditScreen(navController: NavController, postId: String){
    navController.navigate(CommunityRoutesItems.EditPost.route.plus("/$postId")){
        launchSingleTop = true
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun FeedPostDetailScreen(
    modifier: Modifier = Modifier,
    state: FeedPostDetailScreenState = FeedPostDetailScreenState(),
    intent: (FeedPostDetailScreenIntent) -> Unit = {},
) {
    val attachments = state.post?.attachments ?: emptyList()

    var expanded by remember { mutableStateOf(false) }

    val navController = LocalNavController.current

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.loading,
        onRefresh = {
            intent(
                FeedPostDetailScreenIntent.OnRefresh,
            )
        },
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
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
                        if (state.showEditButton){
                            IconButton(onClick = { expanded = true }) {
                                Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = "")
                                FeedItemDropdownActions(onDismiss = { expanded = false }, expanded = expanded ) {
                                        onItemSelected ->
                                    when (onItemSelected){
                                        FeedItemActions.Edit ->{
                                            navigateToEditScreen(navController, state.post?.postId ?: "")
                                        }
                                        FeedItemActions.Delete ->{
                                            intent(FeedPostDetailScreenIntent.Delete)
                                        }
                                    }
                                    expanded = false
                                }
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

            }

            item {
                ExpandableTextComponent(
                    text = state.post?.content ?: "",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
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
        PullRefreshIndicator(modifier = Modifier.align(Alignment.TopCenter), refreshing = state.loading, state = pullRefreshState )

    }
}
