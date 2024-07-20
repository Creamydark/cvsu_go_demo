package com.creamydark.cvsugo.presentation.screens.studentportal.homescreen

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.domain.dataclass.SubjectData


val subjects = listOf(
    SubjectData(
        subjectName = "Art Appreciation",
        instructorName = "Iv**y Pa****",
        schedCode = "202401356",
        subjectCode = "GNED 01",
        section = "BSIT 1-3"
    ),
    SubjectData(
        subjectName = "Mathematics in the Modern World",
        instructorName = "Ro**** Si*** S. D****",
        schedCode = "202401357",
        subjectCode = "GNED 03",
        section = "BSIT 1-3"
    ),
    SubjectData(
        subjectName = "Science, Technology and Society",
        instructorName = "Ke**** D****",
        schedCode = "202401358",
        subjectCode = "GNED 06",
        section = "BSIT 1-3"
    ),
    SubjectData(
        subjectName = "Dalumat Ng/Sa Filipino",
        instructorName = "Ch******* D. N******",
        schedCode = "202401359",
        subjectCode = "GNED 12",
        section = "BSIT 1-3"
    ),
    SubjectData(
        subjectName = "Computer Programming 2",
        instructorName = "Ja******* Po*********",
        schedCode = "202401360",
        subjectCode = "DCIT 23",
        section = "BSIT 1-3"
    ),
    SubjectData(
        subjectName = "Web System and Technologies 1",
        instructorName = "Ma***** J. Be***",
        schedCode = "202401361",
        subjectCode = "ITEC 50",
        section = "BSIT 1-3"
    ),
    SubjectData(
        subjectName = "Fitness Exercises",
        instructorName = "Ni** I. Ar****",
        schedCode = "202401362",
        subjectCode = "FITT 2",
        section = "BSIT 1-3"
    ),
    SubjectData(
        subjectName = "CWTS/LTS/ROTC",
        instructorName = "Ar*** Sh**** de** To****",
        schedCode = "202401363",
        subjectCode = "NSTP 2",
        section = "BSIT 1-3"
    )
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StudentPortalHomeScreen(modifier: Modifier = Modifier,navHostController: NavHostController) {
    val context = LocalContext.current

    LazyColumn(modifier = Modifier

        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        item {
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
        }
        item {
            StudentStatusDashboardItem(
                modifier = Modifier.fillMaxWidth(),
                label = "Status",
                value = "Not Enrolled"){
                Button(
                    onClick = {
                        Toast.makeText(context, "Pre-Registration", Toast.LENGTH_SHORT).show()
                    },
                ) {
                    Text(text = "Pre-Registration")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            StudentDashboard()
            Spacer(modifier = Modifier.height(22.dp))
        }

        items(items = subjects, key = { it.subjectName }) {
            data ->
            SubjectsListItem(
                subjectName = data.subjectName,
                instructorName = data.instructorName,
                schedCode = data.schedCode,
                subjectCode = data.subjectCode,
                section = data.section
            )
        }

    }



}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StudentDashboard(modifier: Modifier = Modifier) {
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
            icon = {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = Color(0xffffc107),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_calendar_today_24),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
//            icon = ImageVector.vectorResource(id = R.drawable.baseline_calendar_today_24)
        )
        StudentCurrentLevelItem(
            modifier = itemModifier,
            label = "Year\n" +
                    "& Section",
            value = "1-3",
            icon = {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = Color(0xff17a2b8),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_edit_calendar_24),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
//            icon = ImageVector.vectorResource(id = R.drawable.baseline_edit_calendar_24)
        )
        StudentCurrentLevelItem(
            modifier = itemModifier,
            label = "Course",
            value = "BSIT",
            icon = {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = Color(0xff6c757d),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_groups_24),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
//            icon = ImageVector.vectorResource(id = R.drawable.baseline_groups_24)
        )
        StudentCurrentLevelItem(
            modifier = itemModifier,
            label = "Academic Year",
            value = "2023-2024",
            icon = {
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
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
//            icon = Icons.Default.DateRange
        )
    }
}


@Composable
fun StudentCurrentLevelItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: @Composable () -> Unit
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
                icon()

            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
            )
        }
    }
}


@Composable
fun StudentStatusDashboardItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    btn:@Composable () -> Unit 
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                btn()
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




@Preview(showBackground = true)
@Composable
private fun SomeItemPrev() {
    SubjectsListItem()
}



@Composable
fun SubjectsListItem(
    modifier: Modifier = Modifier,
    subjectName: String = "Mathematics in the Modern World",
    instructorName: String = "Ronnel Siggy S. Deseo",
    schedCode: String = "202401357",
    subjectCode: String = "GNED 03",
    section: String = "BSIT 1-3"
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        Text(
            text = subjectName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = instructorName,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),


        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$section - $subjectCode - $schedCode",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                fontSize = 12.sp,
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}