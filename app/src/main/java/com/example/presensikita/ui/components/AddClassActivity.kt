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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.presensikita.R
import com.example.presensikita.data.model.Class
import com.example.presensikita.ui.viewModel.AddClassViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class AddClassActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val addClassViewModel: AddClassViewModel = viewModel()
            AddClassScreen(addClassViewModel) { isSuccess ->
                if (isSuccess) {
                    val intent = Intent(this, ClassListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddClassScreen(
    addClassViewModel: AddClassViewModel = viewModel(),
    onResult: (Boolean) -> Unit = {}
) {
    val context = LocalContext.current
    var className by remember { mutableStateOf("") }
    var classCode by remember { mutableStateOf("") }
    var lecturer by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }

    val isSuccess by addClassViewModel.isSuccess.collectAsState()
    val errorMessage by addClassViewModel.errorMessage.collectAsState()


    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            showSnackbar = true
            onResult(true)
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
                text = "Tambah Kelas",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2A2A2A)
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = className,
                onValueChange = { className = it },
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
                value = classCode,
                onValueChange = { classCode = it },
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
                value = lecturer,
                onValueChange = { lecturer = it },
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

            Button(
                onClick = {
                    val newClass = Class(
                        class_name = className,
                        class_code = classCode,
                        lecturer = lecturer
                    )
                    addClassViewModel.addClass(newClass)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A844))
            ) {
                Text(text = "Tambah", color = Color.White)
            }

            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

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
                Text(text = "Kelas berhasil ditambahkan!", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddClassScreenPreview() {
    AddClassScreen()
}
