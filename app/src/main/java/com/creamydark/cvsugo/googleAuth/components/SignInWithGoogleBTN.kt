package com.creamydark.cvsugo.googleAuth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.creamydark.cvsugo.R

@Composable
fun SignInWithGoogleBTN(modifier: Modifier = Modifier,onSignInClick: () -> Unit = {}) {
    Card(
        modifier = modifier,
        onClick = {
            onSignInClick()
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.icons8_google),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Sign in with Google")
        }
    }
}

@Preview
@Composable
private fun Prev() {
    SignInWithGoogleBTN()
}