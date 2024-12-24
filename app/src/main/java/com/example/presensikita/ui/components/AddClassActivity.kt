package com.example.presensikita.ui.components

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.presensikita.R
import com.example.presensikita.data.model.Class
import com.example.presensikita.data.model.Lecturer
import com.example.presensikita.ui.viewModel.ClassViewModel

class AddClassActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ClassViewModel = viewModel()
            AddClassScreen(viewModel) { isSuccess ->
                if (isSuccess) {
                    val intent = Intent(this, ClassListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddClassScreen(
    viewModel: ClassViewModel = viewModel(),
    onResult: (Boolean) -> Unit = {}
) {
    val context = LocalContext.current
    var namaKelas by remember { mutableStateOf("") }
    var kodeKelas by remember { mutableStateOf("") }
    var semester by remember { mutableStateOf("") }
    var jumlahSks by remember { mutableStateOf("") }
    var selectedLecturer by remember { mutableStateOf<Lecturer?>(null) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var showSnackbar by remember { mutableStateOf(false) }

    val lecturers by viewModel.lecturers.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchLecturers()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header Section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.solutions),
                    contentDescription = "Solutions Icon",
                    modifier = Modifier.size(144.dp, 30.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.notification),
                        contentDescription = "Notification Icon",
                        modifier = Modifier
                            .size(43.dp, 31.dp)
                            .padding(end = 8.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile Icon",
                        modifier = Modifier.size(43.dp, 31.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            // Back Navigation
            Image(
                painter = painterResource(id = R.drawable.leftchevron),
                contentDescription = "Chevron Icon",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 0.dp)
                    .size(33.dp, 31.dp)
                    .clickable {
                        val intent = Intent(context, ClassListActivity::class.java)
                        context.startActivity(intent)
                        (context as? ComponentActivity)?.finish()
                    }
            )

            Spacer(modifier = Modifier.height(30.dp))
            // Header Section
            Text(
                text = "Tambah Kelas",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2A2A2A)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Input Fields
            OutlinedTextField(
                value = namaKelas,
                onValueChange = { namaKelas = it },
                label = { Text("Nama Kelas") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 52.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
            )

            OutlinedTextField(
                value = kodeKelas,
                onValueChange = { kodeKelas = it },
                label = { Text("Kode Kelas") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 52.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
            )

            OutlinedTextField(
                value = jumlahSks,
                onValueChange = { jumlahSks = it },
                label = { Text("Jumlah SKS") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 52.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
            )

            OutlinedTextField(
                value = semester,
                onValueChange = { semester = it },
                label = { Text("Semester") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 52.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
            )

            // Lecturer Dropdown
            ExposedDropdownMenuBox(
                expanded = isDropdownExpanded,
                onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
            ) {
                OutlinedTextField(
                    value = selectedLecturer?.nama_dosen ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Dosen Pengampu") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(horizontal = 52.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    )
                )

                ExposedDropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    lecturers.forEach { lecturer ->
                        DropdownMenuItem(
                            text = { Text("${lecturer.nama_dosen} (${lecturer.nip})") },
                            onClick = {
                                selectedLecturer = lecturer
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val sksNumber = jumlahSks.toIntOrNull()
                    when {
                        namaKelas.trim().isEmpty() -> {
                            viewModel.setError("Nama kelas tidak boleh kosong")
                            return@Button
                        }
                        kodeKelas.trim().isEmpty() -> {
                            viewModel.setError("Kode kelas tidak boleh kosong")
                            return@Button
                        }
                        semester.trim().isEmpty() -> {
                            viewModel.setError("Semester tidak boleh kosong")
                            return@Button
                        }
                        sksNumber == null || sksNumber <= 0 -> {
                            viewModel.setError("Jumlah SKS harus berupa angka positif")
                            return@Button
                        }
                        selectedLecturer == null -> {
                            viewModel.setError("Dosen pengampu harus dipilih")
                            return@Button
                        }
                        else -> {
                            try {
                                val newClass = Class(
                                    kode_kelas = kodeKelas.trim(),
                                    nama_kelas = namaKelas.trim(),
                                    nip = selectedLecturer!!.nip,
                                    jumlah_sks = sksNumber,
                                    semester = semester.trim(),
                                )
                                viewModel.addClass(newClass)
                                showSnackbar = true
                                onResult(true)
                            } catch (e: Exception) {
                                viewModel.setError("Terjadi kesalahan saat menambah kelas: ${e.message}")
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A844))
            ) {
                Text("Tambah", color = Color.White)
            }
        }

        val snackbarMessage = when {
            error != null -> error
            showSnackbar -> "Kelas berhasil ditambahkan!"
            else -> null
        }

        snackbarMessage?.let {
            Snackbar(
                action = {
                    Text(
                        "Tutup",
                        color = Color.White,
                        modifier = Modifier.clickable {
                            showSnackbar = false
                            viewModel.setError(null)
                        }
                    )
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(it, color = Color.White)
            }
        }
    }
}
