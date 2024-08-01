package com.creamydark.cvsugo.auth.presentation.letssignin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.creamydark.cvsugo.core.presentation.rootscreen.LocalNavController
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.RoutesV2


@Composable
fun LetsSignInScreenRoot(modifier: Modifier = Modifier,) {
    val navHostController = LocalNavController.current
    LetsSignInScreen(modifier){
        navHostController.navigate(RoutesV2.SignInScreen.route){
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
    }
}
@Composable
private fun LetsSignInScreen(modifier: Modifier = Modifier,onSignIn:()->Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp),
        ) {
            /*Image(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .sizeIn(
                        minWidth = 320.dp,
                        minHeight = 320.dp,
                        maxWidth = 480.dp,
                        maxHeight = 480.dp
                    ),
                painter = painterResource(id = R.drawable.need_sign_in_img),
                contentDescription = "",
            )*/
            Text(
                text = "Sign-In Required",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)

            )
            Text(
                text = "Content is not available unless you are signed in.",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            onClick = {
                onSignIn()
            },
        ) {
            Text(text = "Sign In")
        }
    }
}


