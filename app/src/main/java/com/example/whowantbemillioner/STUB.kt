package com.example.whowantbemillioner

object STUB {

    private fun getAllQuestions() = Questions(questionsEasy + questionsMedium + questionsHard)
    fun getQuestion(number: Int) = getAllQuestions().questions[number]
    fun getTrueAnswer() = getAllQuestions().questions.map { it.answers.first() }

    private val questionsEasy = listOf(
        Question(
            question = "Во что превращается вода на морозе?",
            answers = listOf("В лёд", "В пар", "В газ", "В кисель")
        ),
        Question(
            question = "Чем едят суп?",
            answers = listOf("Ложкой", "Вилкой", "Совочком", "Руками")
        ),
        Question(
            question = "Что чаще всего вешают на елку?",
            answers = listOf("Шарики", "Кубики", "Тюбики", "Зубики")
        ),
        Question(
            question = "Что растет на дубе?",
            answers = listOf("Жёлуди", "Яблоки", "Шишки", "Златая цепь")
        ),
        Question(
            question = "Кто приходит под Новый год к хорошим детям?",
            answers = listOf("Дед Мороз", "Баба Яга", "Полицейский", "Шапокляк")
        )
    )

    private val questionsMedium = listOf(
        Question(
            question = "На территории какой страны был открыт золотоносный район Клондайк?",
            answers = listOf("Канада", "США", "Мексика", "Венесуэла")
        ),
        Question(
            question = "Сколько корон на Государственном гербе РФ?",
            answers = listOf("Три", "Одна", "Две", "Пять")
        ),
        Question(
            question = "Какой персонаж оперы Глинки \"Руслан и Людмила\" не поёт?",
            answers = listOf("Черномор", "Ратмир", "Наина", "Финн")
        ),
        Question(
            question = "Где проходила Олимпиада, на которой бросок А. Белова вывел баскетбольную команду СССР в чемпионы?",
            answers = listOf("Мюнхен", "Рим", "Монреаль", "Мехико")
        ),
        Question(
            question = "В какой стране столица порт?",
            answers = listOf("Сингапур", "Италия", "Канада", "Мексика")
        )
    )

    private val questionsHard = listOf(
        Question(
            question = "В какой из этих столиц бывших союзных республик раньше появилось метро?",
            answers = listOf("Тбилиси", "Минск", "Ереван", "Баку")
        ),
        Question(
            question = "Какой химический элемент назван в честь злого подземного гнома?",
            answers = listOf("Кобальт", "Берилий", "Теллур", "Гафний")
        ),
        Question(
            question = "Кто из перечисленных был пажом во времена Екатерины II?",
            answers = listOf("А.Н. Радищев", "Н.М. Карамзин", "Д.И. Фонвизин", "Г.Р. Державин")
        ),
        Question(
            question = "Реки с каким названием нет на территории России?",
            answers = listOf("Спина", "Шея", "Уста", "Палец")
        ),
        Question(
            question = "В честь какого растения область Фриули-Венеция-Джулия в Италии ежегодно проводит трёхмесячный фестиваль?",
            answers = listOf("Спаржа", "Лук", "Фасоль", "Артишок")
        )
    )

}

