package com.example.presensikita.ui.jadwal_kuliah

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberTimePickerState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presensikita.R
import com.example.presensikita.configs.RetrofitClient
import com.example.presensikita.configs.RetrofitClient.jadwalService
import com.example.presensikita.data.model.RuanganItem
import com.example.presensikita.ui.header
import com.example.presensikita.ui.theme.PresensiKitaTheme
import com.example.presensikita.ui.viewModel.AddJadwalViewModel
import com.example.presensikita.ui.viewModel.AddJadwalViewModelFactory
import com.example.presensikita.ui.viewModel.JadwalViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TambahJadwalActivity : ComponentActivity() {
    private val viewModel: AddJadwalViewModel by viewModels {
        AddJadwalViewModelFactory(jadwalService, RetrofitClient.getApiService(), applicationContext)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TambahJadwalActivity", "Activity created")

        setContent {
            PresensiKitaTheme {
                TambahJadwalScreen(viewModel = viewModel)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahJadwalScreen(
    viewModel: AddJadwalViewModel
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val classes by viewModel.classes.collectAsState(initial = emptyList())
    val ruangan by viewModel.ruangan.observeAsState(emptyList())
    val navigateToList by viewModel.navigateToList.observeAsState(initial = false)
    val toastMessage by viewModel.toastMessage.observeAsState()

    var selectedKelas by remember { mutableStateOf("") }
    var expandedKelas by remember { mutableStateOf(false) }

    var selectedHari by remember { mutableStateOf("") }
    var expandedHari by remember { mutableStateOf(false) }

    var selectedRuang by remember { mutableStateOf<RuanganItem?>(null) }
    var expandedRuang by remember { mutableStateOf(false) }

    var selectedTime by remember { mutableStateOf("") }
    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()

    val hari = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat")

    // Handle navigation
    LaunchedEffect(navigateToList) {
        if (navigateToList) {
            Log.d("TambahJadwalScreen", "Navigating back to list")
            (context as Activity).finish()
            viewModel.doneNavigating()
        }
    }

    // Handle toast messages
    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Log.d("TambahJadwalScreen", "Showing toast: $it")
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
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
                .fillMaxWidth(),
//                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            // Dropdown Kelas
            ExposedDropdownMenuBox(
                expanded = expandedKelas,
                onExpandedChange = { expandedKelas = !expandedKelas },
                modifier = Modifier
                    .fillMaxWidth()
//                    .padding(horizontal = 52.dp, vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = selectedKelas,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Pilih Kelas") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedKelas) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .menuAnchor()
                        .padding(horizontal = 52.dp, vertical = 8.dp)
                )

                ExposedDropdownMenu(
                    expanded = expandedKelas,
                    onDismissRequest = { expandedKelas = false }
                ) {
                    classes.forEach { kelas ->
                        DropdownMenuItem(
                            text = { Text("${kelas.kode_kelas} / ${kelas.nama_kelas}") },
                            onClick = {
                                selectedKelas = kelas.kode_kelas
                                expandedKelas = false
                                Log.d("TambahJadwalScreen", "Selected kelas: ${kelas.kode_kelas}")
                            }
                        )
                    }
                }
            }


            // Dropdown Hari
            ExposedDropdownMenuBox(
                expanded = expandedHari,
                onExpandedChange = { expandedHari = !expandedHari },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 52.dp, vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = selectedHari,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Pilih Hari") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedHari) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expandedHari,
                    onDismissRequest = { expandedHari = false }
                ) {
                    hari.forEach { hari ->
                        DropdownMenuItem(
                            text = { Text(hari) },
                            onClick = {
                                selectedHari = hari
                                expandedHari = false
                            }
                        )
                    }
                }
            }

            // Jam Mulai (Time Picker)
            OutlinedTextField(
                value = selectedTime,
                onValueChange = { },
                label = { Text("Pilih Jam Mulai") },
                singleLine = true,
                readOnly = true,
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = "Select time",
                        modifier = Modifier
                            .clickable { showTimePicker = true }
                            .size(24.dp)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 52.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
            )

            // Dropdown Ruangan
            ExposedDropdownMenuBox(
                expanded = expandedRuang,
                onExpandedChange = { expandedRuang = !expandedRuang },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 52.dp, vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = selectedRuang?.nama_ruangan ?: "",
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Pilih Ruangan") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRuang) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expandedRuang,
                    onDismissRequest = { expandedRuang = false }
                ) {
                    ruangan.forEach { ruang ->
                        DropdownMenuItem(
                            text = { Text(ruang.nama_ruangan) },
                            onClick = {
                                selectedRuang = ruang
                                expandedRuang = false
                            }
                        )
                    }
                }
            }
        }


        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    val jamFormatted = "${timePickerState.hour}:${timePickerState.minute}"
                    Log.d("TambahJadwalScreen", "Attempting to add jadwal: $selectedKelas, ${selectedRuang?.ruang_id}, $selectedHari, $selectedTime")
                    viewModel.tambahJadwal(
                        kodeKelas = selectedKelas,
                        ruanganId = selectedRuang?.ruang_id ?: 0,
                        hari = selectedHari,
                        jamMulai = selectedTime
                    )
                },
                enabled = selectedKelas.isNotEmpty() && selectedRuang != null &&
                        selectedHari.isNotEmpty() && selectedTime.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A844))
            ) {
                Text(text = "Tambah", color = Color.White)
            }
        }

        if (showTimePicker) {
            TimePickerDialog(
                onDismissRequest = { showTimePicker = false },
                onConfirm = {
                    val hour = timePickerState.hour.toString().padStart(2, '0')
                    val minute = timePickerState.minute.toString().padStart(2, '0')
                    selectedTime = "$hour:$minute"
                    showTimePicker = false
                },
                timePickerState = timePickerState
            )
        }

    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewTambahJadwalScreen() {
//    PresensiKitaTheme {
//        TambahJadwalScreen(
////            onAddClick = {},
////            onBack = {}
//        )
//    }
//}