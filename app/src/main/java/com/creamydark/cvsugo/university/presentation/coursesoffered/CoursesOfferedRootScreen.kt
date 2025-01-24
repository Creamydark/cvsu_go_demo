package com.creamydark.cvsugo.university.presentation.coursesoffered

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.creamydark.cvsugo.core.presentation.rootscreen.LocalNavController
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.RoutesV2
import com.creamydark.cvsugo.university.domain.dataclass.CoursesOfferedData
import com.creamydark.cvsugo.university.presentation.coursesoffered.intent.CoursesOfferedScreenIntent
import com.creamydark.cvsugo.university.presentation.coursesoffered.state.CoursesOfferedScreenState
import com.creamydark.cvsugo.university.presentation.coursesoffered.viewmodel.CoursesOfferedViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CoursesOfferedRootScreen(
    modifier: Modifier = Modifier,
    viewModel: CoursesOfferedViewModel = hiltViewModel()
) {

    val navHostController = LocalNavController.current
    CoursesOfferedScreen(
        state = viewModel.state,
        intent = {
            //intent
            intent ->
            when (intent) {
                is CoursesOfferedScreenIntent.NavigateToDetail -> {
                    navHostController.navigate(RoutesV2.CoursesOfferDetailScreen.route.plus("/${intent.id}")){
                        launchSingleTop = true
                    }
                }
            }
        },
    )
}


@Composable
private fun CoursesOfferedScreen(
    modifier: Modifier = Modifier,
    state: CoursesOfferedScreenState,
    intent: (CoursesOfferedScreenIntent) -> Unit = {}
) {

    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {

        items(
            items = state.coursesOfferedList,
            key = { item ->
                // Return a stable + unique key for the item
                item.id
            }
        ) { item ->
            CoursesOfferedItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp),
                data = item
            ){
                //intent
                intent(CoursesOfferedScreenIntent.NavigateToDetail(item.id))
            }
            Spacer(modifier = Modifier.size(14.dp))
        }
    }
}


@Composable
fun CoursesOfferedItem(modifier: Modifier = Modifier, data: CoursesOfferedData, onClick:()->Unit={}) {
    ElevatedCard(
        modifier = modifier,
//        colors = CardDefaults.cardColors().copy(containerColor = data.bgColor),
        onClick = { onClick() },

    ) {
        Column {
            AsyncImage(
                model = data.imgUrl,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(16.dp)),

            )
            /*Text(
                modifier = Modifier.padding(16.dp),
                text = data.courseName,
                style = MaterialTheme.typography.titleMedium.copy(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.5f),
                        blurRadius = 12f
                    )
                ),
                fontWeight = FontWeight.Bold,
                color = Color(0xfffae10d)
            )*/
        }
    }
}