package com.example.presensikita.ui.jadwal_kuliah

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presensikita.R
import com.example.presensikita.data.JadwalKuliah
import com.example.presensikita.data.UserProfile
import com.example.presensikita.dummySchedules
import com.example.presensikita.ui.theme.PresensiKitaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditJadwalScreen(
    onSaveClick: (JadwalKuliah) -> Unit,
    initialJadwal: JadwalKuliah = JadwalKuliah("", "", "", ""),
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    var jadwal by remember { mutableStateOf(initialJadwal) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ABF ",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(0xFF00AF4F)
                )
                Text(
                    text = "Solutions",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Row {
                IconButton(onClick = { /* Handle notification */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.notification),
                        contentDescription = "Notifications",
                        tint = Color(0xFF00AF4F),
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
                IconButton(onClick = { /* Handle profile */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile",
                        tint = Color(0xFF00AF4F),
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
            }
        }

        // Chevron Icon
        Image(
            painter = painterResource(id = R.drawable.leftchevron),
            contentDescription = "Kembali",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 10.dp, top = 40.dp, bottom = 24.dp)
                .size(24.dp, 24.dp)
        )

        // Judul Tabel
        Text(
            text = "Perbarui Jadwal",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black,
            modifier = Modifier.padding(start = 12.dp, bottom = 12.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

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


        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { onSaveClick(jadwal) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A844))
            ) {
                Text(text = "Simpan", color = Color.White)
            }
        }

    }
}

//@Preview(showBackground = true)
//@Composable
//fun Preview() {
//    PresensiKitaTheme {
//        EditJadwalScreen(
//            initialJadwal = JadwalKuliah("", "", "", ""),
//            onSaveClick = { simpan() },
//            onBack = { finish() }
//        )
//    }
//}
//
//fun simpan() {
//    TODO("Not yet implemented")
//}
//
//fun finish() {
//    TODO("Not yet implemented")
//}