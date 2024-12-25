package com.example.presensikita

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.presensikita.ui.theme.PresensiKitaTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PresensiKitaTheme {
                Scaffold {
//                    ScheduleTableScreen(
//                        schedules = dummySchedules(),
//                        onBack = { finish() } // Aksi untuk tombol kembali
//                    )
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    PresensiKitaTheme {

//        ScheduleTableScreen(
//            schedules = dummySchedules(),
//            onBack = { finish() } // Aksi untuk tombol kembali
//        )
    }
}

fun finish() {
    TODO("Not yet implemented")
}
