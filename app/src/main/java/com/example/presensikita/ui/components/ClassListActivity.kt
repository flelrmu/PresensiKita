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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.presensikita.R
import com.example.presensikita.ui.header
import com.example.presensikita.ui.viewModel.ClassViewModel

class ClassListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClassListScreen()
        }
    }
}

@Composable
fun ClassListScreen(viewModel: ClassViewModel = viewModel()) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var selectedClass by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    val classes by viewModel.classes.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchClasses()
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
//            // Header
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 0.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.solutions),
//                    contentDescription = "Solutions Icon",
//                    modifier = Modifier.size(144.dp, 30.dp)
//                )
//
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Image(
//                        painter = painterResource(id = R.drawable.notification),
//                        contentDescription = "Notification Icon",
//                        modifier = Modifier.size(43.dp, 31.dp)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Image(
//                        painter = painterResource(id = R.drawable.profile),
//                        contentDescription = "Profile Icon",
//                        modifier = Modifier.size(43.dp, 31.dp)
//                    )
//                }
//            }

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
                text = "Daftar Kelas",
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
                    text = "Tambah Kelas",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xFF2A2A2A)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(R.drawable.add),
                    contentDescription = "Add Class",
                    modifier = Modifier
                        .size(29.dp, 28.dp)
                        .clickable {
                            context.startActivity(Intent(context, AddClassActivity::class.java))
                        }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // LazyColumn for Class List
            if (error != null) {
                Text(
                    text = "Error: $error",
                    color = Color.Red,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    items(classes) { classItem ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${classItem.class_name}/${classItem.class_code}",
                                fontSize = 15.sp,
                                fontFamily = FontFamily.Serif,
                                color = Color(0xFF2A2A2A),
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.weight(1f))
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
                                        selectedClass = "${classItem.class_name}/${classItem.class_code}"
                                        showDialog = true
                                    }
                            )
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
                                text = "Yakin untuk\nmenghapus Kelas?",
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
                                        val classId = classes.firstOrNull {
                                            "${it.class_name}/${it.class_code}" == selectedClass
                                        }?.id
                                        if (classId != null) {
                                            viewModel.deleteClass(classId)
                                            snackbarMessage = "Kelas berhasil dihapus!"
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
                onClick = { /* Handle Laporan Kehadiran */ },
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
fun PreviewClassListScreen() {
    ClassListScreen()
}
