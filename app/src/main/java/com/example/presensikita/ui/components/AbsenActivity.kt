package com.example.presensikita.ui.components

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    val pertemuan by viewModel.pertemuan.collectAsState(initial = emptyList())
    val error by viewModel.error.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        viewModel.fetchPertemuan()
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
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    item {
                        // Header row for the table
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Kode Kelas", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                            Text("Hari", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                            Text("Total Pertemuan", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        }
                    }

                    items(pertemuan) { item ->
                        val totalPertemuan = item.Presensis.firstOrNull()?.total_pertemuan ?: "0"
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(item.kode_kelas, modifier = Modifier.weight(1f))
                            Text(item.hari, modifier = Modifier.weight(1f))
                            Text(totalPertemuan, modifier = Modifier.weight(1f))
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
