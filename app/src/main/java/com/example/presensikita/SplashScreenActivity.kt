package com.example.presensikita

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.example.presensikita.ui.theme.PresensiKitaTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menggunakan Jetpack Compose untuk menampilkan SplashScreen
        setContent {
            PresensiKitaTheme {
                SplashScreen()
            }
        }

        // Menunda selama 3 detik sebelum pindah ke MainActivity
        lifecycleScope.launch {
            delay(3000) // Delay selama 3 detik
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish() // Tutup SplashScreenActivity
        }
    }
}

@Composable
fun SplashScreen() {
    // ConstraintLayout di Compose untuk meniru tata letak XML
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        // Atur constraints seperti di XML
        val (image) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.solutions__converted_),
            contentDescription = "Splash Image",
            modifier = Modifier
                .size(224.dp, 553.dp) // Menggunakan ukuran yang mirip dengan XML
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentScale = ContentScale.Fit // Sama seperti `scaleType="fitCenter"`
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    PresensiKitaTheme {
        SplashScreen()
    }
}
