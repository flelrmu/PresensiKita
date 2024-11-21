package com.example.presensikita.ui.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presensikita.R

class ClassListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClassListScreen()
        }
    }
}

@Composable
fun ClassListScreen() {
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

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.notification),
                        contentDescription = "Notification Icon",
                        modifier = Modifier.size(43.dp, 31.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile Icon",
                        modifier = Modifier.size(43.dp, 31.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))

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
                text = "Daftar Kelas",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2A2A2A)
            )

            Spacer(modifier = Modifier.height(70.dp))

            // Add Class Section
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
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(R.drawable.add),
                    contentDescription = "Add Class",
                    modifier = Modifier.size(29.dp, 28.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // LazyColumn for Class List
            val sampleData = listOf(
                "Kalkulus/A",
                "Algoritma dan Pemrograman/B",
                "Basis Data/C",
                "Jaringan Komputer/D",
                "Sistem Operasi/E"
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(sampleData) { className ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = className,
                            fontSize = 20.sp,
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
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(R.drawable.trash),
                            contentDescription = "Trash Icon",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Attendance Report Button
            androidx.compose.material3.Button(
                onClick = { /* Handle login */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A844))
            ) {
                androidx.compose.material3.Text(text = "Laporan Kehadiran", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewClassListScreen() {
    ClassListScreen()
}
