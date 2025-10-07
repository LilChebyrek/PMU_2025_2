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
import com.example.mybugsactivity.present.DropTabs
import com.example.mybugsactivity.ui.theme.MyBugsActivityTheme
import java.time.Clock
import java.time.Instant

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyBugsActivityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DropTabs(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
