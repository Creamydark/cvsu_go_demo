package com.creamydark.cvsugo.accountSetup.presentation.setupUsername

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun UsernameSetupRootScreen(modifier: Modifier = Modifier) {

}

@Composable
fun UsernameSetupScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(horizontal = 16.dp)){
        Column {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Username")
                },
                value = "",
                onValueChange = {}
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Prev() {
    UsernameSetupScreen()
}