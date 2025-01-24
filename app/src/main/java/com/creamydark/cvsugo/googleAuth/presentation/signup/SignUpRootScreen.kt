package com.creamydark.cvsugo.googleAuth.presentation.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.auth.util.SignInResult
import com.creamydark.cvsugo.core.presentation.loading.LoadingScreen
import com.creamydark.cvsugo.core.presentation.rootscreen.LocalNavController
import com.creamydark.cvsugo.googleAuth.components.EmailTextFieldCustom
import com.creamydark.cvsugo.googleAuth.components.PasswordTextFieldCustom
import com.creamydark.cvsugo.googleAuth.presentation.signup.intent.SignUpScreenIntent
import com.creamydark.cvsugo.googleAuth.presentation.signup.state.SignUpScreenState
import com.creamydark.cvsugo.googleAuth.presentation.signup.viewmodel.SignUpViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpRootScreen(modifier: Modifier = Modifier,viewModel: SignUpViewModel = hiltViewModel()) {
    val state = viewModel.state
    val navHostController = LocalNavController.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
            viewModel.receiver.collectLatest {
                value: SignInResult ->
                when (value) {
                    is SignInResult.Error -> {
                        Toast.makeText(context, value.exception.message, Toast.LENGTH_SHORT).show()
                    }
                    is SignInResult.Success -> {
                        Toast.makeText(context, value.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    if (state.loading){

        LoadingScreen()

    }else{

        SignUpScreen(
            modifier = modifier
                .imePadding()
                .verticalScroll(rememberScrollState()),
            state = state,
            intent = {
                    intent ->
                when (intent) {
                    SignUpScreenIntent.SignIn -> {
                        navHostController.navigateUp()
                    }
                    else -> {}
                }
                viewModel.onIntent(intent)

            }
        )

    }

}

@Composable
private fun SignUpScreen(
    modifier: Modifier = Modifier,
    state: SignUpScreenState = SignUpScreenState(),
    intent: (SignUpScreenIntent) -> Unit = {}
) {
    Column(modifier = modifier
        .padding(16.dp)
        , horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
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
        Text(
            text = "Create an account",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        EmailTextFieldCustom(
            state = state.email,
            onTextChanged = {
                intent(SignUpScreenIntent.OnEmailChanged(it))
            }
        )
        PasswordTextFieldCustom(
            state = state.password,
            onTextChanged = { intent(SignUpScreenIntent.OnPasswordChanged(it)) }
        )
        PasswordTextFieldCustom(
            label = "Re-type your password",
            state = state.confirmPassword,
            onTextChanged = { intent(SignUpScreenIntent.OnConfirmPasswordChanged(it)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = Modifier
            .fillMaxWidth(), onClick = { intent(SignUpScreenIntent.Submit) }, shape = RoundedCornerShape(8.dp)) {
            Text(modifier = Modifier.padding(vertical = 8.dp), text = "Sign Up")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = {
                intent(SignUpScreenIntent.SignIn)
            },
        ) {
            Text(
                text = "Already have an account? Sign In",
            )
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Prev() {
    SignUpScreen()
}