package com.example.presensikita.ui.jadwal_kuliah

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presensikita.R
import com.example.presensikita.data.JadwalKuliah
import com.example.presensikita.data.UserProfile
import com.example.presensikita.ui.components.HomePageActivity
import com.example.presensikita.ui.header
import com.example.presensikita.ui.theme.PresensiKitaTheme

class TambahJadwalActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PresensiKitaTheme {
                TambahJadwalScreen(
//                    onAddClick = { jadwal ->
//                        // Handle adding jadwal
//                    },
//                    onBack = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahJadwalScreen(
//    onAddClick: (JadwalKuliah) -> Unit,
//    onBack: () -> Unit
) {
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    var jadwal by remember { mutableStateOf(JadwalKuliah("","", "", "", "")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        header()

        // Chevron Icon

        Spacer(modifier = Modifier.height(50.dp))

        Image(
            painter = painterResource(id = R.drawable.leftchevron),
            contentDescription = "Chevron Icon",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 0.dp)
                .size(33.dp, 31.dp)
                .clickable {
//                    context.startActivity(Intent(context, DaftarJadwalKuliahActivity::class.java))
                    (context as Activity).finish()
                }
        )

        Spacer(modifier = Modifier.height(30.dp))


        // Judul Tabel
        Text(
            text = "Tambah Jadwal",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black,
            modifier = Modifier.padding(start = 12.dp, bottom = 32.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = jadwal.kelas,
                onValueChange = { jadwal = jadwal.copy(kelas = it) },
                label = { Text("Kelas") },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 52.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
            )

            OutlinedTextField(
                value = jadwal.hari,
                onValueChange = { jadwal = jadwal.copy(hari = it) },
                label = { Text("Hari") },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 52.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
            )

            OutlinedTextField(
                value = jadwal.jam,
                onValueChange = { jadwal = jadwal.copy(jam = it) },
                label = { Text("Jam") },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 52.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
            )

            OutlinedTextField(
                value = jadwal.ruang,
                onValueChange = { jadwal = jadwal.copy(ruang = it) },
                label = { Text("Ruang") },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 52.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
            )
        }



        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A844))
            ) {
                Text(text = "Tambah", color = Color.White)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTambahJadwalScreen() {
    PresensiKitaTheme {
        TambahJadwalScreen(
//            onAddClick = {},
//            onBack = {}
        )
    }
}