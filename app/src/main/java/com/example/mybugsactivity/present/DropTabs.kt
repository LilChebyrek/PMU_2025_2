package com.example.mybugsactivity.present


import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Spanned
import android.webkit.WebView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.TypedArrayUtils.getString
import coil3.compose.AsyncImage
import com.example.mybugsactivity.R
import com.example.mybugsactivity.present.DifficultyLevel
import com.example.mybugsactivity.present.Gender
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropTabs(modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Регистрация", "Правила", "Авторы", "Настройки")

    Column(modifier = modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> RegistrationTab()
            1 -> RulesTab()
            2 -> AuthorsTab()
            3 -> SettingsTab()
        }
    }
}

@Composable
fun RegistrationTab() {
    Registration(
        fullName = "Name",
        gender = Gender.MALE,
        course = 1,
        difficultyLevel = DifficultyLevel.EASY,
        dateOfBirth = Instant.now(),
        zodiakSign = "https://www.google.com/url?sa=i&url=https%3A%2F%2Faif.ru%2Fsociety%2Fhoroscopes%2Flev-polnaya-harakteristika-znaka-zodiaka&psig=AOvVaw2uHHPJmyLOHFUaAk70Gvjr&ust=1757525358838000&source=images&cd=vfe&opi=89978449&ved=0CBUQjRxqFwoTCNimxamazI8DFQAAAAAdAAAAABAE",
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    )
}

@Composable
fun RulesTab() {
    val context = LocalContext.current
    val text = context.getString(R.string.game_rules)
    val styledText: Spanned = Html.fromHtml(text, FROM_HTML_MODE_LEGACY)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(factory = {
            WebView(it).apply {
                loadData(
                    context.getString(R.string.game_rules),
                    "text/html",
                    "UTF-8"
                )
            }
        })
    }
}

@Composable
fun AuthorsTab() {
    val authors = remember {
        listOf(
            Author(
                "Нарзиев Тимур",
                "https://sun9-43.userapi.com/s/v1/ig2/Bh89r5X4VzZxjS9sPNmucjZzTIXJS_dxTfgUZy83a51c71y49G7_yrToV_uTO-HxWEFXCom33JktGsYAl80nTaJd.jpg?quality=95&as=32x56,48x85,72x127,108x191,160x282,240x423,331x584&from=bu&cs=331x0"
            ),
            Author(
                "Гончар Алексей",
                "https://sun9-34.userapi.com/s/v1/ig2/-p8HkoUkYcwdQkv_hlFOftxLtFUsEnpnnEIrAfHZM_CZ4IRqoEVzCZ-pGvzUteFkgo7W-SB0oInkVlk5mgJX2reh.jpg?quality=95&as=32x43,48x64,72x96,108x144,160x213,240x320,360x480,480x640,540x720,640x853,720x960,960x1280&from=bu&cs=960x0"
            )
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(authors) { author ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = author.photoUrl,
                    contentDescription = "Фото ${author.name}",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(androidx.compose.foundation.shape.CircleShape)
                )
                Text(
                    text = author.name,
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            HorizontalDivider()
        }
    }
}

data class Author(val name: String, val photoUrl: String)

@Composable
fun SettingsTab() {
    var gameSpeed by remember { mutableFloatStateOf(1f) }
    var maxBugs by remember { mutableStateOf("5") }
    var bonusInterval by remember { mutableStateOf("10") }
    var roundDuration by remember { mutableStateOf("60") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Скорость игры")
        Slider(
            value = gameSpeed,
            onValueChange = { gameSpeed = it },
            valueRange = 0.5f..2.0f,
            modifier = Modifier.fillMaxWidth()
        )

        Text("Максимальное количество тараканов на экране")
        TextField(
            value = maxBugs,
            onValueChange = { maxBugs = it },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Интервал появления бонусов (секунды)")
        TextField(
            value = bonusInterval,
            onValueChange = { bonusInterval = it },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Длительность раунда (секунды)")
        TextField(
            value = roundDuration,
            onValueChange = { roundDuration = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
}