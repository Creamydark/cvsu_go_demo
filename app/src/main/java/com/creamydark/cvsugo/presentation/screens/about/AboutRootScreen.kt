package com.creamydark.cvsugo.presentation.screens.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.creamydark.cvsugo.R

@Composable
fun AboutRootScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        AsyncImage(
            model = "http://generaltrias.cvsu.edu.ph/images/gtbuilding.png",
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Core Values",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier,
            text = "Truth, Excellence and Service",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 42.sp,
            lineHeight = 42.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(32.dp))
        SomeSectionText(label = "Quality Policy", text = stringResource(id = R.string.quality_policy_string))
        Spacer(modifier = Modifier.height(16.dp))
        SomeSectionText(label = "Mission", text = stringResource(id = R.string.mission_string))
        Spacer(modifier = Modifier.height(16.dp))
        SomeSectionText(label = "Vision", text = stringResource(id = R.string.visiong_string))
        Spacer(modifier = Modifier.height(32.dp))


    }
}

@Composable
private fun SomeSectionText(modifier: Modifier = Modifier,label:String,text:String) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            lineHeight = 32.sp
        )
    }
}