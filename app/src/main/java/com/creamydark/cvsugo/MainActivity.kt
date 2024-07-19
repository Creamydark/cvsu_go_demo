package com.creamydark.cvsugo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.creamydark.cvsugo.presentation.navgraphs.AboutScreens
import com.creamydark.cvsugo.presentation.navgraphs.AppInfoScreens
import com.creamydark.cvsugo.presentation.navgraphs.CoursesScreens
import com.creamydark.cvsugo.presentation.navgraphs.HomeScreens
import com.creamydark.cvsugo.presentation.navgraphs.MainGraph
import com.creamydark.cvsugo.presentation.navgraphs.about
import com.creamydark.cvsugo.presentation.navgraphs.appInfo
import com.creamydark.cvsugo.presentation.navgraphs.courses
import com.creamydark.cvsugo.presentation.navgraphs.home
import com.creamydark.cvsugo.ui.theme.CVSUGoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CVSUGoTheme {
                val navHostController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val nav_items = listOf(
                    MainGraph.Home,
                    MainGraph.Courses,
                    MainGraph.About
                )

                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val minju = listOf(
                    HomeScreens.RootScreen.route,
                    CoursesScreens.RootScreen.route,
                    AboutScreens.RootScreen.route,
                    AppInfoScreens.RootScreen.route,

                )
                val accountMenus = listOf(
                    Pair(Icons.Outlined.Lock,"Sign In"),
                )
                val xc = Modifier.padding(horizontal = 12.dp)
                val hPadding32 = Modifier.padding(horizontal = 28.dp)
                val vs = Modifier.padding(vertical = 16.dp)
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet{
                            Spacer(modifier = vs)
                            Text(
                                modifier = hPadding32,
                                text = "CVSU Go",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            nav_items.forEach { screen ->
                                NavigationDrawerItem(
                                    modifier = xc,
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        navHostController.navigate(screen.route) {
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navHostController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }

                                    },
                                    icon = {
                                        Icon(imageVector = screen.icon, contentDescription = "")
                                    },
                                    label = {
                                        Text(
                                            text = screen.label,
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.padding(vertical = 8.dp))
                            Text(
                                modifier = hPadding32,
                                text = "Account",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.padding(vertical = 8.dp))

                            accountMenus.forEach {
                                pair: Pair<ImageVector, String> ->
                                NavigationDrawerItem(
                                    modifier = xc,
                                    label = {
                                        Text(
                                            text = pair.second,
                                        )
                                    },
                                    icon = {
                                        Icon(imageVector = pair.first, contentDescription = "")
                                    },
                                    selected = false,
                                    onClick = {

                                    },
                                )
                            }
                            Spacer(modifier = Modifier.padding(vertical = 8.dp))
                            Text(
                                modifier = Modifier.padding(horizontal = 28.dp),
                                text = "Development",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.padding(vertical = 8.dp))

                            NavigationDrawerItem(
                                modifier = xc,
                                label = {
                                    Text(
                                        text = "See More",
                                    )
                                },
                                icon = {
                                    Icon(imageVector = MainGraph.AppInfo.icon, contentDescription = "")
                                },
                                selected = currentDestination?.hierarchy?.any { it.route == MainGraph.AppInfo.route } == true,
                                onClick = {
                                    navHostController.navigate(MainGraph.AppInfo.route) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(navHostController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }
                                },
                            )
                        }
                    },
                ) {
                    //Main Screen
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
//                        HomeTopBar(Modifier.padding(horizontal = 16.dp).statusBarsPadding().fillMaxWidth())
                            TopAppBar(
                                title = {
                                    if (currentDestination?.route in minju){
                                        IconButton(onClick = {  }) {
                                            Image(painter = painterResource(id = R.drawable.cvsu_clean_logo), contentDescription = "")
                                        }
                                    }
                                },
                                navigationIcon = {
                                    if (currentDestination?.route !in minju){
                                        IconButton(
                                            onClick = {
                                                navHostController.popBackStack()
                                            },
                                        ) {
                                            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "")
                                        }
                                    }


                                },
                                actions = {
                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        },
                                    ) {
                                        Icon(imageVector = Icons.Outlined.Menu, contentDescription = "")
                                    }
                                }
                            )
                        },
                        bottomBar = {
                            /*NavigationBar {
                                nav_items.forEach { screen ->
                                    NavigationBarItem(
                                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                        onClick = {
                                            navHostController.navigate(screen.route) {
                                                // Pop up to the start destination of the graph to
                                                // avoid building up a large stack of destinations
                                                // on the back stack as users select items
                                                popUpTo(navHostController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                // Avoid multiple copies of the same destination when
                                                // reselecting the same item
                                                launchSingleTop = true
                                                // Restore state when reselecting a previously selected item
                                                restoreState = true
                                            }

                                        },
                                        icon = {
                                            Icon(imageVector = screen.icon, contentDescription = "")
                                        },
                                        label = {
                                            Text(text = screen.label,style = MaterialTheme.typography.titleSmall)
                                        }
                                    )
                                }
                            }*/
                        }
                    ) { innerPadding ->
                        NavHost(
                            modifier = Modifier.padding(innerPadding),
                            navController = navHostController,
                            startDestination = MainGraph.Home.route
                        ){
                            home(navHostController = navHostController)
                            about(navHostController = navHostController)
                            courses(navHostController)
                            appInfo(navHostController = navHostController)
                        }
                    }
                }

            }
        }
    }
}