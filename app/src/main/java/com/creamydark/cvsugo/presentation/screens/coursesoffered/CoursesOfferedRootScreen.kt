package com.creamydark.cvsugo.presentation.screens.coursesoffered

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.creamydark.cvsugo.domain.dataclass.CoursesOfferedData
import com.creamydark.cvsugo.presentation.screens.coursesoffered.viewmodel.CoursesOfferedViewModel


@Composable
fun CoursesOfferedRootScreen(modifier: Modifier = Modifier,viewModel: CoursesOfferedViewModel = hiltViewModel()) {

    val coursesofferedList by viewModel.coursesOfferList.collectAsStateWithLifecycle()

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
                Toast.makeText(context, item.id,Toast.LENGTH_SHORT).show()
            }
            Spacer(modifier = Modifier.size(14.dp))
        }
    }
}



@Composable
fun CoursesOfferedItem(modifier: Modifier = Modifier,data:CoursesOfferedData,onClick:()->Unit={}) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors().copy(containerColor = data.bgColor),
        onClick = { onClick() }
    ) {
        Column {
            AsyncImage(model = data.imgUrl, contentDescription = "",modifier = Modifier.fillMaxWidth() )
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