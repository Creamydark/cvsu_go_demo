package com.creamydark.cvsugo.portal.presentation.studentportal.grades

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.creamydark.cvsugo.core.components.ListItemCustom0
import com.creamydark.cvsugo.portal.components.InfoCardWithButton
import com.creamydark.cvsugo.portal.domain.dataclass.CourseData

val courses = listOf(
    CourseData(
        code = "202401290",
        subjectCode = "GNED 05",
        subjectTitle = "Purposive Communication",
        instructor = "J*** J** P. C*****",
        grade = "N/A"
    ),
    CourseData(
        code = "202401294",
        subjectCode = "DCIT 22",
        subjectTitle = "Computer Programming I",
        instructor = "M****** J. B****",
        grade = "N/A"
    ),
    CourseData(
        code = "202401291",
        subjectCode = "GNED 11",
        subjectTitle = "Kontekstwalisadong Komunikasyon sa Filipino",
        instructor = "C********* D. N*****",
        grade = "N/A"
    ),
    CourseData(
        code = "202401289",
        subjectCode = "GNED 02",
        subjectTitle = "Ethics",
        instructor = "M** E****** J** T****",
        grade = "N/A"
    ),
    CourseData(
        code = "202401293",
        subjectCode = "DCIT 21",
        subjectTitle = "Introduction to Computing",
        instructor = "J******* P********",
        grade = "N/A"
    ),
    CourseData(
        code = "202401296",
        subjectCode = "NSTP 1",
        subjectTitle = "CWTS / LTS / ROTC",
        instructor = "J*** J** R. S****",
        grade = "N/A"
    ),
    CourseData(
        code = "202401292",
        subjectCode = "COSC 50",
        subjectTitle = "Discrete Structure",
        instructor = "L**-A***** Y*****",
        grade = "N/A"
    ),
    CourseData(
        code = "202401295",
        subjectCode = "FITT 1",
        subjectTitle = "Movement Enhancement",
        instructor = "A***** M*******",
        grade = "N/A"
    ),
    CourseData(
        code = "202401297",
        subjectCode = "CvSU 101",
        subjectTitle = "Institutional Orientation",
        instructor = "L******* A****",
        grade = "N/A"
    ),
    CourseData(
        code = "202401363",
        subjectCode = "NSTP 2",
        subjectTitle = "CWTS / LTS / ROTC",
        instructor = "A**** S**** d** T****",
        grade = "N/A"
    ),
    CourseData(
        code = "202401359",
        subjectCode = "GNED 12",
        subjectTitle = "Dalumat Ng/Sa Filipino",
        instructor = "C********* D. N*****",
        grade = "N/A"
    ),
    CourseData(
        code = "202401362",
        subjectCode = "FITT 2",
        subjectTitle = "Fitness Exercises",
        instructor = "N** I. A****",
        grade = "N/A"
    ),
    CourseData(
        code = "202401358",
        subjectCode = "GNED 06",
        subjectTitle = "Science, Technology and Society",
        instructor = "K***** D****",
        grade = "N/A"
    ),
    CourseData(
        code = "202401356",
        subjectCode = "GNED 01",
        subjectTitle = "Art Appreciation",
        instructor = "I**** P****",
        grade = "N/A"
    ),
    CourseData(
        code = "202401360",
        subjectCode = "DCIT 23",
        subjectTitle = "Computer Programming 2",
        instructor = "J******* P********",
        grade = "N/A"
    ),
    CourseData(
        code = "202401361",
        subjectCode = "ITEC 50",
        subjectTitle = "Web System and Technologies 1",
        instructor = "M****** J. B****",
        grade = "N/A"
    )
)

@Composable
fun StudentGradesScreen(modifier: Modifier = Modifier) {

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Column {
//                BigHeaderMain(text = "Sheeesh!")
//                Text(text = "Here's your grades", style = MaterialTheme.typography.titleLarge)
//                Spacer(modifier = Modifier.height(22.dp))
                InfoCardWithButton(label = "Order", value = "Graded Subjects") {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "1st, 2023-2024")
                    }
                }
                Spacer(modifier = Modifier.height(22.dp))
            }
        }
        items(
            items = courses,
            key = { it.code }
        ){
            data ->
            ListItemCustom0(
                title = data.subjectTitle,
                subtitle0 = data.instructor,
                subtitle1 = "Grade: ${data.grade} - ${data.subjectCode} - ${data.code}"
            )
//            CourseCard(modifier = Modifier, course = data)
        }
    }
}
