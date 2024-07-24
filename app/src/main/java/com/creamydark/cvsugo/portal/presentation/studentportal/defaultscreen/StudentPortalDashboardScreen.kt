package com.creamydark.cvsugo.portal.presentation.studentportal.defaultscreen

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.portal.components.InfoCardWithButton
import com.creamydark.cvsugo.portal.domain.dataclass.AcademicInformation
import com.creamydark.cvsugo.portal.presentation.studentportal.defaultscreen.viewmodel.StudentPortalViewModel


@Composable
fun StudentPortalDashboardScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: StudentPortalViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val information by viewModel.studentInformation.collectAsStateWithLifecycle()
    val academicInformation by viewModel.academicInformation.collectAsStateWithLifecycle()
    val subjectEnrolledList by viewModel.subjectEnrolledList.collectAsStateWithLifecycle()
    val name = "${information.firstName} ${information.lastName}"

    LazyColumn(modifier = modifier

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
            Text(text = "Welcome, $name", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(22.dp))
        }
        item {
            InfoCardWithButton(
                modifier = Modifier.fillMaxWidth(),
                label = "Status",
                value = if (!academicInformation.enrolled)"Not Enrolled" else "Enrolled"
            ){
                Button(
                    onClick = {
                        Toast.makeText(context, "Pre-Registration", Toast.LENGTH_SHORT).show()
                    },
                ) {
                    Text(text = "Pre-Registration")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            StudentDashboard(
                academicInformation = academicInformation
            )
            Spacer(modifier = Modifier.height(22.dp))
        }

        items(items = subjectEnrolledList, key = { it.subjectName }) {
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
fun StudentDashboard(modifier: Modifier = Modifier,academicInformation: AcademicInformation) {
    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 2,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val itemModifier = Modifier
            .height(120.dp)
            .weight(1f)
        InfoCardWithButton(
            modifier = itemModifier,
            label = "Current\nSemester",
            value = academicInformation.currentSemester,
            btn = {
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
                        imageVector = Icons.Outlined.CalendarToday,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
//            icon = ImageVector.vectorResource(id = R.drawable.baseline_calendar_today_24)
        )
        InfoCardWithButton(
            modifier = itemModifier,
            label = "Year\n" +
                    "& Section",
            value = "${academicInformation.year}-${academicInformation.section}",
            btn = {
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
        InfoCardWithButton(
            modifier = itemModifier,
            label = "Course",
            value = academicInformation.course,
            btn = {
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
        InfoCardWithButton(
            modifier = itemModifier,
            label = "Academic Year",
            value = academicInformation.currentAcademicYear,
            btn = {
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


