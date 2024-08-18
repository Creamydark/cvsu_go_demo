package com.creamydark.cvsugo.googleAuth.presentation.oneClickSignIn

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.auth.util.SignInResult
import com.creamydark.cvsugo.core.domain.KimChaewon
import com.creamydark.cvsugo.core.presentation.rootscreen.LocalNavController
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.RootRoutesItems
import com.creamydark.cvsugo.googleAuth.components.SignInWithGoogleBTN
import com.creamydark.cvsugo.googleAuth.presentation.oneClickSignIn.intent.OneClickSignInScreenIntent
import com.creamydark.cvsugo.googleAuth.presentation.oneClickSignIn.state.OneClickSignInScreenState
import com.creamydark.cvsugo.googleAuth.presentation.oneClickSignIn.viewmodel.OneClickSignInViewmodel
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.getUserFromTokenId
import com.stevdzasan.onetap.rememberOneTapSignInState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OneClickSignInRootScreen(modifier: Modifier = Modifier,viewmodel: OneClickSignInViewmodel = hiltViewModel()) {

    val oneTapState = rememberOneTapSignInState()
    val context = LocalContext.current

    val navHostController = LocalNavController.current

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED){
            viewmodel.receiver.collectLatest {
                result->
                when (result) {
                    is SignInResult.Error -> {
                        Toast.makeText(context, result.exception.message, Toast.LENGTH_SHORT).show()
                    }
                    is SignInResult.Success -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        navigateToFeed(navHostController)
                    }
                }
            }
        }
    }
    OneTapSignInWithGoogle(
        state = oneTapState,
        clientId = KimChaewon.web_client_id,
        rememberAccount = false,
        onTokenIdReceived = {
            tokenId: String ->
            viewmodel.execIntent(OneClickSignInScreenIntent.OnTokenIdReceived(tokenId))
            Log.d("OneTapSignInWithGoogle", "OneClickSignInRootScreen: ${getUserFromTokenId(tokenId)?.email}")
        },
        onDialogDismissed = { error ->
            Log.d("OneTapSignInWithGoogle", "OneClickSignInRootScreen: $error")
            viewmodel.execIntent(OneClickSignInScreenIntent.OnDialogDismissed(error = error))
//            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    )

    OneClickSignInScreen(
        modifier = modifier,
        state = OneClickSignInScreenState(),
        intent = {
            intent ->
            when (intent) {
                OneClickSignInScreenIntent.OnSignInButtonClick -> {
                    oneTapState.open()
                }
                else -> {}
            }
            viewmodel.execIntent(intent)
        }
    )
}


fun navigateToFeed(navHostController: NavHostController){
    navHostController.navigate(RootRoutesItems.Community.route) {
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

@Composable
private fun OneClickSignInScreen(modifier: Modifier = Modifier, state : OneClickSignInScreenState, intent: (OneClickSignInScreenIntent) -> Unit) {
    Box(modifier = modifier
        .fillMaxSize()
        .padding(16.dp), contentAlignment = Alignment.Center){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.widthIn(max = 160.dp),
                painter = painterResource(id = R.drawable.cvsu_clean_logo),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.one_tap_signin_title_screen),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.one_tap_signin_content_text),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        SignInWithGoogleBTN(
            modifier = modifier.align(Alignment.BottomCenter),
            onSignInClick = {
                intent(OneClickSignInScreenIntent.OnSignInButtonClick)
            }
        )

    }
}

