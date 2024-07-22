package com.creamydark.cvsugo.presentation.screens.signin

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.presentation.screens.signin.viewmodel.SignInViewModel
import com.creamydark.cvsugo.presentation.screens.util.composables.TwoSelectableComponent
import com.creamydark.cvsugo.domain.enums.UserChooserType

@Composable
fun SignInScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewmodel: SignInViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var studentID by remember { mutableStateOf("MINJU2024") }
    var userPassword by remember { mutableStateOf("cvSUHenyo") }
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(scrollState),
    ) {
        var currentSelectedOption by remember { mutableStateOf(UserChooserType.Student) }
//        Image(painter = painterResource(id = R.drawable.cvsu_clean_logo), contentDescription = "", modifier = Modifier.size(220.dp))
        Text(
            text = "Cavite State University Portal",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 42.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
        )
        Text(
            text = "Sign In",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Please sign in to your account to access all the features of the \"CVSU Go\" app. Enter your credentials below.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TwoSelectableComponent(
            modifier = Modifier
                .fillMaxWidth(),
            selectedOption = currentSelectedOption,
            onSelect = {
                currentSelectedOption = it
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        val usernameLabel = if (currentSelectedOption == UserChooserType.Student) "Student ID" else "Employee Number"

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Person, contentDescription = "")
            },
            value = studentID,
            onValueChange = {

            },
            label = {
                Text(text = usernameLabel)
            },
            placeholder = {
                Text(text = "Enter your $usernameLabel")
            },
            singleLine = true,
            readOnly = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        var passwordVisible by remember { mutableStateOf(false) }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Lock, contentDescription = "")
            },
            value = userPassword,
            onValueChange = {

            },
            label = {
                Text(text = "Password")
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                val icon = if (passwordVisible) R.drawable.outline_visibility_24 else R.drawable.outline_visibility_off_24
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                Toast.makeText(context, "Signing In", Toast.LENGTH_SHORT).show()
                viewmodel.updateLoginState(true)
                /*navHostController.navigate(MainGraph.Home.route){
                    popUpTo(AccountScreens.SignInScreen.route){
                        inclusive = true
                    }
                    launchSingleTop = true
                }*/
            },
        ) {
            Text(modifier = Modifier.padding(vertical = 6.dp), text = "Sign In")
        }
        Text(
            text = "This is a demo. Text fields editing is disabled.",
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}