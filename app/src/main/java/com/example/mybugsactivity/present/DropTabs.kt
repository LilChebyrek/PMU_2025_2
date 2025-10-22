package com.example.mybugsactivity.present

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import android.widget.TextView
import coil3.compose.AsyncImage
import com.example.mybugsactivity.R
import java.time.Instant
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random
import kotlinx.coroutines.isActive
import kotlinx.coroutines.yield
import androidx.core.text.HtmlCompat
import androidx.compose.ui.res.imageResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropTabs(modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Игра", "Регистрация", "Правила", "Авторы", "Настройки")

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
            0 -> GameTab()
            1 -> RegistrationTab()
            2 -> RulesTab()
            3 -> AuthorsTab()
            4 -> SettingsTab()
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
    val scrollState = rememberScrollState()
    val rulesSpanned = remember(context) {
        HtmlCompat.fromHtml(
            context.getString(R.string.game_rules),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        AndroidView(
            factory = { ctx ->
                TextView(ctx).apply {
                    text = rulesSpanned
                    textSize = 16f
                }
            },
            update = { view ->
                view.text = rulesSpanned
            },
            modifier = Modifier.fillMaxWidth()
        )
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

private class BugState(
    val id: Int,
    val image: ImageBitmap,
    val drawSize: IntSize,
    val points: Int,
    position: Offset,
    velocity: Offset,
) {
    var position by mutableStateOf(position)
    var velocity by mutableStateOf(velocity)
    val halfWidth = drawSize.width / 2f
    val halfHeight = drawSize.height / 2f

    fun contains(point: Offset): Boolean {
        return point.x in (position.x - halfWidth)..(position.x + halfWidth) &&
                point.y in (position.y - halfHeight)..(position.y + halfHeight)
    }
}

private data class BugSprite(
    val image: ImageBitmap,
    val points: Int,
)

@Composable
fun GameTab() {
    val bugs = remember { mutableStateListOf<BugState>() }
    var score by remember { mutableIntStateOf(0) }
    var misses by remember { mutableIntStateOf(0) }
    var highScore by remember { mutableIntStateOf(0) }
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }
    val context = LocalContext.current
    val bugSprites = remember(context) {
        listOf(
            BugSprite(
                image = ImageBitmap.imageResource(context.resources, R.drawable.bug_brown),
                points = 3
            ),
            BugSprite(
                image = ImageBitmap.imageResource(context.resources, R.drawable.bug_grey),
                points = 2
            ),
            BugSprite(
                image = ImageBitmap.imageResource(context.resources, R.drawable.bug_red),
                points = 5
            ),
        )
    }
    val random = remember { Random(System.currentTimeMillis()) }
    var bugId by remember { mutableIntStateOf(0) }

    LaunchedEffect(canvasSize) {
        if (canvasSize == IntSize.Zero) return@LaunchedEffect
        var lastFrameTime = 0L
        while (isActive) {
            val frameTime = withFrameNanos { it }
            if (lastFrameTime != 0L) {
                val deltaSeconds = (frameTime - lastFrameTime) / 1_000_000_000f
                updateBugPositions(
                    bugs = bugs,
                    deltaSeconds = deltaSeconds,
                    canvasSize = canvasSize
                )
            }
            lastFrameTime = frameTime

            if (bugs.size < 6) {
                val sprite = bugSprites.random(random)
                bugs += createBug(
                    id = bugId++,
                    canvasSize = canvasSize,
                    random = random,
                    image = sprite.image,
                    points = sprite.points
                )
            }
            yield()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Счёт: $score", style = MaterialTheme.typography.titleLarge)
            Text(text = "Лучший: $highScore", style = MaterialTheme.typography.titleMedium)
            Text(text = "Промахи: $misses", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(bugs) {
                        detectTapGestures { offset ->
                            val hitBug = bugs.lastOrNull { bug ->
                                bug.contains(offset)
                            }
                            if (hitBug != null) {
                                bugs.remove(hitBug)
                                score += hitBug.points
                                if (score > highScore) {
                                    highScore = score
                                }
                            } else {
                                misses += 1
                                score = maxOf(0, score - 2)
                            }
                        }
                    }
                    .onSizeChanged { canvasSize = it }
            ) {
                bugs.forEach { bug ->
                    drawBug(bug = bug)
                }
            }

            if (bugs.isEmpty()) {
                Text(
                    text = "Подождите... насекомые собираются в кучку!",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        FilledTonalButton(
            onClick = {
                bugs.clear()
                score = 0
                misses = 0
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Перезапустить раунд")
        }
    }
}

private fun updateBugPositions(
    bugs: MutableList<BugState>,
    deltaSeconds: Float,
    canvasSize: IntSize
) {
    if (canvasSize == IntSize.Zero) return
    val width = canvasSize.width.toFloat()
    val height = canvasSize.height.toFloat()

    bugs.forEach { bug ->
        var px = bug.position.x + bug.velocity.x * deltaSeconds
        var py = bug.position.y + bug.velocity.y * deltaSeconds
        var vx = bug.velocity.x
        var vy = bug.velocity.y

        if (px - bug.halfWidth < 0f || px + bug.halfWidth > width) {
            vx = -vx
            px = px.coerceIn(bug.halfWidth, width - bug.halfWidth)
        }
        if (py - bug.halfHeight < 0f || py + bug.halfHeight > height) {
            vy = -vy
            py = py.coerceIn(bug.halfHeight, height - bug.halfHeight)
        }

        bug.position = Offset(px, py)
        bug.velocity = Offset(vx, vy)
    }
}

private fun createBug(
    id: Int,
    canvasSize: IntSize,
    random: Random,
    image: ImageBitmap,
    points: Int,
): BugState {
    val drawWidth = image.width.coerceAtLeast(24)
    val drawHeight = image.height.coerceAtLeast(24)

    val halfWidth = drawWidth / 2f
    val halfHeight = drawHeight / 2f

    val availableWidth = (canvasSize.width - drawWidth).coerceAtLeast(0)
    val availableHeight = (canvasSize.height - drawHeight).coerceAtLeast(0)
    val startX = halfWidth + random.nextFloat() * availableWidth
    val startY = halfHeight + random.nextFloat() * availableHeight

    val speed = 80f + random.nextFloat() * 160f
    val angle = random.nextDouble(0.0, 2.0 * PI)
    val velocity = Offset(
        (speed * cos(angle)).toFloat(),
        (speed * sin(angle)).toFloat()
    )

    return BugState(
        id = id,
        image = image,
        drawSize = IntSize(drawWidth, drawHeight),
        points = points,
        position = Offset(startX, startY),
        velocity = velocity
    )
}

private fun DrawScope.drawBug(bug: BugState) {
    val halfWidth = bug.halfWidth
    val halfHeight = bug.halfHeight
    val topLeft = Offset(
        x = bug.position.x - halfWidth,
        y = bug.position.y - halfHeight
    )
    drawContext.canvas.drawImage(bug.image, topLeft, Paint())
}
