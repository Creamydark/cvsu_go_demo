package com.creamydark.cvsugo.presentation.screens.home


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import coil.util.DebugLogger
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.domain.dataclass.CoursesOfferedData
import com.creamydark.cvsugo.presentation.navgraphs.CoursesScreens
import com.creamydark.cvsugo.presentation.screens.home.viewmodel.HomeViewModel




@Composable
fun HomeRootScreen(navHostController: NavHostController,modifier: Modifier = Modifier,viewModel: HomeViewModel = hiltViewModel()) {
    val coursesofferedList by viewModel.coursesOfferList.collectAsStateWithLifecycle()
    val partnersLogosUrl by viewModel.partnersLogoUrl.collectAsStateWithLifecycle()
    val state = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
            .verticalScroll(state),
    ) {
//        HomeTopBar(Modifier.fillMaxWidth())

        Text(
            modifier = Modifier.padding(vertical = 22.dp),
            text = "Cavite State\nUniversity",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 42.sp,
            lineHeight = 42.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            modifier = Modifier,
            text = "Courses Offered",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
//            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(8.dp))

        CoursesOffer(Modifier.fillMaxWidth(),navHostController,coursesofferedList)

        Spacer(modifier = Modifier.height(32.dp))

        CVSUDetails(modifier = Modifier )

        Spacer(modifier = Modifier.height(32.dp))

        QualityPolicySection()

        Spacer(modifier = Modifier.height(32.dp))

        StrategicPlanSection(modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(32.dp))

        PartnersSection( modifier = Modifier.fillMaxWidth(),partnersLogosUrl)

        Spacer(modifier = Modifier.height(32.dp))


    }
}







@Composable
fun CVSUDetails(modifier: Modifier = Modifier) {
    val items = listOf(
        Pair("2,190", "Students Enrolled"),
        Pair("7", "Programs Offered"),
        Pair("60", "Academic Personnel"),
        Pair("9", "Admin Staff")
    )
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GridItem(modifier = Modifier.weight(1f), label = items[0].first, value = items[0].second)
                GridItem(modifier = Modifier.weight(1f), label = items[1].first, value = items[1].second)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GridItem(modifier = Modifier.weight(1f), label = items[2].first, value = items[2].second)
                GridItem(modifier = Modifier.weight(1f), label = items[3].first, value = items[3].second)
            }
        }
    }
}

@Composable
fun GridItem(modifier: Modifier = Modifier ,label: String, value: String) {
    Column(
        modifier = modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 22.sp,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            fontWeight = FontWeight.Bold

        )
        Text(
            text = value,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun CoursesOffer(modifier: Modifier = Modifier,navHostController: NavHostController,list: List<CoursesOfferedData>) {
    val context = LocalContext.current
    LazyRow(modifier = modifier) {

        items(
            items = list,
            key = { item ->
                // Return a stable + unique key for the item
                item.id
            }
        ) { item ->
            CoursesOfferItem(
                modifier = Modifier.width(240.dp),
                data = item
            ){
                id->
                navHostController.navigate(CoursesScreens.CourseDetailScreen.route.plus("/$id")){
                    popUpTo(CoursesScreens.RootScreen.route){
                        inclusive = true
                    }
                    launchSingleTop = true
                }
//                Toast.makeText(context, id ,Toast.LENGTH_SHORT).show()

            }
            Spacer(modifier = Modifier.size(8.dp))
        }

    }
}

@Composable
fun CoursesOfferItem(
    modifier: Modifier = Modifier,
    data: CoursesOfferedData,
    onClick:(id:String)->Unit={}
) {
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors().copy(containerColor = data.bgColor),
        onClick = {
            onClick(data.id)
        }
    ) {
        Column {
            AsyncImage(model = data.imgUrl, contentDescription = "", modifier = Modifier.fillMaxWidth())

            Text(
                modifier = Modifier.padding(16.dp),
                text = data.courseName,
                style = MaterialTheme.typography.titleMedium.copy(
                    shadow = Shadow(
                        color = Color.Gray,
                        blurRadius = 12f
                    )
                ),
                fontWeight = FontWeight.Bold,
                color = Color(0xfffae10d),

            )
//            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}



@Composable
fun QualityPolicySection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Quality Policy",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.quality_policy_string),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            lineHeight = 32.sp
        )
    }
}

@Composable
fun StrategicPlanSection(modifier: Modifier = Modifier) {
    Card {
        AsyncImage(
            model = "http://generaltrias.cvsu.edu.ph/images/StrategicPlan.jpg",
            contentDescription = "",
            modifier = modifier,
            contentScale = ContentScale.Fit,
//        placeholder = painterResource(id = R.drawable.cvsu_clean_logo)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PartnersSection(modifier: Modifier = Modifier,list:List<String>) {

    FlowRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp), maxItemsInEachRow = 3, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        val itemModifier = Modifier
            .padding(4.dp)
            .height(80.dp)
            .weight(1f)

        list.forEach { url ->
            Box(itemModifier, contentAlignment = Alignment.Center){
                AsyncImage(
                    model = url,
                    contentDescription = "",
                    modifier = Modifier.run { size(72.dp) },
                    placeholder = painterResource(id = R.drawable.cvsu_clean_logo)
                )
            }
        }
    }
}




