package com.example.whowantbemillioner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun RulesScreen(onClick:() -> Unit) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Gray, Color.Blue, Color.Gray)
                )
            )
    ) {
        item {
            IconButton(onClick = { onClick() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
            Text(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .padding(top = 10.dp),
                text = stringResource(R.string.rules),
                color = Color.White
            )

        }
    }
}
