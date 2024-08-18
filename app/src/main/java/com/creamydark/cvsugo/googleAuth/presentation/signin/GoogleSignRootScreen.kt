package com.creamydark.cvsugo.googleAuth.presentation.signin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.auth.util.SignInResult
import com.creamydark.cvsugo.core.presentation.rootscreen.LocalNavController
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.RootRoutesItems
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.SignInRoutesItems
import com.creamydark.cvsugo.googleAuth.components.EmailTextFieldCustom
import com.creamydark.cvsugo.googleAuth.components.PasswordTextFieldCustom
import com.creamydark.cvsugo.googleAuth.presentation.signin.intent.SignInScreenIntent
import com.creamydark.cvsugo.googleAuth.presentation.signin.state.SignInScreenState
import com.creamydark.cvsugo.googleAuth.presentation.signin.viewmodel.SignInViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GoogleSignRootScreen(modifier: Modifier = Modifier,viewModel: SignInViewModel = hiltViewModel()) {
    val navHostController = LocalNavController.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
            viewModel.signInResultReceiver.collectLatest {
                value: SignInResult ->
                when (value) {
                    is SignInResult.Error -> {
                        Toast.makeText(context, value.exception.message, Toast.LENGTH_SHORT).show()
                    }
                    is SignInResult.Success -> {
                        Toast.makeText(context, value.message, Toast.LENGTH_SHORT).show()
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
                }
            }
        }
    }
    GoogleSignScreen(
        modifier = modifier.imePadding().verticalScroll(rememberScrollState()),
        state =viewModel.state,
        intent = {
            intent->
            when (intent) {
                SignInScreenIntent.SignUp -> {
                    navHostController.navigate(SignInRoutesItems.SignUp.route){
                        launchSingleTop = true
                    }
                }
                else -> {}
            }
            viewModel.onIntent(intent)
        }
    )
}

@Composable
private fun GoogleSignScreen(
    modifier: Modifier = Modifier,
    state: SignInScreenState = SignInScreenState(),
    intent: (SignInScreenIntent) -> Unit = {}
) {
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
            Spacer(modifier = Modifier.height(16.dp))
            EmailTextFieldCustom(
                state = state.email,
                onTextChanged = {
                    intent(SignInScreenIntent.OnEmailChanged(it))
                }
            )
            PasswordTextFieldCustom(
                state = state.password,
                onTextChanged = { intent(SignInScreenIntent.OnPasswordChanged(it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(modifier = Modifier
                .fillMaxWidth(), onClick = { intent(SignInScreenIntent.Submit) }, shape = RoundedCornerShape(8.dp)
            ) {
                Text(modifier = Modifier.padding(vertical = 8.dp), text = "Sign In")
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                onClick = {
                    intent(SignInScreenIntent.SignUp)
                },
            ) {
                Text(
                    text = "Don't have an account? Sign Up",
                )
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Prev() {
    GoogleSignScreen()
}