package com.creamydark.cvsugo.presentation.screens.coursesoffered

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.presentation.screens.coursesoffered.viewmodel.CourseDetailViewModel

@Composable
fun CourseDetailScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: CourseDetailViewModel
) {
    val currentData by viewModel.currentCourseData.collectAsStateWithLifecycle()
    val state = rememberScrollState()
    Column(modifier = modifier
        .padding(horizontal = 16.dp)
        .verticalScroll(state)) {
        Text(
            modifier = Modifier.padding(vertical = 22.dp),
            text = currentData.coursName,
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 42.sp,
            lineHeight = 42.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(32.dp))
        AsyncImage(
            model = currentData.courseBannerImgUrl,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 200.dp)
                .clip(shape = RoundedCornerShape(16.dp)),
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Chairperson",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
        Text(
            text = currentData.chairperson,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Department",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
        Text(
            text = currentData.department,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(modifier = Modifier.size(18.dp),painter = painterResource(id = R.drawable.baseline_filter_none_24), contentDescription = "")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Total Units: ${ if(currentData.totalUnits == 0) "-" else currentData.totalUnits }",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Icon(modifier = Modifier.size(18.dp),painter = painterResource(id = R.drawable.outline_access_time_24), contentDescription = "")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Contact Hours: ${if (currentData.contactHours == 0) "-" else currentData.contactHours.toString()}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Program Summary",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = currentData.programSummary.ifBlank { stringResource(id = R.string.lorem_ipsum) },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            lineHeight = 32.sp
        )
        Spacer(modifier = Modifier.height(32.dp))

    }
}