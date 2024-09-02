package com.creamydark.cvsugo.notification.presentation.addpost.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.creamydark.cvsugo.core.components.BigHeaderMain
import com.creamydark.cvsugo.core.presentation.rootscreen.LocalNavController
import com.creamydark.cvsugo.notification.presentation.addpost.presentation.event.UploadNotificationScreenEvents
import com.creamydark.cvsugo.notification.presentation.addpost.presentation.intent.UploadNotificationScreenIntent
import com.creamydark.cvsugo.notification.presentation.addpost.presentation.state.UploadNotificationScreenState
import com.creamydark.cvsugo.notification.presentation.addpost.presentation.viewmodel.UploadNotificationScreenViewmodel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UploadNotificationRootScreen(
    modifier: Modifier = Modifier,
    viewmodel: UploadNotificationScreenViewmodel = hiltViewModel()
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val navhostController = LocalNavController.current
    val context = LocalContext.current

    LaunchedEffect(key1 = lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED){
            viewmodel.event.collectLatest {
                event ->
                when (event) {
                    is UploadNotificationScreenEvents.OnFailed -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }
                    is UploadNotificationScreenEvents.OnSuccess -> {
                        navhostController.navigateUp()
                    }
                }
            }
        }
    }

    UploadNotificationScreen(
        modifier = modifier,
        state = viewmodel.state,
        onIntent = viewmodel::execIntent
    )
}

@Composable
private fun UploadNotificationScreen(
    modifier: Modifier = Modifier,
    state: UploadNotificationScreenState,
    onIntent: (UploadNotificationScreenIntent) -> Unit = {}
) {
    Box(modifier = modifier.padding(16.dp)){
        Column(Modifier.align(Alignment.TopCenter)) {
            BigHeaderMain(text = "Upload Notification")
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                label = { Text(text = "Title") },
                value = state.notification.title,
                onValueChange = {
                    onIntent(UploadNotificationScreenIntent.OnTitleChange(it))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                label = { Text(text = "Body") },
                value = state.notification.message,
                onValueChange = {
                    onIntent(UploadNotificationScreenIntent.OnMessageChange(it))
                }
            )
        }

        Button(
            enabled = !state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onClick = {
                onIntent(UploadNotificationScreenIntent.Send)
            },
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }

                Text(text = if (state.isLoading) "Please wait" else "Send")
            }
        }

    }
}