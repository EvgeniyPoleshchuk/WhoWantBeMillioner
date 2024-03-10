package com.example.whowantbemillioner

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ResulInfo(
    @PrimaryKey
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "coin") val coin:Int,
    @ColumnInfo(name = "cash") val cash:String
)

data class CurrentInfo(val number: Int, val isChecked: Boolean)
data class ButtonInfo(val alfa: Float = 1f, val alfa2: Float = 1f, val alfa3: Float = 1f)



