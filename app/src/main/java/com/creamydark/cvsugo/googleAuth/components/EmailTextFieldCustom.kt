package com.creamydark.cvsugo.googleAuth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.creamydark.cvsugo.googleAuth.components.state.EmailTextFieldState

@Composable
fun EmailTextFieldCustom(modifier: Modifier = Modifier,state: EmailTextFieldState = EmailTextFieldState(),onTextChanged: (String)->Unit = {}) {
    OutlinedTextField(
        value = state.text,
        leadingIcon = { Icon(imageVector = Icons.Outlined.Email, contentDescription = "")},
        onValueChange = { onTextChanged(it) },
        label = { Text("Email") },
        placeholder = { Text("cvshenyo@cvsu.edu.ph") },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth(),
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
    EmailTextFieldCustom()
}