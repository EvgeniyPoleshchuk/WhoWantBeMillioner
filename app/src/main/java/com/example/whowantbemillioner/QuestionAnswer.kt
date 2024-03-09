package com.example.whowantbemillioner

data class QuestionAnswer(
    val amount: Int,
    val `data`: List<Data>,
    val ok: Boolean
)

data class Data(
    val answers: List<String>,
    val id: Int,
    val question: String
)