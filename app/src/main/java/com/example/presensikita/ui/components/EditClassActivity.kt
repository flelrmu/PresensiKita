package com.example.presensikita.ui.components

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.presensikita.R
import com.example.presensikita.ui.viewModel.ClassViewModel

class EditClassActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val kode_kelas = intent.getStringExtra("KODE_KELAS") ?: ""
            val nama_kelas = intent.getStringExtra("NAMA_KELAS") ?: ""
            val nip = intent.getStringExtra("NIP") ?: ""
            val jumlah_sks = intent.getIntExtra("JUMLAH_SKS", 0)
            val semester = intent.getStringExtra("SEMESTER") ?: ""

            EditClassScreen(
                kodeKelas = kode_kelas,
                namaKelas = nama_kelas,
                nip = nip,
                jumlahSks = jumlah_sks,
                semester = semester,
                context = this
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditClassScreen(
    kodeKelas: String,
    namaKelas: String,
    nip: String,
    jumlahSks: Int,
    semester: String,
    context: Context,
    viewModel: ClassViewModel = viewModel()
) {
    var updatedClassName by remember { mutableStateOf(namaKelas) }
    var updatedClassCode by remember { mutableStateOf(kodeKelas) }
    var selectedNip by remember { mutableStateOf(nip) }
    var updatedJumlahSKS by remember { mutableStateOf(jumlahSks) }
    var updatedSemester by remember { mutableStateOf(semester) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val lecturers by viewModel.lecturers.collectAsState()

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
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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

            Text(
                text = "Edit Kelas",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2A2A2A)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Input Fields
            OutlinedTextField(
                value = updatedClassName,
                onValueChange = { updatedClassName = it },
                label = { Text(text = "Nama Kelas") },
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
                value = updatedClassCode,
                onValueChange = { updatedClassCode = it },
                label = { Text(text = "Kode Kelas") },
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
                value = updatedJumlahSKS.toString(),
                onValueChange = {
                    updatedJumlahSKS = it.toIntOrNull() ?: 0
                },
                label = { Text(text = "Jumlah SKS") },
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
                value = updatedSemester,
                onValueChange = { updatedSemester = it },
                label = { Text(text = "Semester") },
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

            ExposedDropdownMenuBox(
                expanded = isDropdownExpanded,
                onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
            ) {
                OutlinedTextField(
                    value = lecturers.find { it.nip == selectedNip }?.nama_dosen ?: "",
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
                            text = { Text(lecturer.nama_dosen) },
                            onClick = {
                                selectedNip = lecturer.nip
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.updateClass(
                        kodeKelas = updatedClassCode,
                        namaKelas = updatedClassName,
                        nip = selectedNip,
                        jumlahSks = updatedJumlahSKS,
                        semester = updatedSemester
                    )
                    Toast.makeText(context, "Kelas berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, ClassListActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A844))
            ) {
                Text(text = "Simpan", color = Color.White)
            }
        }
    }
}
