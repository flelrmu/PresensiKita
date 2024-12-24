package com.example.presensikita.ui.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.presensikita.R
import com.example.presensikita.ui.viewModel.ClassViewModel

class AbsenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AbsenScreen()
        }
    }
}

@Composable
fun AbsenScreen(viewModel: ClassViewModel = viewModel()) {
    val classes by viewModel.classes.collectAsState(initial = emptyList())
    val presensi by viewModel.presensi.collectAsState(initial = emptyList())
    val error by viewModel.error.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        viewModel.fetchClasses()
        viewModel.fetchPresensi()
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
            // Header section
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

            // Back button and title section
            Image(
                painter = painterResource(id = R.drawable.leftchevron),
                contentDescription = "Chevron Icon",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 10.dp)
                    .size(33.dp, 31.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Laporan Pertemuan",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2A2A2A)
            )

            Spacer(modifier = Modifier.height(70.dp))

            if (error != null) {
                Text(
                    text = "Terjadi kesalahan: $error\nSilakan coba lagi nanti.",
                    color = Color.Red,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                // LazyColumn for dynamic data
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    item {
                        // Header row for the table
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Kelas", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                            Text("Dosen", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                            Text("Total Pertemuan", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        }
                    }

                    items(classes) { classItem ->
                        val totalPertemuan = presensi.filter { it.presensi_id == classItem.kode_kelas.toInt() }.sumOf { it.pertemuan }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(classItem.nama_kelas, modifier = Modifier.weight(1f))
                            Text(classItem.nip, modifier = Modifier.weight(1f))
                            Text(totalPertemuan.toString(), modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDownloadReportScreen() {
    AbsenScreen()
}
