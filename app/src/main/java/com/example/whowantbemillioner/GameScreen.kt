package com.example.whowantbemillioner

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

var resulInfo: ResulInfo? = null

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    onClick: () -> Unit,
    EndGameScreen: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val questionViewModel: MainViewModel = viewModel()
    val viewState by questionViewModel.questionsState
    var isButtonEnabled by remember { mutableStateOf(true) }
    val questionCount = remember { mutableIntStateOf(0) }
    val questionDif = remember { mutableStateOf(0) }
    val timerRepeat = remember { mutableStateOf(true) }
    val timerCount = remember { mutableStateOf(30) }
    val count = remember { mutableIntStateOf(0) }
    var timerColor by remember { mutableStateOf(Color.White) }

    timerColor = when (timerCount.value){
        in 11..20 -> Color(0xFFFFB340)
        in 0..10 -> Color(0xFFFF6231)
        else -> Color.White
    }

    if (timerCount.value == 0 || count.intValue == 14 ) {
        EndGameScreen()
        resulInfo = ResulInfo(count.intValue + 1, cashList()[count.intValue])
    }

    if (questionCount.intValue == 5) {
        questionCount.intValue = 0
        questionDif.value++
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF374C94),
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "ВОПРОС ${count.intValue + 1}",
                            color = Color.White.copy(alpha = 0.5F), // Устанавливаем альфа-канал только для этого текста
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = cashList()[count.intValue],
                            color = Color.White, // Оставляем цвет остального текста без изменений
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onClick() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description",
                            tint = Color.White
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        containerColor = Color(0xFF374C94)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .background(
                        color = timerColor.copy(alpha = 0.3F),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .padding(16.dp, 8.dp, 16.dp, 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.stopwatch),
                    contentDescription = null,
                    tint = timerColor.copy(alpha = 1F)
                )
                Text(
                    text = countDownTimer(timerCount, timerRepeat).toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = timerColor
                )
            }
            Text(
                text = question(viewState,questionCount.intValue, questionDif.value),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .size(100.dp),
                textAlign = TextAlign.Center
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(280.dp)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                items(4) {
                    var answer by remember { mutableStateOf("") }
                    var color = when (answer) {
                        "true" -> painterResource(id = R.drawable.answer_green)
                        "false" -> painterResource(id = R.drawable.answer_red)
                        else -> painterResource(id = R.drawable.answer_blue)
                    }
                    val timerHandler = Handler(Looper.getMainLooper())
                    val timerRunnable = Runnable {
                        count.intValue++
                        questionCount.intValue++
                        isButtonEnabled = true
                        answer = "t"
                        timerCount.value = 30
                        timerRepeat.value = true
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .size(65.dp)
                            .clickable(enabled = isButtonEnabled) {
                                answer = "true"
                                isButtonEnabled = false
                                timerRepeat.value = false
                                timerHandler.postDelayed(timerRunnable, 5000)
                            }

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
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "${
                                    when (it) {
                                        0 -> "A"
                                        1 -> "B"
                                        2 -> "C"
                                        else -> "D"
                                    }
                                }:  ",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFB340)
                            )

                            Text(
                                text = questionAnswer(viewState,
                                    questionCount.intValue,
                                    questionDif.value)[it],
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                        }
                    }

                }
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                val buttonList =
                    listOf(R.drawable.fifty_fifty, R.drawable.audience, R.drawable.call)
                items(buttonList.size) {
                    Image(
                        painter = painterResource(id = buttonList[it]),
                        contentDescription = null,
                        modifier = Modifier.size(95.dp, 75.dp),
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }
    }
}

fun questionAnswer(viewState:QuestionState, question: Int, dif: Int): List<String> {
   var a = listOf("","","","")
    if(viewState.list != null){
     a = when (dif) {
         0 -> viewState.list.questionEasy.data.get(question).answers
         1 -> viewState.list.questionMedium.data.get(question).answers
         2 -> viewState.list.questionHard.data.get(question).answers
            else -> {
                listOf("")
            }
        }
    }
    return a
}



fun question(viewState:QuestionState,count: Int, dif: Int): String {
    var a = ""
    if(viewState.list != null) {
       a = when (dif) {
            0 -> return viewState.list.questionEasy.data.get(count).question
            1 -> return viewState.list.questionMedium.data.get(count).question
            2 -> return viewState.list.questionHard.data.get(count).question
           else -> {
               ""
           }
       }
    }
    return a
}

@Composable
fun countDownTimer(value: MutableState<Int>, isRunning: MutableState<Boolean>): Int {
    var seconds by remember { mutableStateOf(value) }
    LaunchedEffect(isRunning.value) {
        while (isRunning.value) {
            delay(1000)
            seconds.value--
        }
    }
    if (!isRunning.value) {
        seconds = value
    }
    return seconds.value
}