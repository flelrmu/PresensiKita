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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presensikita.ui.header

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
        header()


        Spacer(modifier = Modifier.height(50.dp))

        Image(
            painter = painterResource(id = R.drawable.leftchevron),
            contentDescription = "Chevron Icon",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 0.dp)
                .size(33.dp, 31.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))


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

            Spacer(modifier = Modifier.width(16.dp))

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

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        onChangePasswordClick = {},
        onEditProfileClick = {},
        onLogoutClick = {}
    )
}