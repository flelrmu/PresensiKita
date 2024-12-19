package com.example.presensikita.ui.jadwal_kuliah

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presensikita.R
import com.example.presensikita.data.JadwalKuliah
import com.example.presensikita.dummySchedules
import com.example.presensikita.ui.theme.PresensiKitaTheme

@Composable
fun ScheduleTableScreen(
    schedules: List<JadwalKuliah>,
    onBack: () -> Unit,
//    onProfileClick: () -> Unit,
//    onAddJadwal: () -> Unit,
//    onEditJadwal: () -> Unit,
//    kembali: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
                IconButton(onClick = { /* Handle notification */ }) {
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
                .clickable { onBack() }
        )

        // Judul Tabel
        Text(
            text = "Jadwal Kuliah",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black,
            modifier = Modifier.padding(start = 12.dp, bottom = 32.dp)
        )

        // Add Class Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
//                .clickable { onAddJadwal() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            androidx.compose.material.Text(
                text = "Tambah Jadwal",
                fontSize = 12.sp,
                fontFamily = FontFamily.Serif,
                color = Color(0xFF2A2A2A)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.add),
                contentDescription = "Add Class",
                modifier = Modifier.size(29.dp, 28.dp)
            )
        }

        ScheduleTable(
            schedules = listOf(
                JadwalKuliah("Kalkulus", "Kamis", "13.30 - 15.00", "Ruang A"),
                JadwalKuliah("Akuisisi Data", "Rabu", "9.20 - 11.00", "Ruang B"),
                JadwalKuliah("Sistem Basis Data", "Senin", "10.00 - 12.00", "Ruang C")
            )
//            onEditJadwal
        )
    }
}

@Composable
fun ScheduleTable(
    schedules: List<JadwalKuliah>
//    onEditJadwal: () -> Unit
) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            TableHeader("Kelas")
            TableHeader("Hari")
            TableHeader("Jam")
            TableHeader("Ruang")
            Spacer(Modifier.width(16.dp))
        }

        schedules.forEach { schedule ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TableCell(schedule.kelas)
                TableCell(schedule.hari)
                TableCell(schedule.jam)
                TableCell(schedule.ruang)
                Spacer(modifier = Modifier.width(12.dp))
                IconButton(onClick = { /* onEditJadwal() */ }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color(0xFF00AF4F),
                        modifier = Modifier
                            .size(18.dp)
                    )
                }
                IconButton(onClick = { /* TODO: Delete */ }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TableHeader(text: String, widthFraction: Float = 0.25f) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth(widthFraction)
            .padding(horizontal = 1.dp),
        fontSize = 11.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}


@Composable
fun TableCell(text: String, widthFraction: Float = 0.25f) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth(widthFraction)
            .padding(horizontal = 1.dp),
        fontSize = 11.sp,
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    PresensiKitaTheme {
        ScheduleTableScreen(
            schedules = dummySchedules(),
            onBack = { finish() } // Aksi untuk tombol kembali
        )
    }
}

fun finish() {
    TODO("Not yet implemented")
}