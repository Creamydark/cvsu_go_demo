package com.creamydark.cvsugo.university.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.core.components.AnimatedTextCustomComponent
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.UniversityRoutesItems


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun UniversityPageTopbar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var expanded by remember { mutableStateOf(false) }

    val title = UniversityRoutesItems.entries.find { it.route == currentDestination?.route }?.label ?: ""

    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Image(
                    painter = painterResource(id = R.drawable.cvsu_clean_logo),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        title = {

            TextButton(onClick = { expanded = true }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AnimatedTextCustomComponent(text = title)
                    Icon(imageVector = Icons.Outlined.KeyboardArrowDown, contentDescription = "")
                }
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                UniversityRoutesItems.entries.forEach {
                    universityRoutesItems ->
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(imageVector = universityRoutesItems.icon, contentDescription = "")
                        },
                        text = { Text(text = universityRoutesItems.label) },
                        onClick = {
                            navHostController.navigate(universityRoutesItems.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
//                                popUpTo(currentDestination.id) {
//                                    saveState = true
//                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
//                                restoreState = true
                            }
                            expanded = false
                        }
                    )
                }
            }
        },
        actions = {

        }
    )
}