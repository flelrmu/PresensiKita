package com.example.presensikita.ui.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presensikita.R

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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(R.drawable.solutions),
                    contentDescription = "Solutions",
                    modifier = Modifier.size(144.dp, 30.dp)
                )
                Image(
                    painter = painterResource(R.drawable.notification),
                    contentDescription = "Notification",
                    modifier = Modifier.size(43.dp, 31.dp)
                )
                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "Profile",
                    modifier = Modifier.size(50.dp, 50.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "Absensi Dosen",
                fontSize = 34.sp,
                fontFamily = FontFamily.Serif,
                color = Color(0xFF2A2A2A),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lecturer list item
            LecturerItem(name = "Affiyardi Dwi Kartika, MT", email = "Dwi@gmail.com", nidn = "12121212125452")
            LecturerItem(name = "Hasdi Putra, MT", email = "Hasdi@gmail.com", nidn = "385763976749")

            Spacer(modifier = Modifier.height(16.dp))

            // Add Lecturer Section
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
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(R.drawable.add),
                    contentDescription = "Add Lecturer",
                    modifier = Modifier.size(29.dp, 28.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Download Attendance Report Button using download.png
            Button(
                onClick = { /* Handle download */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A844))
            ) {
                Image(
                    painter = painterResource(R.drawable.download), // The image for download
                    contentDescription = "Download Absensi",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Download Absensi", color = Color.White)
            }
        }
    }
}

@Composable
fun LecturerItem(name: String, email: String, nidn: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                color = Color(0xFF2A2A2A)
            )
            Text(
                text = email,
                fontSize = 14.sp,
                color = Color(0xFF6C6C6C)
            )
            Text(
                text = nidn,
                fontSize = 14.sp,
                color = Color(0xFF6C6C6C)
            )
        }

        // Edit and Delete icons
        IconButton(onClick = { /* Handle edit */ }) {
            Icon(
                painter = painterResource(R.drawable.edit),
                contentDescription = "Edit",
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = { /* Handle delete */ }) {
            Icon(
                painter = painterResource(R.drawable.trash),
                contentDescription = "Delete",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLecturerListScreen() {
    LecturerListScreen()
}
