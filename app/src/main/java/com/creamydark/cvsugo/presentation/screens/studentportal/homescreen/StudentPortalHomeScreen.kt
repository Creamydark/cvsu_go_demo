package com.creamydark.cvsugo.presentation.screens.studentportal.homescreen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.creamydark.cvsugo.R


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StudentPortalHomeScreen(modifier: Modifier = Modifier,navHostController: NavHostController) {


    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        Text(
            modifier = Modifier.padding(top = 22.dp),
            text = "Portal",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 42.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
        )
        Text(text = "Welcome, Marc Luis Segunto", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(22.dp))
        StudentLevelUI()

    }



}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StudentLevelUI(modifier: Modifier = Modifier) {
    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 2,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val itemModifier = Modifier
            .height(120.dp)
            .weight(1f)
        StudentCurrentLevelItem(
            modifier = itemModifier,
            label = "Current\nSemester",
            value = "1st",
            icon = ImageVector.vectorResource(id = R.drawable.baseline_calendar_today_24)
        )
        StudentCurrentLevelItem(
            modifier = itemModifier,
            label = "Year\n" +
                    "& Section",
            value = "1-3",
            icon = ImageVector.vectorResource(id = R.drawable.baseline_edit_calendar_24)
        )
        StudentCurrentLevelItem(
            modifier = itemModifier,
            label = "Course",
            value = "BSIT",
            icon = ImageVector.vectorResource(id = R.drawable.baseline_groups_24)
        )
        StudentCurrentLevelItem(
            modifier = itemModifier,
            label = "Academic Year",
            value = "2023-2024",
            icon = Icons.Default.DateRange
        )
    }
}


@Composable
fun StudentCurrentLevelItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: ImageVector
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = Color(0xff28a745),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = icon,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

