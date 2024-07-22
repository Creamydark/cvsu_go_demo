package com.creamydark.cvsugo

import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.creamydark.cvsugo.presentation.screens.mainscreen.MainScreen
import com.creamydark.cvsugo.presentation.screens.mainscreen.viewmodel.MainScreenViewModel
import com.creamydark.cvsugo.presentation.screens.util.composables.NavigationDrawerComposable
import com.creamydark.cvsugo.ui.theme.CVSUGoTheme
import com.creamydark.cvsugo.workmanager.GreetingtWorkManager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CVSUGoTheme {
                val viewModel: MainScreenViewModel = hiltViewModel()
                val navHostController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                val notificationPermission = rememberPermissionState(permission = POST_NOTIFICATIONS)
                val textToShow = remember {
                    if (notificationPermission.status.shouldShowRationale) {
                        // If the user has denied the notification permission but the rationale can be shown,
                        // then gently explain why the app requires this permission
                        "This app uses notifications to keep you updated on important events and information. Please grant the permission."
                    } else {
                        // If it's the first time the user lands on this feature, or the user
                        // doesn't want to be asked again for this permission, explain that the
                        // permission is required
                        "Notifications are required for this feature to work properly. Please grant the permission."
                    }
                }

                if(!notificationPermission.status.isGranted){
//                    notificationPermission.launchPermissionRequest()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                            .navigationBarsPadding(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){

                        Text(
                            textToShow,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { notificationPermission.launchPermissionRequest() }) {
                            Text("Request permission")
                        }
                    }
                }else{
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            NavigationDrawerComposable(
                                navHostController = navHostController,
                                viewModel = viewModel,
                                drawerState = drawerState
                            )
                        },
                    ) {
                        MainScreen(
                            modifier = Modifier.fillMaxWidth(),
                            navHostController = navHostController,
                            drawaState = drawerState,
                            viewModel = viewModel
                        )
                    }
                }
                LaunchedEffect(key1 = Unit){
                    val workRequest = PeriodicWorkRequestBuilder<GreetingtWorkManager>(
                        repeatInterval = 3,
                        repeatIntervalTimeUnit = TimeUnit.HOURS
                    ).build()
                    val workManager = WorkManager.getInstance(applicationContext)
                    workManager.enqueue(workRequest)
                }
            }
        }
    }
}