package com.creamydark.cvsugo.home.presentation.main


import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.Routes
import com.creamydark.cvsugo.core.presentation.util.composables.ParagraphWithLabel
import com.creamydark.cvsugo.home.domain.dataclass.CoursesOfferedData
import com.creamydark.cvsugo.home.domain.dataclass.UniversityStatsData
import com.creamydark.cvsugo.home.presentation.main.viewmodel.HomeViewModel


@Composable
fun HomeMainScreen(navHostController: NavHostController,modifier: Modifier = Modifier,viewModel: HomeViewModel = hiltViewModel()) {
    val coursesofferedList by viewModel.coursesOfferList.collectAsStateWithLifecycle()
    val partnersLogosUrl by viewModel.partnersLogoUrl.collectAsStateWithLifecycle()
    val universityStatsData by viewModel.universityStatsData.collectAsStateWithLifecycle()
    val state = rememberScrollState()
    val paddingHorrizontal = Modifier.padding(horizontal = 16.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state),
    ) {
        Column(
            modifier = paddingHorrizontal

        ) {
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
                text = "Courses Offered",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        CoursesOffer(Modifier.fillMaxWidth(),navHostController,coursesofferedList)
        Column(
            modifier = paddingHorrizontal
        ){
            Spacer(modifier = Modifier.height(32.dp))

            UniversityStatsCard(
                modifier = Modifier,
                universityStatsData = universityStatsData
            )

            Spacer(modifier = Modifier.height(32.dp))

            ParagraphWithLabel(
                label = "Quality Policy",
                text = stringResource(id = R.string.quality_policy_string),
            )

            Spacer(modifier = Modifier.height(32.dp))

            YouTubePlayer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(
                        RoundedCornerShape(16.dp)
                    ),
                "qZWVlW5IkLg"
            )

            Spacer(modifier = Modifier.height(32.dp))

            StrategicPlanSection(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(32.dp))

            PartnersSection( modifier = Modifier.fillMaxWidth(),partnersLogosUrl)

            Spacer(modifier = Modifier.height(32.dp))


        }
    }


}




@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun YouTubePlayer(modifier: Modifier = Modifier, videoId: String) {
    val html = """
        <!DOCTYPE html>
        <html>
        <body style="margin:0;padding:0;height:280px;">
        <iframe width="100%" height="280px" src="https://www.youtube.com/embed/$videoId" 
                frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                allowfullscreen></iframe>
        </body>
        </html>
    """.trimIndent()

    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
            }
        },
        update = {
            it.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
        },
    )
}


@Composable
private fun UniversityStatsCard(modifier: Modifier = Modifier,universityStatsData: UniversityStatsData) {
     ElevatedCard(modifier = modifier, shape = RoundedCornerShape(28.dp)) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GridItem(
                    modifier = Modifier.weight(1f),
                    label = "Students Enrolled",
                    value = universityStatsData.studentsEnrolled.toString()
                )
                GridItem(
                    modifier = Modifier.weight(1f),
                    label = "Programs Offered",
                    value = universityStatsData.programsOffered.toString()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GridItem(
                    modifier = Modifier.weight(1f),
                    label = "Academic Personnel",
                    value = universityStatsData.academicPersonnel.toString()
                )
                GridItem(
                    modifier = Modifier.weight(1f),
                    label = "Admin Staff",
                    value = universityStatsData.adminStaff.toString()
                )
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
            text = value,
            fontSize = 22.sp,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            fontWeight = FontWeight.Bold

        )
        Text(
            text = label,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun CoursesOffer(modifier: Modifier = Modifier,navHostController: NavHostController,list: List<CoursesOfferedData>) {
    LazyRow(modifier = modifier) {
        item {
            Spacer(modifier = Modifier.width(16.dp))
        }
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
                navHostController.navigate(Routes.CoursesOfferDetailScreen.route.plus("/$id")){
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
        },
        shape = RoundedCornerShape(16.dp)
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
private fun StrategicPlanSection(modifier: Modifier = Modifier) {
    ElevatedCard(shape = RoundedCornerShape(28.dp)) {
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
private fun PartnersSection(modifier: Modifier = Modifier,list:List<String>) {

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




