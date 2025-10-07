package com.example.mybugsactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mybugsactivity.present.DifficultyLevel
import com.example.mybugsactivity.present.Gender
import com.example.mybugsactivity.present.HelloWorld
import com.example.mybugsactivity.present.Registration
import com.example.mybugsactivity.ui.theme.MyBugsActivityTheme
import java.time.Clock
import java.time.Instant

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // fwefew
        setContent {
            MyBugsActivityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
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
                }
            }
        }
    }
}
