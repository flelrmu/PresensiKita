package com.example.presensikita.ui.components

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presensikita.R

class HomePageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomePage()
        }
    }
}

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Bagian atas (Header dengan logo dan ikon)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo aplikasi
            Image(
                painter = painterResource(id = R.drawable.solutions), // Ganti dengan ID logo
                contentDescription = "Solutions Icon",
                modifier = Modifier.size(144.dp, 30.dp) // Sesuaikan ukuran logo
            )

            // Icon notifikasi dan profil
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.notification), // Ganti dengan ID notifikasi
                    contentDescription = "Notification Icon",
                    modifier = Modifier
                        .size(43.dp, 31.dp)
                        .padding(end = 8.dp) // Jarak antar ikon
                )
                Image(
                    painter = painterResource(id = R.drawable.profile), // Ganti dengan ID profil
                    contentDescription = "Profile Icon",
                    modifier = Modifier.size(43.dp, 31.dp)
                )
            }
        }

        // Spacer dinamis untuk menggeser logo dan tombol ke tengah
        Spacer(modifier = Modifier.weight(1f))

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
