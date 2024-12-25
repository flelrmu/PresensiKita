package com.example.presensikita.ui.components

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.presensikita.R
import com.example.presensikita.ui.viewModel.DosenViewModel

class AddLecturerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: DosenViewModel = viewModel()
            AddLecturerScreen(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLecturerScreen(
    viewModel: DosenViewModel = viewModel()
) {
    val context = LocalContext.current
    var namaDosen by remember { mutableStateOf("") }
    var nip by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val error by viewModel.error.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Effect to handle successful submission
    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            val intent = Intent(context, LecturerListActivity::class.java)
            context.startActivity(intent)
            (context as? ComponentActivity)?.finish()
        }
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
                        val intent = Intent(context, LecturerListActivity::class.java)
                        context.startActivity(intent)
                        (context as? ComponentActivity)?.finish()
                    }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Tambah Dosen",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2A2A2A)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Input Fields
            OutlinedTextField(
                value = namaDosen,
                onValueChange = { namaDosen = it },
                label = { Text("Nama Dosen") },
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
                value = nip,
                onValueChange = { nip = it },
                label = { Text("NIP") },
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
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
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
                    when {
                        namaDosen.trim().isEmpty() -> {
                            viewModel.setError("Nama dosen tidak boleh kosong")
                        }
                        nip.trim().isEmpty() -> {
                            viewModel.setError("NIP tidak boleh kosong")
                        }
                        email.trim().isEmpty() -> {
                            viewModel.setError("Email tidak boleh kosong")
                        }
                        else -> {
                            val newDosen = Dosen(
                                nip = nip.trim(),
                                nama = namaDosen.trim(),
                                email = email.trim()
                            )
                            viewModel.addDosen(newDosen)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A844)),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text("Tambah", color = Color.White)
                }
            }
        }

        // Error Snackbar
        error?.let { errorMessage ->
            Snackbar(
                action = {
                    TextButton(onClick = { viewModel.setError(null) }) {
                        Text("Tutup", color = Color.White)
                    }
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(errorMessage, color = Color.White)
            }
        }
    }
}

fun Dosen(nip: String, nama: String, email: String) {

}

@Preview(showBackground = true)
@Composable
fun PreviewAddLecturerScreen() {
    AddLecturerScreen()
}