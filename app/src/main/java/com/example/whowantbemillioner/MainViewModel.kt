package com.example.whowantbemillioner

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _questionState = mutableStateOf(QuestionState())
    val questionsState: State<QuestionState> = _questionState

    init {
        fetchQuestions()
    }

    private fun fetchQuestions() {
        viewModelScope.launch {
            try {
                val response = getQuestionAll()
                _questionState.value = _questionState.value.copy(
                    list = response,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                _questionState.value = _questionState.value.copy(
                    loading = false,
                    error = "Ошибка получения вопросов ${e.message}"
                )
            }
        }
    }
}

data class QuestionState(
    val loading: Boolean = true,
    val list: QuestionsAll? = null,
    val error: String? = null
)