package com.example.mybugsactivity.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

data class ZodiacSign(
    val name: String,
    val startMonth: Int,
    val startDay: Int,
    val endMonth: Int,
    val endDay: Int,
    val urlImg: String = "",
)

val zodiacSigns = listOf(
    ZodiacSign("Овен", 3, 21, 4, 19, "https://amorem.ru/upload/iblock/9ef/post_5d1eeca37c95f.jpeg"),
    ZodiacSign("Телец", 4, 20, 5, 20,"https://aif-s3.aif.ru/images/040/566/ceed55489da6b1da3f44e977c2c9fed3.webp"),
    ZodiacSign("Близнецы", 5, 21, 6, 20,"https://cdn.7days.ru/pic/b82/981127/1439718/86.jpg"),
    ZodiacSign("Рак", 6, 21, 7, 22,"https://img.freepik.com/premium-photo/cancer-zodiac-sign-abstract-night-sky-background-cancer-icon-blue-space-background_101969-2781.jpg"),
    ZodiacSign("Лев", 7, 23, 8, 22,"https://i.pinimg.com/736x/35/f9/14/35f914e7baf91fc5a196428546bb841c.jpg"),
    ZodiacSign("Дева", 8, 23, 9, 22,"https://amorem.ru/upload/iblock/9ef/post_5d1eeca37c95f.jpeg"),
    ZodiacSign("Весы", 9, 23, 10, 22,"https://amorem.ru/upload/iblock/9ef/post_5d1eeca37c95f.jpeg"),
    ZodiacSign("Скорпион", 10, 23, 11, 21,"https://amorem.ru/upload/iblock/9ef/post_5d1eeca37c95f.jpeg"),
    ZodiacSign("Стрелец", 11, 22, 12, 21,"https://amorem.ru/upload/iblock/9ef/post_5d1eeca37c95f.jpeg"),
    ZodiacSign("Козерог", 12, 22, 1, 19,"https://amorem.ru/upload/iblock/9ef/post_5d1eeca37c95f.jpeg"),
    ZodiacSign("Водолей", 1, 20, 2, 18,"https://amorem.ru/upload/iblock/9ef/post_5d1eeca37c95f.jpeg"),
    ZodiacSign("Рыбы", 2, 19, 3, 20,"https://amorem.ru/upload/iblock/9ef/post_5d1eeca37c95f.jpeg"),
)

@RequiresApi(Build.VERSION_CODES.O)
fun valZodiacSign(instant: Instant): String {
    val date = instant.atZone(ZoneId.systemDefault()).toLocalDate()
    val day = date.dayOfMonth
    val month = date.monthValue

    return zodiacSigns.find { sign ->
        (month == sign.startMonth && day >= sign.startDay) ||
                (month == sign.endMonth && day <= sign.endDay) ||
                (sign.startMonth < sign.endMonth && month > sign.startMonth && month < sign.endMonth) ||
                (sign.startMonth > sign.endMonth && (month > sign.startMonth || month < sign.endMonth))
    }?.name ?: "Неизвестный знак"
}