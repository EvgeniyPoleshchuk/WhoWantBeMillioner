package com.example.whowantbemillioner

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _questionState = mutableStateOf(QuestionState())
    val questionsState: State<QuestionState> = _questionState

    fun loadQuestions(number: Int) {
        val question = STUB.getQuestion(number)
        _questionState.value = _questionState.value.copy(
            question = question
        )
    }
}

data class QuestionState(
    val question: Question? = null,
)