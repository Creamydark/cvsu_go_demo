package com.creamydark.cvsugo.googleAuth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.googleAuth.components.state.PasswordTextFieldState


@Composable
fun PasswordTextFieldCustom(modifier: Modifier = Modifier,label:String = "Password",state: PasswordTextFieldState = PasswordTextFieldState(),onTextChanged: (String) -> Unit) {
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(imageVector = Icons.Outlined.Lock, contentDescription = "")
        },
        value = state.text,
        onValueChange = {
            onTextChanged(it)
        },
        label = {
            Text(text = label)
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        readOnly = state.readOnly,
        trailingIcon = {
            val icon = if (passwordVisible) R.drawable.outline_visibility_24 else R.drawable.outline_visibility_off_24
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                )
            }
        },
        supportingText = {
            if (state.errorMessage != null) {
                Text(text = "${state.errorMessage}", color = Color.Red)
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun Prev() {
//    EmailTextFieldCustom()
}