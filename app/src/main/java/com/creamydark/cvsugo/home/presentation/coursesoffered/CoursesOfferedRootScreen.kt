package com.creamydark.cvsugo.home.presentation.coursesoffered

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.creamydark.cvsugo.home.domain.dataclass.CourseData
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.HomeScreens
import com.creamydark.cvsugo.home.presentation.coursesoffered.viewmodel.CourseDetailViewModel


@Composable
fun CoursesOfferedRootScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: CourseDetailViewModel = hiltViewModel()
) {

    val coursesofferedList by viewModel.coursesMutableList.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
        item {
            Text(
                modifier = Modifier.padding(vertical = 22.dp),
                text = "Courses Offered",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 42.sp,
                lineHeight = 42.sp,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
            )
        }
        items(
            items = coursesofferedList,
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
                //onClicked
                navHostController.navigate(HomeScreens.CoursesOfferDetailScreen.route.plus("/${item.id}")){
                    launchSingleTop = true
                }
            }
            Spacer(modifier = Modifier.size(14.dp))
        }
    }
}



@Composable
fun CoursesOfferedItem(modifier: Modifier = Modifier, data: CourseData, onClick:()->Unit={}) {
    ElevatedCard(
        modifier = modifier,
//        colors = CardDefaults.cardColors().copy(containerColor = data.bgColor),
        onClick = { onClick() },

    ) {
        Column {
            AsyncImage(
                model = data.courseBannerImgUrl,
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