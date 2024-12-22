package com.example.presensikita.ui.components

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.presensikita.R
import com.example.presensikita.ui.ProfilePageActivity
import com.example.presensikita.ui.viewModel.ChangePasswordViewModel
import com.example.presensikita.ui.viewModel.ChangePasswordViewModelFactory

class ChangePasswordActivity : ComponentActivity() {
    private val viewModel: ChangePasswordViewModel by viewModels {
        ChangePasswordViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.changePasswordResult.observe(this) { result ->
            result.fold(
                onSuccess = { message ->
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    finish()
                },
                onFailure = { exception ->
                    Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
                }
            )
        }

        setContent {
            ChangePasswordScreen(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    viewModel: ChangePasswordViewModel
) {
    val context = LocalContext.current

    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) } // Menyimpan status visibility password

    val scrollState = rememberScrollState()

    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    fun validateAndChangePassword(): Boolean {
        when {
            oldPassword.isEmpty() -> {
                errorMessage = "Password lama harus diisi"
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                isError = true
            }
            newPassword.isEmpty() -> {
                Toast.makeText(context, "Password baru harus diisi", Toast.LENGTH_SHORT).show()
                isError = true
            }
            newPassword.length < 6 -> {
                Toast.makeText(context, "Password baru minimal 6 karakter", Toast.LENGTH_SHORT).show()
                isError = true
            }
            newPassword != confirmPassword -> {
                Toast.makeText(context, "Konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show()
                isError = true
            }
            else -> {
                isError = false
            }
        }
        return !isError
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
//            .padding(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState) // Tambahkan scroll di sini
                .padding(horizontal = 16.dp, vertical = 40.dp)
                .align(Alignment.TopStart)
        ) {

            Image(
                painter = painterResource(id = R.drawable.leftchevron),
                contentDescription = "Chevron Icon",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 0.dp)
                    .size(33.dp, 31.dp)
                    .clickable {
                        context.startActivity(Intent(context, ProfilePageActivity::class.java))
                    }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Judul halaman di tengah
            Text(
                text = "Ubah Password",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2A2A2A)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Input Password Lama
            Column(modifier = Modifier.padding(horizontal = 52.dp)) {
                Text(
                    text = "Password Lama",
                    fontSize = 17.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Start)
                )
                OutlinedTextField(
                    value = oldPassword,
                    onValueChange = { oldPassword = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                            )
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Input Password Baru
            Column(modifier = Modifier.padding(horizontal = 52.dp)) {
                Text(
                    text = "Password Baru",
                    fontSize = 17.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Start)
                )
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                            )
                        }
                    }
                )
            }

            // Input Konfirmasi Password
            Column(modifier = Modifier.padding(horizontal = 52.dp)) {
                Text(
                    text = "Confirm Password",
                    fontSize = 17.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Start)
                )
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                            )
                        }
                    }
                )
            }


            Spacer(modifier = Modifier.height(40.dp))

            // Tombol Simpan di bawah dengan lebar lebih kecil
            Button(
                onClick = {
                    if (validateAndChangePassword()) {
                        viewModel.changePassword(oldPassword, newPassword)
                    }
                },
                modifier = Modifier
                    .width(120.dp)
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A844)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Save", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChangePasswordScreenPreview() {
    ChangePasswordScreen(
        viewModel = ChangePasswordViewModelFactory(LocalContext.current).create(ChangePasswordViewModel::class.java)
    )
}
