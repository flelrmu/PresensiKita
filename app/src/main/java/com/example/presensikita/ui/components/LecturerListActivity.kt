package com.example.presensikita.ui.components

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.presensikita.R
import com.example.presensikita.ui.header
import com.example.presensikita.ui.viewModel.DosenViewModel

class LecturerListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LecturerListScreen()
        }
    }
}

@Composable
fun LecturerListScreen(viewModel: DosenViewModel = viewModel()) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var selectedLecturer by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    val lecturers by viewModel.dosens.collectAsState(initial = emptyList())
    val error by viewModel.error.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        viewModel.fetchDosens()
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

            header()

            Spacer(modifier = Modifier.height(50.dp))

            Image(
                painter = painterResource(id = R.drawable.leftchevron),
                contentDescription = "Chevron Icon",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 0.dp)
                    .size(33.dp, 31.dp)
                    .clickable {
                        val intent = Intent(context, HomePageActivity::class.java)
                        context.startActivity(intent)
                        (context as? ComponentActivity)?.finish()
                    }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Daftar Dosen",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2A2A2A),
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Tambah Dosen",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xFF2A2A2A)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(R.drawable.add),
                    contentDescription = "Add Lecturer",
                    modifier = Modifier
                        .size(29.dp, 28.dp)
                        .clickable {
                            context.startActivity(Intent(context, AddLecturerActivity::class.java))
                        }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // LazyColumn for Lecturer List
            if (error != null) {
                Text(
                    text = "Terjadi kesalahan: $error\nSilakan coba lagi nanti.",
                    color = Color.Red,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    items(lecturers) { lecturer ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${lecturer.nama}/${lecturer.nip}",
                                fontSize = 15.sp,
                                fontFamily = FontFamily.Serif,
                                color = Color(0xFF2A2A2A),
                                modifier = Modifier.weight(1f)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            IconButton(
                                onClick = {
                                    val intent = Intent(context, EditLecturerActivity::class.java).apply {
                                        putExtra("NIP", lecturer.nip)
                                        putExtra("NAMA_DOSEN", lecturer.nama)
                                        putExtra("EMAIL", lecturer.email)
                                        putExtra("TELEPON", lecturer.telepon)
                                    }
                                    context.startActivity(intent)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.edit),
                                    contentDescription = "Edit",
                                    modifier = Modifier
                                        .size(23.dp, 20.dp),
                                    tint = Color(0xFF00A844)
                                )
                            }
                            Spacer(modifier = Modifier.width(25.dp))

                            IconButton(
                                onClick = {
                                    selectedLecturer = lecturer.nip // Ambil nip saja
                                    showDialog = true
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.trash),
                                    contentDescription = "Delete",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

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
                                text = "Yakin untuk\nmenghapus Dosen?",
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
                                        if (selectedLecturer.isNotEmpty()) {
                                            viewModel.deleteDosen(selectedLecturer)
                                            snackbarMessage = "Dosen berhasil dihapus."
                                            showSnackbar = true
                                        } else {
                                            snackbarMessage = "NIP tidak valid."
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

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val intent = Intent(context, AbsenActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A844))
            ) {
                Text(text = "Laporan Kehadiran", color = Color.White)
            }
        }

        // Snackbar untuk notifikasi
        if (showSnackbar) {
            Snackbar(
                action = {
                    Text(
                        text = "Tutup",
                        color = Color.White,
                        modifier = Modifier.clickable { showSnackbar = false }
                    )
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(text = snackbarMessage, color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLecturerListScreen() {
    LecturerListScreen()
}
