package com.example.whowantbemillioner

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.whowantbemillioner.data.QuestionsRepository
import kotlinx.coroutines.delay


@Composable
fun ProgressScreen(
    endGameScreen: () -> Unit,
    onClick: () -> Unit,
    number: Int? = currentInfo?.number,
    isChecked: Boolean? = currentInfo?.isChecked,
    application: Application,
) {
    val questionsRepository = QuestionsRepository(application)

    val image = painterResource(id = R.drawable.millionaire)
    val questions = listOf(
        "$500", "$1,000", "$2,000", "$3,000", "$5,000",
        "$7,500", "$10,000", "$12,500", "$15,000", "$25,000",
        "$50,000", "$100,000", "$250,000", "$500,000", "$1,000,000"
    )
    var wins = ""

    if (isChecked == false) {
        LaunchedEffect(Unit) {
            delay(1000)
            if (number != null && resulInfo != null) {
                wins = when (number) {
                    in 5..9 -> "$5,000"
                    in 9..13 -> "$25,000"
                    14 -> "$1,000,000"
                    else -> cashList()[number - 1]
                }
                questionsRepository.insertUserIntoCache(
                    ResulInfo(resulInfo!!.name, number, wins)
                )
                resulInfo = ResulInfo(resulInfo!!.name, number, wins)
            }
        }

        LaunchedEffect(Unit) {
            delay(3000)
            endGameScreen()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Gray, Color.Blue, Color.Gray)
                )
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.withdrawal),
            contentDescription = null,
            alignment = Alignment.TopStart,
            modifier = Modifier
                .size(35.dp)
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource()
                ) {
                    resulInfo = number?.let { ResulInfo(resulInfo!!.name, it, wins) }
                    endGameScreen()
                }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 105.dp),
            verticalArrangement = Arrangement.Top,
            reverseLayout = true
        ) {
            items(cashList().size) {
                val color =
                    if (it + 1 == 5 || it + 1 == 10) painterResource(id = R.drawable.rectangle_blue)
                    else if (it + 1 == 15) painterResource(id = R.drawable.rectangle_gold)
                    else if (it + 1 == number && isChecked == true) painterResource(id = R.drawable.answer_green)
                    else if (it + 1 == number && isChecked == false) painterResource(id = R.drawable.answer_red)
                    else painterResource(id = R.drawable.rectangle_dark_blue)

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .size(40.dp)
                ) {
                    Image(
                        painter = color,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 30.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${it + 1}:",
                            color = Color.White,

                            )
                        Text(
                            text = questions[it],
                            color = Color.White
                        )
                    }

                }
            }

        }

        Image(
            modifier = Modifier
                .padding(top = 40.dp)
                .size(85.dp),
            painter = image,
            contentDescription = null,
        )

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 30.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        if (isChecked != null) {
            Button(enabled = isChecked,
                onClick = {

                    onClick()
                    if (number != null) {
                        currentInfo = CurrentInfo(number, isChecked)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text("Продолжить")
            }
        }
    }
}

fun cashList(): List<String> {
    return listOf(
        "$500", "$1,000", "$2,000", "$3,000", "$5,000",
        "$7,500", "$10,000", "$12,500", "$15,000", "$25,000",
        "$50,000", "$100,000", "$250,000", "$500,000", "$1,000,000"
    )
}
