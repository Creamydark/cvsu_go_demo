package com.creamydark.cvsugo.presentation.screens.appInfo

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.creamydark.cvsugo.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppInfoRootScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://avatars.githubusercontent.com/u/89747755?s=96&v=4",
                contentDescription = "",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Developed by", style = MaterialTheme.typography.labelSmall)
                Text(
                    text = "CreamyDark",
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                )
                Text(text = "Marc Luis Segunto", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(
            horizontalArrangement = Arrangement.Center
        ) {
            SocialMediaChip(
                context = context,
                modifier = Modifier.weight(1f),
                label = "Instagram",
                icon = painterResource(id = R.drawable.icons8_instagram),
                package_name = "com.instagram.android",
                uri = "https://instagram.com/creamydark_14"
            )
            Spacer(modifier = Modifier.width(8.dp))
            SocialMediaChip(
                context = context,
                modifier = Modifier.weight(1f),
                label = "Facebook",
                icon = painterResource(id = R.drawable.icons8_facebook),
                package_name = "com.facebook.katana",
                uri = "https://m.facebook.com/creamydark"
            )
            Spacer(modifier = Modifier.width(8.dp))
            SocialMediaChip(
                context = context,
                modifier = Modifier.weight(1f),
                label = "Github",
                icon = painterResource(id = R.drawable.icons8_github),
                package_name = "com.github.android",
                uri = "https://github.com/creamydark"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        SomeSectionText(label = "Important Notice", text = stringResource(id = R.string.warn_user_appinfo))
        Spacer(modifier = Modifier.height(16.dp))
        SomeSectionText(label = "Contact Me", text = stringResource(id = R.string.contact_me_string))
    }
}


@Composable
fun SocialMediaChip(
    context: Context,
    modifier: Modifier = Modifier,
    label: String = "",
    package_name: String = "",
    uri: String = "",
    icon: Painter,
) {

    AssistChip(
        onClick = {
            val uri = Uri.parse(uri)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage(package_name)

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                // Fallback to browser if Instagram app is not installed
                val browserIntent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(browserIntent)
            }
        },
        label = { Text(text = label) },
        leadingIcon = {
            Icon(
                painter = icon,
                contentDescription = "",
                modifier = modifier.size(AssistChipDefaults.IconSize)
            )
        }
    )
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



