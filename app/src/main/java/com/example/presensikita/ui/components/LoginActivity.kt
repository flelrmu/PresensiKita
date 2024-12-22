package com.example.presensikita.ui.components

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import com.example.presensikita.data.model.LoginRequest
import com.example.presensikita.ui.viewModel.LoginViewModel

class LoginActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("access_token", null)

        if (!token.isNullOrEmpty()) {
            // Token masih ada, arahkan ke halaman utama
            startActivity(Intent(this, HomePageActivity::class.java))
            finish()
        } else {
            setContent {
                LoginScreen { email, password ->
                    loginViewModel.login(LoginRequest(email, password))
                }
            }
        }

        // Observe perubahan hasil login dari ViewModel
        loginViewModel.loginResponse.observe(this, Observer { response ->
            if (response != null) {
                if (response.isSuccessful) {

                    var namaAdmin: String = ""
                    response.body()?.let { loginResponse ->
                        // Simpan token dan refresh token
                        val sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
                        with(sharedPreferences.edit()) {
                            putString("access_token", loginResponse.accessToken)
                            putString("refresh_token", loginResponse.refreshToken)
                            // Konversi dan simpan ID sebagai Integer
                            putInt("user_id", loginResponse.admin.id ?: 0)
                            putString("user_name", loginResponse.admin.nama ?: "")
                            namaAdmin = loginResponse.admin.nama ?: ""
                            putString("user_email", loginResponse.admin.email ?: "")
                            putInt("user_departemen_id", loginResponse.admin.departemen_id ?: 0)
                            putString("user_departemen", loginResponse.admin.nama_departemen ?: "")
                            putString("user_fakultas", loginResponse.admin.fakultas ?: "")
                            putString("user_foto_profile", loginResponse.admin.foto_profile)
                            apply()
                        }
                    }

                    Log.d("LoginActivity", "Navigating to HomePage")
                    Toast.makeText(this, "Login berhasil \n Login Sebagai ${namaAdmin}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomePageActivity::class.java)
                    startActivity(intent)
                    finish() // Tutup LoginActivity agar tidak bisa kembali
                } else {
                    Toast.makeText(this, "Login gagal: ${response.code()} \n Email atau Password Salah", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Terjadi kesalahan, silakan coba lagi.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginClick: (String, String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) } // Menyimpan status visibility password

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
//            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .align(Alignment.Center), // Konten di tengah layar
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp, vertical = 8.dp),
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

            Spacer(modifier = Modifier.height(20.dp))
        }

        Button(
            onClick = { onLoginClick(email, password) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 45.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A844))
        ) {
            Text(text = "Login", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen { _, _ -> }
}
