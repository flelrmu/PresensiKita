package com.example.presensikita.ui.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.presensikita.R

class EditClassActivity : ComponentActivity() { // Mengubah AddClassActivity menjadi EditClassActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EditClassScreen() // Mengubah pemanggilan AddClassScreen menjadi EditClassScreen
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditClassScreen() { // Mengubah AddClassScreen menjadi EditClassScreen
    var inputText by remember { mutableStateOf("") } // State untuk input teks

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Top Bar Icons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Ikon Solutions tetap di tempatnya
                Image(
                    painter = painterResource(id = R.drawable.solutions),
                    contentDescription = "Solutions Icon",
                    modifier = Modifier.size(144.dp, 30.dp)
                )

                // Row untuk Notification dan Profile agar lebih fleksibel
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.notification),
                        contentDescription = "Notification Icon",
                        modifier = Modifier
                            .size(43.dp, 31.dp)
                            .padding(end = 8.dp) // Mengatur jarak antar ikon
                    )
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile Icon",
                        modifier = Modifier.size(43.dp, 31.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Chevron Icon
            Image(
                painter = painterResource(id = R.drawable.leftchevron),
                contentDescription = "Chevron Icon",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 10.dp)
                    .size(33.dp, 31.dp)
            )

            Spacer(modifier = Modifier.height(70.dp))

            // Title
            Text(
                text = "Edit Kelas", // Mengubah "Tambah Kelas" menjadi "Edit Kelas"
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2A2A2A)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // OutlinedTextField untuk input biasa
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
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
                value = inputText,
                onValueChange = { inputText = it },
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
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text(text = "Dosen Pengampu") },
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

            Spacer(modifier = Modifier.weight(1f))

            // Button
            Button(
                onClick = { /* Handle edit kelas */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A844))
            ) {
                Text(text = "Simpan", color = Color.White) // Mengubah teks tombol menjadi "Simpan"
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditClassScreenPreview() { // Mengubah AddClassScreenPreview menjadi EditClassScreenPreview
    EditClassScreen()
}