@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mybugsactivity.present

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.mybugsactivity.util.valZodiacSign
import com.example.mybugsactivity.util.zodiacSigns
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Registration(
    fullName: String,
    gender: Gender,
    course: Int = 1,
    difficultyLevel: DifficultyLevel = DifficultyLevel.EASY,
    dateOfBirth: Instant,
    zodiakSign: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    val selectedZodiac = remember { mutableStateOf("") }
    val datePickerState = rememberDatePickerState()

    var fullNameRemember by remember { mutableStateOf(fullName) }
    val genders = Gender.entries.toList()
    var selectedGender by remember { mutableStateOf<Gender?>(null) }

    var sliderSet by remember { mutableFloatStateOf(0f) }
    var sliderPosition by remember { mutableFloatStateOf(0f) }

    Column(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        TextField(
            value = fullNameRemember,
            onValueChange = { name ->
                fullNameRemember = name
            },
            modifier = Modifier.fillMaxWidth(),
        )

        Text(
            text = "Выберите пол:",
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 15.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            genders.forEach { gender ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    RadioButton(
                        selected = gender == selectedGender,
                        onClick = {
                            selectedGender = gender
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.primary, // Selected color
                            unselectedColor = MaterialTheme.colorScheme.onSurface // Unselected color
                        )
                    )
                    Text(
                        text = gender.s
                    )

                }

            }
        }

        SelectCourse(
            courses = (1..6).toList(),
            onSelectCourse = { course ->

            }
        )

        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
            },
            valueRange = 0f..0.99f,
            steps = 1,
            modifier = Modifier.fillMaxWidth(),
        )

        val currentDifficultyLevel = when {
            sliderPosition < 0.33f -> DifficultyLevel.EASY
            sliderPosition < 0.66f -> DifficultyLevel.MEDIUM
            else -> DifficultyLevel.HARD
        }
        Text(
            text = "Уровень сложности: ${currentDifficultyLevel.name}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 1.dp, bottom = 20.dp),
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )


        Button(onClick = { showDatePicker = true }) {
            Text("Выбрать дату")
        }

        Text(
            text = if (selectedDate.isEmpty()) {
                "Дата не выбрана"
            } else {
                "Выбрано: ${selectedDate} (${selectedZodiac.value})"
            },
            modifier = Modifier.padding(top = 16.dp)
        )

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    Button(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                            selectedDate = formatter.format(Date(millis))

                            val instant = Instant.ofEpochMilli(millis)
                            selectedZodiac.value = valZodiacSign(instant)

                            Toast.makeText(
                                context,
                                "Выбрана дата: ${selectedDate}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        showDatePicker = false
                    }) {
                        Text("Подтвердить")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDatePicker = false }) {
                        Text("Отмена")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        AsyncImage(
            modifier = Modifier
                .size(256.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
            model = zodiacSigns.find { it.name == selectedZodiac.value }?.urlImg,
            contentDescription = selectedZodiac.value,
            onError = {
                Log.d("MYLOG", it.result.throwable.message.toString())
            }
        )
        Text(
            text = " "
        )
    }
}

@Composable
fun SelectCourse(
    courses: List<Int>,
    onSelectCourse: (Int) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Выберите курс") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            enabled = false,
            label = { Text("Нажмите для выбора") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            courses.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.toString()) },
                    onClick = {
                        selectedItem = item.toString()
                        onSelectCourse(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

enum class DifficultyLevel {
    EASY,
    MEDIUM,
    HARD,
}

enum class Gender(val s: String) {
    FEMALE("Женский"),
    MALE("Мужской")
}