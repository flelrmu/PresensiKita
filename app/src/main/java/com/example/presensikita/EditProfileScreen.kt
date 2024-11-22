package com.example.presensikita

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.presensikita.data.UserProfile

@Composable
fun EditProfileScreen(
    onSaveClick: (UserProfile) -> Unit,
    initialProfile: UserProfile = UserProfile(),
    onBackClick: () -> Unit = {}
) {
    var profile by remember { mutableStateOf(initialProfile) }
    val scrollState = rememberScrollState()

    // State untuk validasi email
    var isEmailValid by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Logo section
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ABF ",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(0xFF00AF4F)
                )
                Text(
                    text = "Solutions",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            // Icons
            Row {
                IconButton(onClick = { /* Handle notification */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notifications),
                        contentDescription = "Notifications",
                        tint = Color(0xFF00AF4F)
                    )
                }
                IconButton(onClick = { /* Handle profile */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_profile),
                        contentDescription = "Profile",
                        tint = Color(0xFF00AF4F)
                    )
                }
            }
        }

        Text(
            text = "Edit Profile",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Profile Image
        Image(
            painter = painterResource(id = R.drawable.ic_profile_placeholder),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        // Form Fields
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(top = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = profile.nama,
                onValueChange = { profile = profile.copy(nama = it) },
                label = { Text("Nama") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = profile.email,
                onValueChange = {
                    profile = profile.copy(email = it)
                    // Periksa validasi email
                    isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = !isEmailValid // Tandai sebagai error jika tidak valid
            )

            if (!isEmailValid) {
                Text(
                    text = "Masukkan email yang valid",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            OutlinedTextField(
                value = profile.departemen,
                onValueChange = { profile = profile.copy(departemen = it) },
                label = { Text("Departemen") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = profile.fakultas,
                onValueChange = { profile = profile.copy(fakultas = it) },
                label = { Text("Fakultas") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Save Button
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { onSaveClick(profile) },
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(vertical = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00AF4F)
                ),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
