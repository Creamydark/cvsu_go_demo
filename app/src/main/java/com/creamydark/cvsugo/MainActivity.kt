package com.creamydark.cvsugo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.creamydark.cvsugo.presentation.navgraphs.HomeScreens
import com.creamydark.cvsugo.presentation.navgraphs.MainGraph
import com.creamydark.cvsugo.presentation.navgraphs.about
import com.creamydark.cvsugo.presentation.navgraphs.courses
import com.creamydark.cvsugo.presentation.navgraphs.home
import com.creamydark.cvsugo.ui.theme.CVSUGoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CVSUGoTheme {
                val navHostController = rememberNavController()
                val nav_items = listOf(
                    MainGraph.Home,
                    MainGraph.Courses,
                    MainGraph.About
                )
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        HomeTopBar(Modifier.padding(horizontal = 16.dp).statusBarsPadding().fillMaxWidth())
                    },
                    bottomBar = {
                        NavigationBar {
                            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
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
                                        Text(text = screen.label)
                                    }
                                )
                            }
                        }
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
                    }
                }
            }
        }
    }
}

@Composable
fun HomeTopBar(modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        AsyncImage(model = R.drawable.cvsu_clean_logo, contentDescription = "",modifier = Modifier.size(42.dp))
        IconButton(onClick = {  }) {
            Icon(
                imageVector = Icons.Outlined.Menu,
                contentDescription = "",
            )
        }
    }
}