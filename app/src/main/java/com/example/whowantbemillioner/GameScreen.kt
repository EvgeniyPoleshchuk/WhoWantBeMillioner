package com.example.whowantbemillioner

import android.app.Application
import android.media.MediaPlayer
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

var resulInfo: ResulInfo? = null
var currentInfo: CurrentInfo? = null
var buttonInfo: ButtonInfo = ButtonInfo()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    onClick: () -> Unit,
    EndGameScreen: () -> Unit,
    navController: NavHostController,
    application:Application
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val questionViewModel: MainViewModel = viewModel()
    val viewState by questionViewModel.questionsState
    val buffonHelper = remember { mutableStateOf(true) }
    val buffonHelper2 = remember { mutableStateOf(true) }
    val hasExtraLife = remember { mutableStateOf(false) }
    val buffonHelper3 = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    var isButtonEnabled by remember { mutableStateOf(true) }
    val questionCount = remember { mutableIntStateOf(currentInfo?.number ?: 0) }
    val timerRepeat = remember { mutableStateOf(true) }
    val timerCount = remember { mutableStateOf(30) }
    var timerColor by remember { mutableStateOf(White) }
    var isChecked by remember { mutableStateOf(true) }
    val alpha = remember { mutableFloatStateOf(buttonInfo.alfa) }
    val alpha2 = remember { mutableFloatStateOf(buttonInfo.alfa2) }
    val alpha3 = remember { mutableFloatStateOf(buttonInfo.alfa3) }
    val mediaPlayer by remember { mutableStateOf(MediaPlayer.create(application, R.raw.timer))}
    mediaPlayer.start()

    val snackbarHostState = remember { SnackbarHostState() }
    val resultFriend = remember { mutableStateOf("") }

    timerColor = when (timerCount.value) {
        in 11..20 -> Color(0xFFFFB340)
        in 0..10 -> Color(0xFFFF6231)
        else -> White
    }
    questionViewModel.loadQuestions(questionCount.intValue)

    var shuffledAnswers by remember(viewState.answers) {
        mutableStateOf(viewState.answers.shuffled())
    }


    if (timerCount.value == 0 || questionCount.intValue == 14) {
        EndGameScreen()
        resulInfo = ResulInfo(
            resulInfo?.name ?: "Нет имени",
            questionCount.intValue,
            cashList()[questionCount.intValue]
        )
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF374C94),
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "ВОПРОС ${questionCount.intValue + 1}",
                            color = White.copy(alpha = 0.5F), // Устанавливаем альфа-канал только для этого текста
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = cashList()[questionCount.intValue],
                            color = White, // Оставляем цвет остального текста без изменений
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
                            tint = White
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
                        color = Color(0xFFFFB340).copy(alpha = 0.3F),
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
                    color = timerColor.copy(alpha = 1F)
                )

            }
            Text(
                text = viewState.question,
                color = White,
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
                items(shuffledAnswers.size) {
                    var currentAnswer by remember { mutableStateOf("") }

                    val color = when (currentAnswer) {
                        "true" -> painterResource(id = R.drawable.answer_green)
                        "false" -> painterResource(id = R.drawable.answer_red)
                        "check" -> painterResource(id = R.drawable.big_rectangle_gold)
                        else -> painterResource(id = R.drawable.answer_blue)
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .size(65.dp)
                            .clickable(enabled = isButtonEnabled) {
                                isButtonEnabled = false
                                timerRepeat.value = false
                                currentAnswer = "check"
                                scope.launch {
                                    delay(5000)
                                    if (STUB
                                            .getTrueAnswer()
                                            .contains(shuffledAnswers[it])
                                    ) {
                                        currentAnswer = "true"
                                        isChecked = true
                                    } else {
                                        currentAnswer = "false"
                                        isChecked = false
                                    }

                                    isButtonEnabled = true
                                    timerCount.value = 30
                                    timerRepeat.value = true
                                    Log.i("!!!", "$isChecked")
                                    delay(1000)
                                    currentAnswer = "t"
                                    navController.navigate("ProgressScreen")
                                    questionCount.intValue++
                                    currentInfo = CurrentInfo(questionCount.intValue, isChecked)
                                }
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
                                text = shuffledAnswers[it],
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = White
                            )

                        }
                    }

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                val buttonList =
                    listOf(R.drawable.fifty_fifty, R.drawable.audience, R.drawable.call)
                Image(
                    painter = painterResource(id = buttonList[0]),
                    contentDescription = null,
                    modifier = Modifier
                        .size(95.dp, 75.dp)
                        .clickable(enabled = buffonHelper.value) {
                            val trueAnswers = shuffledAnswers.filter {
                                STUB
                                    .getTrueAnswer()
                                    .contains(it)
                            }
                            val answers = shuffledAnswers.first {
                                !STUB
                                    .getTrueAnswer()
                                    .contains(it)
                            }
                            val allAnswers = trueAnswers + answers

                            shuffledAnswers = allAnswers

                            alpha.floatValue = 0.5f
                            buttonInfo = ButtonInfo(
                                alfa = 0.5f,
                                alfa2 = alpha2.floatValue,
                                alfa3 = alpha3.floatValue
                            )
                            buffonHelper.value = false
                        },
                    contentScale = ContentScale.Crop,
                    alpha = alpha.floatValue
                )
                Image(
                    painter = painterResource(id = buttonList[1]),
                    contentDescription = null,
                    modifier = Modifier
                        .size(95.dp, 75.dp)
                        .clickable(enabled = buffonHelper2.value) {
                            val result = helpOfHall(shuffledAnswers.shuffled())
                            scope.launch {
                                snackbarHostState.showSnackbar(result)
                            }

                            alpha2.floatValue = 0.5f
                            buttonInfo = ButtonInfo(
                                alfa = alpha.floatValue,
                                alfa2 = 0.5f,
                                alfa3 = alpha3.floatValue
                            )
                            buffonHelper2.value = false
                        },
                    contentScale = ContentScale.Crop,
                    alpha = alpha2.floatValue
                )
                Image(
                    painter = painterResource(id = buttonList[2]),
                    contentDescription = null,
                    modifier = Modifier
                        .size(95.dp, 75.dp)
                        .clickable(enabled = buffonHelper3.value) {
                            val result = callFriend(shuffledAnswers.shuffled())

                            shuffledAnswers = shuffledAnswers.mapIndexed { _, element ->
                                if (element == result) {
                                    "$element - Выбор Друга"
                                } else {
                                    element
                                }
                            }
                            resultFriend.value = result
                            alpha3.floatValue = 0.5f
                            buttonInfo = ButtonInfo(
                                alpha.floatValue,
                                alfa2 = alpha2.floatValue,
                                alfa3 = 0.5f
                            )
                            buffonHelper3.value = false
                        },
                    contentScale = ContentScale.Crop,
                    alpha = alpha3.floatValue
                )
            }
            Log.i("!!!", "$buttonInfo")
        }
    }
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

fun helpOfHall(answers: List<String>): String {
    val randomValue = Random.nextInt(100)
    return if (randomValue < 70) {
        answers.random()
    } else {
        val wrongAnswers = answers.toMutableList().apply { remove(answers.random()) }
        wrongAnswers.random()
    }
}

fun callFriend(answers: List<String>): String {
    val randomValue = Random.nextInt(100)
    return if (randomValue < 80) {
        answers.random()
    } else {
        val wrongAnswers = answers.toMutableList().apply { remove(answers.random()) }
        wrongAnswers.random()
    }
}





