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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.presensikita.R
import com.example.presensikita.configs.RetrofitClient.jadwalService
//import com.example.presensikita.data.JadwalKuliah
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
        Log.d("DaftarJadwalKuliah", "Activity created")

        setContent {
            PresensiKitaTheme {
                ScheduleTableScreen(viewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("DaftarJadwalKuliah", "Activity resumed, refreshing data")
        viewModel.loadJadwal() // Refresh data saat activity resume
    }
}
@Composable
fun ScheduleTableScreen(
    viewModel: JadwalViewModel
) {
    val scrollState = rememberScrollState()
    // Mendapatkan konteks saat ini
    val context = LocalContext.current

    val uiState by viewModel.uiState.observeAsState()
    val deleteStatus by viewModel.deleteStatus.observeAsState()

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
                ScheduleTable(
                    jadwalList = data,
                    viewModel = viewModel
                )
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
            else -> {
                // Handle null state
                Text(text = "Tidak ada data")
            }
        }

    }
}

@Composable
fun ScheduleTable(
    jadwalList: List<JadwalUiModel>,
    viewModel: JadwalViewModel
) {
    val context = LocalContext.current
    var selectedJadwal by remember { mutableStateOf(-1) }
    var showDialog by remember { mutableStateOf(false) }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

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
                    text = schedule.kode_kelas,
                    modifier = Modifier.weight(0.9f)
                )
                TableCell(
                    text = schedule.nama_kelas,
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
                            .clickable {
                                val intent = Intent(context, EditJadwalActivity::class.java).apply {
                                    putExtra("jadwal_id", schedule.kode_jadwal)
                                    putExtra("kelas", schedule.kode_kelas + "/" + schedule.nama_kelas)
                                    putExtra("hari", schedule.hari)
                                    putExtra("jam_mulai", schedule.jamMulai)
                                    putExtra("ruang", schedule.ruang)
                                }
                                context.startActivity(intent)
                            }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(R.drawable.trash),
                        contentDescription = "Trash Icon",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                selectedJadwal = schedule.kode_jadwal
                                showDialog = true
                            }
                    )
                }
            }
        }

        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(170.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Yakin untuk\nmenghapus Jadwal Kuliah?",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = {
                                    showDialog = false
                                    if (selectedJadwal != -1) {
                                        viewModel.deleteJadwal(selectedJadwal)
                                        snackbarMessage = "Kelas berhasil dihapus."
                                        showSnackbar = true
                                    } else {
                                        snackbarMessage = "Kode kelas tidak valid."
                                        showSnackbar = true
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A844))
                            ) {
                                Text(text = "Continue", color = Color.White)
                            }

                            Button(
                                onClick = { showDialog = false },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) {
                                Text(text = "Cancel", color = Color.White)
                            }
                        }
                    }
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