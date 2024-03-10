package com.example.whowantbemillioner


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun EndScreen(
    navigateToMainScreen: () -> Unit,
    navigateToGameScreen: () -> Unit,
    level: Int? = resulInfo?.coin,
    coin: String? = resulInfo?.cash
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Gray, Color.Blue, Color.Gray)
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(200.dp),
            painter = painterResource(id = R.drawable.millionaire),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Игра окончена",
            fontSize = 36.sp,
            lineHeight = 42.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(horizontal = 50.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Ваш уровень  " + level,
            fontSize = 19.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {

            Image(
                painter = painterResource(id = R.drawable.coin),
                contentDescription = "Coin",
                Modifier
                    .padding(2.dp)
                    .size(16.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = coin.toString(),
                fontSize = 16.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(100.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(80.dp)
                .padding(horizontal = 20.dp)
                .clickable { },
        ) {
            Image(
                painter = painterResource(id = R.drawable.big_rectangle_gold),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ) {
                        currentInfo = CurrentInfo(0,true)
                          navigateToGameScreen()
                    }
            )
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Новая игра",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(80.dp)
                .padding(horizontal = 20.dp)

        ) {
            Image(
                painter = painterResource(id = R.drawable.big_rectangle_dark_blue),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ) {
                        currentInfo = CurrentInfo(0,true)
                        navigateToMainScreen()

                    }
            )

            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Главный экран",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White

                )
            }
        }
    }
}

@Preview
@Composable
fun EndScreenPreview() {
    EndScreen({}, {})
}