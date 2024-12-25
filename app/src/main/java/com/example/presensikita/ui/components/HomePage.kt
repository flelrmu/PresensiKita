package com.example.presensikita.ui.components

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.presensikita.R
import com.example.presensikita.ui.header
import com.example.presensikita.ui.jadwal_kuliah.DaftarJadwalKuliahActivity
import kotlinx.coroutines.delay

class HomePageActivity : ComponentActivity() {
//    private val HomePageViewModel: HomePageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HomePage", "HomePage Activity Launched")
        setContent {
//            HomePagePreview()
            HomePage()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePage(modifier: Modifier = Modifier) {
    Log.d("HomePage", "HomePage is being composed")

    val context = LocalContext.current

    var isRefreshing by remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { isRefreshing = true }
    )

    Box(
        Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Log.d("HomePage", "Header is being composed")
            header()

            // Spacer dinamis untuk menggeser logo dan tombol ke tengah
            Spacer(modifier = Modifier.weight(1f))

            Log.d("HomePage", "Logo is being composed")
            // Logo di tengah
            Image(
                painter = painterResource(id = R.drawable.solutions__converted_), // Ganti dengan ID logo tengah
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 50.dp), // Tambahkan padding bawah untuk memberi jarak
                contentScale = ContentScale.Fit
            )

            // Spacer tambahan antara logo dan tombol
            Spacer(modifier = Modifier.height(24.dp)) // Tambahkan Spacer untuk memberi jarak lebih

            Log.d("HomePage", "Buttons are being composed")
            // Tombol-tombol utama
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HomeButton(text = "Daftar Kelas", onClick = {
                    val intent = Intent(context, ClassListActivity::class.java)
                    context.startActivity(intent)
                })
                Spacer(modifier = Modifier.height(12.dp))
                HomeButton(text = "Daftar Dosen", onClick = { /* Aksi tombol */ })
                Spacer(modifier = Modifier.height(12.dp))
                HomeButton(text = "Jadwal Perkuliahan", onClick = {
                    context.startActivity(Intent(context, DaftarJadwalKuliahActivity::class.java))
                })
            }

            // Spacer dinamis untuk memastikan tombol dan logo tetap di tengah
            Spacer(modifier = Modifier.weight(1f))
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
    // Simulasi refresh data
    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(2000) // Simulasi refresh selama 2 detik
            isRefreshing = false
        }
    }
}

@Composable
fun HomeButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A84C)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(275.dp)
            .height(50.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

// Preview untuk melihat tampilan HomePage
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePagePreview() {
    HomePage()
}
