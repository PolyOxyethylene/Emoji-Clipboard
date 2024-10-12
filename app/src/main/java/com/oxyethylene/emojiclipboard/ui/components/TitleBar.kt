package com.oxyethylene.emojiclipboard.ui.components

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleBar(
    modifier: Modifier = Modifier,
    title: String
) {

    val context = LocalContext.current

    Row (
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Button(
            onClick = {
                (context as Activity).finish()
            },
            modifier = Modifier.padding(start = 10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", modifier = Modifier.size(24.dp), tint = MaterialTheme.colorScheme.onSurface)
        }

        Text(
            text = title,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

    }

}