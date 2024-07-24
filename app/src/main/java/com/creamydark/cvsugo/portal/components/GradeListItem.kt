package com.creamydark.cvsugo.portal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.creamydark.cvsugo.portal.domain.dataclass.CourseData

@Composable
fun GradeListItem(
    modifier: Modifier = Modifier, data: CourseData
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(text = data.instructor, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            Text(text = data.subjectTitle, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = data.grade, style = MaterialTheme.typography.headlineLarge)
            Box(
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(100)
                )
            ){
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    text = data.subjectCode,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 14.sp
                )
            }

        }
    }
}


@Composable
fun CourseCard(course: CourseData, modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(28.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = course.subjectTitle,
                fontSize = 20.sp, // Heading size
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary // Example color
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${course.subjectCode} - ${course.code}",
                fontSize = 16.sp // Subheading size
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = course.instructor,
                fontSize = 14.sp // Body size
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Grade: ${course.grade}",
                fontSize = 14.sp, // Body size
                color = MaterialTheme.colorScheme.secondary // Example color
            )
        }
    }
}

