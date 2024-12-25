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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.window.Dialog
import com.example.presensikita.R
import com.example.presensikita.ui.header
import com.example.presensikita.ui.theme.PresensiKitaTheme
import com.example.presensikita.ui.viewModel.EditJadwalUiState
import com.example.presensikita.ui.viewModel.EditJadwalViewModel
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.livedata.observeAsState
import com.example.presensikita.configs.RetrofitClient
import com.example.presensikita.data.api.JadwalService
import com.example.presensikita.ui.viewModel.EditJadwalViewModelFactory
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class EditJadwalActivity : ComponentActivity() {

    private val viewModel: EditJadwalViewModel by viewModels {
        EditJadwalViewModelFactory(RetrofitClient.jadwalService, this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jadwalId = intent.getIntExtra("jadwal_id", -1)
        val kelas = intent.getStringExtra("kelas") ?: ""
        val hari = intent.getStringExtra("hari") ?: ""
        val jamMulai = intent.getStringExtra("jam_mulai") ?: ""
        val ruang = intent.getStringExtra("ruang") ?: ""

        setContent {
            PresensiKitaTheme {
                EditJadwalScreen(
                    viewModel = viewModel,
                    jadwalId = jadwalId,
                    initialKelas = kelas,
                    initialHari = hari,
                    initialJam = jamMulai,
                    initialRuang = ruang
//                    onNavigateBack = { finish() }
                )
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditJadwalScreen(
    viewModel: EditJadwalViewModel,
    jadwalId: Int,
    initialKelas: String,
    initialHari: String,
    initialJam: String,
    initialRuang: String,
) {
    val uiState by viewModel.uiState.observeAsState()
    val ruanganList by viewModel.ruangan.observeAsState(emptyList())
    var selectedHari by remember { mutableStateOf(initialHari) }
    var selectedJam by remember { mutableStateOf(initialJam) }
    var selectedRuang by remember { mutableStateOf(initialRuang) }
    var showTimePicker by remember { mutableStateOf(false) }

    // Tambahkan state untuk mengontrol expanded state
    var expandedHari by remember { mutableStateOf(false) }
    var expandedRuang by remember { mutableStateOf(false) }

    val hariList = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat")

    // Convert initial time string to hour and minute
    val initialTime = try {
        LocalTime.parse(initialJam, DateTimeFormatter.ofPattern("HH:mm"))
    } catch (e: Exception) {
        LocalTime.of(8, 0) // Default time if parsing fails
    }

    val timePickerState = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute
    )

    val scrollState = rememberScrollState()
    val context = LocalContext.current

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
            text = "Perbarui Jadwal",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black,
            modifier = Modifier.padding(start = 12.dp, bottom = 12.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Kelas (Disabled)
        OutlinedTextField(
            value = initialKelas,
            onValueChange = { },
            label = { Text("Kelas") },
            enabled = false,
//            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 52.dp, vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
        )

        // Hari (Dropdown)
        ExposedDropdownMenuBox(
            expanded = expandedHari,
            onExpandedChange = { expandedHari = it }  // Toggle expanded state
        ) {
            OutlinedTextField(
                value = selectedHari,
                onValueChange = { },
                readOnly = true,
                label = { Text("Hari") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedHari) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .padding(horizontal = 52.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
            )

            ExposedDropdownMenu(
                expanded = expandedHari,
                onDismissRequest = { expandedHari = false }  // Tutup dropdown saat klik di luar
            ) {
                hariList.forEach { hari ->
                    DropdownMenuItem(
                        text = { Text(hari) },
                        onClick = {
                            selectedHari = hari
                            expandedHari = false  // Tutup dropdown setelah memilih
                        }
                    )
                }
            }
        }

        // Jam Mulai (Time Picker)
        OutlinedTextField(
            value = selectedJam,
            onValueChange = { },
            label = { Text("Jam Mulai") },
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

        // Ruangan (Dropdown)
        ExposedDropdownMenuBox(
            expanded = expandedRuang,
            onExpandedChange = { expandedRuang = it }  // Toggle expanded state
        ) {
            OutlinedTextField(
                value = selectedRuang,
                onValueChange = { },
                readOnly = true,
                label = { Text("Ruangan") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRuang) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .padding(horizontal = 52.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
            )

            ExposedDropdownMenu(
                expanded = expandedRuang,
                onDismissRequest = { expandedRuang = false }  // Tutup dropdown saat klik di luar
            ) {
                ruanganList.forEach { ruangan ->
                    DropdownMenuItem(
                        text = { Text(ruangan.nama_ruangan) },
                        onClick = {
                            selectedRuang = ruangan.nama_ruangan
                            expandedRuang = false  // Tutup dropdown setelah memilih
                        }
                    )
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
                    val ruanganId = ruanganList.find { it.nama_ruangan == selectedRuang }?.ruang_id
                    if (ruanganId != null) {
                        viewModel.updateJadwal(jadwalId, ruanganId, selectedHari, selectedJam)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A844))
            ) {
                Text(text = "Simpan", color = Color.White)
            }
        }

        if (showTimePicker) {
            TimePickerDialog(
                onDismissRequest = { showTimePicker = false },
                onConfirm = {
                    selectedJam = String.format("%02d:%02d", timePickerState.hour, timePickerState.minute)
                    showTimePicker = false
                },
                timePickerState = timePickerState
            )
        }

        // State handling
        when (uiState) {
            is EditJadwalUiState.Loading -> {
                Log.d("EditJadwalScreen", "Menampilkan loading state")
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is EditJadwalUiState.Success -> {
                Log.d("EditJadwalScreen", "Edit berhasil, kembali ke halaman daftar")
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "Berhasil memperbarui jadwal", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, DaftarJadwalKuliahActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                    (context as? Activity)?.finish()
                }
            }
            is EditJadwalUiState.Error -> {
                val errorMessage = (uiState as? EditJadwalUiState.Error)?.message
                Log.e("EditJadwalScreen", "Error: $errorMessage")
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    timePickerState: TimePickerState
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Batal")
            }
        },
        text = {
            TimePicker(state = timePickerState)
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun EditJadwalScreenPreview() {
//    EditJadwalScreen(
//        viewModel = EditJadwalViewModel,
//        jadwalId = 1,
//        initialKelas = "Kelas 1",
//        initialHari = "Senin",
//        initialJam = "08:00",
//        initialRuang = "Ruang 1"
//    )
//}