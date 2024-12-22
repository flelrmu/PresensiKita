package com.example.presensikita.ui.components

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.presensikita.R
import com.example.presensikita.ui.header

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

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    Log.d("HomePage", "HomePage is being composed")
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
            HomeButton(text = "Daftar Kelas", onClick = { /* Aksi tombol */ })
            Spacer(modifier = Modifier.height(12.dp))
            HomeButton(text = "Daftar Dosen", onClick = { /* Aksi tombol */ })
            Spacer(modifier = Modifier.height(12.dp))
            HomeButton(text = "Jadwal Perkuliahan", onClick = { /* Aksi tombol */ })
        }

        // Spacer dinamis untuk memastikan tombol dan logo tetap di tengah
        Spacer(modifier = Modifier.weight(1f))
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
