package com.example.presensikita

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    onLogoutClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onChangePasswordClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
            Row {
                IconButton(onClick = { /* Handle notification */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.notification),
                        contentDescription = "Notifications",
                        tint = Color(0xFF00AF4F),
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
                IconButton(onClick = { /* Handle profile */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile",
                        tint = Color(0xFF00AF4F),
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
            }
        }

        Text(
            text = "Your Profile",
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

        Text(
            text = "Admin Departemen Sistem Informasi",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Profile Information
        ProfileSection(
            title = "Email",
            content = "admin_dsi@unand.ac.id"
        )

        ProfileSection(
            title = "Departemen",
            content = "Sistem Informasi"
        )

        ProfileSection(
            title = "Fakultas",
            content = "Teknologi Informasi"
        )

        // Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Ubah Password",
                color = MaterialTheme.colorScheme.primary,  // Menggunakan warna primary #00AF4F
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .clickable { onChangePasswordClick() }  // Menambahkan fungsi klik
            )

            Text(
                text = "Edit Profile",
                color = MaterialTheme.colorScheme.primary,  // Menggunakan warna primary #00AF4F
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .clickable { onEditProfileClick() }  // Menambahkan fungsi klik
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = onLogoutClick,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(vertical = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00AF4F)
                ),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)  // Menambahkan padding 24dp di kanan dan kiri teks
            ) {
                Text(
                    text = "Logout",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun ProfileSection(
    title: String,
    content: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)  // Menambahkan padding horizontal
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}