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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.presensikita.R
import com.example.presensikita.ui.viewModel.DosenViewModel

class EditLecturerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val namaDosen = intent.getStringExtra("NAMA_DOSEN") ?: ""
            val emailDosen = intent.getStringExtra("EMAIL_DOSEN") ?: ""
            val nipDosen = intent.getStringExtra("NIP_DOSEN") ?: ""

            EditLecturerScreen(
                namaDosen = namaDosen,
                emailDosen = emailDosen,
                nipDosen = nipDosen,
                context = this
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditLecturerScreen(
    namaDosen: String,
    emailDosen: String,
    nipDosen: String,
    context: Context,
    viewModel: DosenViewModel = viewModel()
) {
    var updatedNamaDosen by remember { mutableStateOf(namaDosen) }
    var updatedEmailDosen by remember { mutableStateOf(emailDosen) }
    var updatedNipDosen by remember { mutableStateOf(nipDosen) }

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
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Icon",
                    modifier = Modifier.size(43.dp, 31.dp)
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            Image(
                painter = painterResource(id = R.drawable.leftchevron),
                contentDescription = "Chevron Icon",
                modifier = Modifier
                    .align(Alignment.Start)
                    .clickable {
                        val intent = Intent(context, LecturerListActivity::class.java)
                        context.startActivity(intent)
                        (context as? ComponentActivity)?.finish()
                    }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Edit Dosen",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2A2A2A)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Input Nama
            OutlinedTextField(
                value = updatedNamaDosen,
                onValueChange = { updatedNamaDosen = it },
                label = { Text(text = "Nama Dosen") },
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

            // Input Email
            OutlinedTextField(
                value = updatedEmailDosen,
                onValueChange = { updatedEmailDosen = it },
                label = { Text(text = "Email") },
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

            // Input NIP
            OutlinedTextField(
                value = updatedNipDosen,
                onValueChange = { updatedNipDosen = it },
                label = { Text(text = "NIP") },
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

            Button(
                onClick = {
                    viewModel.updateDosen(
                        nama = updatedNamaDosen,
                        email = updatedEmailDosen,
                        nip = updatedNipDosen
                    )
                    Toast.makeText(context, "Dosen berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, LecturerListActivity::class.java)
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
