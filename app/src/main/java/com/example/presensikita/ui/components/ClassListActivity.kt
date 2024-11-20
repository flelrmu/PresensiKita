package com.example.presensikita.ui.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
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
                text = "Daftar Kelas",
                fontSize = 34.sp,
                fontFamily = FontFamily.Serif,
                color = Color(0xFF2A2A2A),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(200.dp))

            // Class item example
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.leftchevron),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Kalkulus/A",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xFF2A2A2A)
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.edit),
                    contentDescription = "Edit",
                    modifier = Modifier.size(23.dp, 20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(R.drawable.trash),
                    contentDescription = "Delete",
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

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
