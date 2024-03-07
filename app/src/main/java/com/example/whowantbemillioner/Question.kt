package com.example.whowantbemillioner

data class QuestionsAll(
    val questionEasy: QuestionsResponseEasy,
    val questionMedium: QuestionsResponseMedium,
    val questionHard: QuestionsResponseHard
)

data class QuestionsEasy(
    val question: String,
    val answers: List<String>
)

data class QuestionsMedium(
    val question: String,
    val answers: List<String>
)

data class QuestionsHard(
    val question: String,
    val answers: List<String>
)

data class QuestionsResponseEasy(val data: List<QuestionsEasy>)
data class QuestionsResponseMedium(val data: List<QuestionsMedium>)
data class QuestionsResponseHard(val data: List<QuestionsHard>)