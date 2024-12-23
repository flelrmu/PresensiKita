package com.example.presensikita.ui.jadwal_kuliah

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presensikita.R
import com.example.presensikita.configs.RetrofitClient.jadwalService
import com.example.presensikita.data.JadwalKuliah
import com.example.presensikita.data.api.JadwalService
import com.example.presensikita.ui.components.HomePageActivity
import com.example.presensikita.ui.header
import com.example.presensikita.ui.theme.PresensiKitaTheme
import com.example.presensikita.ui.viewModel.JadwalUiModel
import com.example.presensikita.ui.viewModel.JadwalUiState
import com.example.presensikita.ui.viewModel.JadwalViewModel
import com.example.presensikita.ui.viewModel.JadwalViewModelFactory

class DaftarJadwalKuliahActivity : ComponentActivity() {

    private val viewModel: JadwalViewModel by viewModels {
        JadwalViewModelFactory(jadwalService, applicationContext)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PresensiKitaTheme {
                ScheduleTableScreen(viewModel)
            }
        }
    }
}
@Composable
fun ScheduleTableScreen(
    viewModel: JadwalViewModel
) {
    val scrollState = rememberScrollState()
    // Mendapatkan konteks saat ini
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()

//    val jadwalList by viewModel.jadwalState.collectAsState()

    // Tambahkan logging
    LaunchedEffect(uiState) {
        Log.d("ScheduleTableScreen", "Current UI State: $uiState")
    }

    LaunchedEffect(Unit) {
        viewModel.loadJadwal()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
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
                    context.startActivity(Intent(context, HomePageActivity::class.java))
                }
        )

        Spacer(modifier = Modifier.height(30.dp))


        // Judul Tabel
        Text(
            text = "Daftar Jadwal Kuliah",
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
                modifier = Modifier
                    .size(29.dp, 28.dp)
                    .clickable {
                        context.startActivity(Intent(context, TambahJadwalActivity::class.java))
                    }
            )
        }

//        ScheduleTable(schedules = jadwalList)

        when (uiState) {
            is JadwalUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Loading...")
                }
            }
            is JadwalUiState.Success -> {
                val data = (uiState as JadwalUiState.Success).data
                Log.d("ScheduleTableScreen", "Showing data: $data")
                ScheduleTable(data)
            }
            is JadwalUiState.Error -> {
                val error = (uiState as JadwalUiState.Error).message
                Log.e("ScheduleTableScreen", "Error: $error")
                Text(
                    text = error,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ScheduleTable(
//    schedules: List<JadwalKuliah>
    jadwalList: List<JadwalUiModel>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color.LightGray)
                .padding(vertical = 1.dp)
                .background(Color.LightGray)
        ) {
            TableHeader(
                text = "Kode",
                modifier = Modifier
                    .weight(0.9f)
                    .align(Alignment.CenterVertically)
            )
            TableHeader(
                text = "Kelas",
                modifier = Modifier
                    .weight(1.2f)
                    .align(Alignment.CenterVertically)
            )
            TableHeader(
                text = "Hari",
                modifier = Modifier
                    .weight(0.9f)
                    .align(Alignment.CenterVertically)
            )
            TableHeader(
                text = "Jam Mulai",
                modifier = Modifier
                    .weight(0.9f)
                    .align(Alignment.CenterVertically)
            )
            TableHeader(
                text = "Ruang",
                modifier = Modifier
                    .weight(0.9f)
                    .align(Alignment.CenterVertically)
            )
            Spacer(Modifier.width(70.dp)) // Memperbesar space untuk action buttons
        }

        jadwalList.forEach { schedule ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .border(0.4.dp, Color.LightGray),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TableCell(
                    text = schedule.kode,
                    modifier = Modifier.weight(0.9f)
                )
                TableCell(
                    text = schedule.nama,
                    modifier = Modifier.weight(1.2f)
                )
                TableCell(
                    text = schedule.hari,
                    modifier = Modifier.weight(0.9f)
                )
                TableCell(
                    text = schedule.jamMulai,
                    modifier = Modifier.weight(1f)
                )
                TableCell(
                    text = schedule.ruang,
                    modifier = Modifier.weight(0.9f)
                )
                Column(
                    modifier = Modifier
                        .width(70.dp)  // Fixed width untuk area action buttons
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.edit),
                        contentDescription = "Edit Icon",
                        modifier = Modifier
                            .size(23.dp, 20.dp)
                            .clickable { }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(R.drawable.trash),
                        contentDescription = "Trash Icon",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { }
                    )
                }
            }
        }
    }
}

@Composable
fun TableHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth()
    )
}


@Composable
fun TableCell(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 12.sp,
        modifier = modifier
            .padding(horizontal = 5.dp, vertical = 12.dp)
            .fillMaxWidth()
    )
}

// Fungsi untuk data dummy
fun dummySchedules(): List<JadwalKuliah> {
    return listOf(
        JadwalKuliah("if01","Kalkulus", "Kamis", "13.30", "Ruang 101"),
        JadwalKuliah("if01","Akuisisi Data", "Rabu", "09.20", "Ruang 203"),
        JadwalKuliah("if01","Pemrograman", "Selasa", "10.00", "Ruang 105"),
        JadwalKuliah("if01","Basis Data", "Jumat", "08.00", "Ruang 102")
    )
}

//@Preview(showBackground = true)
//@Composable
//fun Preview() {
//    PresensiKitaTheme {
//        ScheduleTableScreen(
//            jadwalList = dummySchedules()
//        )
//    }
//}

fun finish() {
    TODO("Not yet implemented")
}