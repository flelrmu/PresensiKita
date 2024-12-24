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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
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
import com.example.presensikita.R
import com.example.presensikita.ui.header

class LecturerListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LecturerListScreen()
        }
    }
}

@Composable
fun LecturerListScreen() {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var selectedLecturer by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    val lecturers = listOf(
        Lecturer(name = "Affiyardi Dwi Kartika, MT", email = "Dwi@gmail.com", nip = "12121212125452"),
        Lecturer(name = "Hasdi Putra, MT", email = "Hasdi@gmail.com", nip = "385763976749")
    )

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
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Daftar Dosen",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2A2A2A)
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
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = lecturer.name,
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Serif,
                                color = Color(0xFF2A2A2A)
                            )
                            Text(
                                text = lecturer.email,
                                fontSize = 14.sp,
                                color = Color(0xFF6C6C6C)
                            )
                            Text(
                                text = lecturer.nip,
                                fontSize = 14.sp,
                                color = Color(0xFF6C6C6C)
                            )
                        }

                        Image(
                            painter = painterResource(R.drawable.edit),
                            contentDescription = "Edit Icon",
                            modifier = Modifier.size(23.dp, 20.dp)
                        )
                        Spacer(modifier = Modifier.width(25.dp))
                        Image(
                            painter = painterResource(R.drawable.trash),
                            contentDescription = "Trash Icon",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    selectedLecturer = lecturer.name
                                    showDialog = true
                                }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (showDialog) {
                Dialog(onDismissRequest = { showDialog = false }) {
                    Box(
                        modifier = Modifier
                            .width(300.dp)
                            .height(200.dp)
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
                                        snackbarMessage = "$selectedLecturer berhasil dihapus!"
                                        showSnackbar = true
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
                onClick = { /* Handle Download */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A844))
            ) {
                Text(text = "Download Absensi", color = Color.White)
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

data class Lecturer(
    val name: String,
    val email: String,
    val nip: String
)