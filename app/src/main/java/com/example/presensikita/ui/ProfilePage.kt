package com.example.presensikita.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.presensikita.R
import com.example.presensikita.ui.components.ChangePasswordActivity
import com.example.presensikita.ui.components.HomePageActivity
import com.example.presensikita.ui.components.LoginActivity

class ProfilePageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val context = LocalContext.current
            val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

            // Baca data user dari SharedPreferences
            val userData = UserData(
                id = sharedPreferences.getInt("user_id", 0),
                nama = sharedPreferences.getString("user_name", "") ?: "",
                email = sharedPreferences.getString("user_email", "") ?: "",
                departemen_id = sharedPreferences.getInt("user_departemen_id", 0),
                nama_departemen = sharedPreferences.getString("user_departemen", "") ?: "",
                fakultas = sharedPreferences.getString("user_fakultas", "") ?: "",
                foto_profile = sharedPreferences.getString("user_foto_profile", null)
            )

            ProfileScreen(
                userData = userData,
                onChangePasswordClick = {},
                onEditProfileClick = {},
                onLogoutClick = { logoutUser(context) }
            )
        }
    }

}

// Data class untuk menyimpan data user
data class UserData(
    val id: Int,
    val email: String,
    val nama: String,
    val foto_profile: String? = null,
    val departemen_id: Int,
    val nama_departemen: String,
    val fakultas: String
)

@Composable
fun ProfileScreen(
    userData: UserData,
    onLogoutClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onChangePasswordClick: () -> Unit
) {
    // Mendapatkan konteks saat ini
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
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
                .clickable {
                    context.startActivity(Intent(context, HomePageActivity::class.java))
                }
        )

        Spacer(modifier = Modifier.height(10.dp))


        Text(
            text = "Your Profile",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Profile Image
        if (userData.foto_profile != null && userData.foto_profile.isNotEmpty()) {
            // Implementasi loading foto profil dari URL
            AsyncImage(
                model = userData.foto_profile,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                error = painterResource(id = R.drawable.ic_profile_placeholder)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_placeholder),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
        }

        Text(
            text = userData.nama,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Profile Information
        ProfileSection(
            title = "Email",
            content = userData.email
        )

        ProfileSection(
            title = "Departemen",
            content = userData.nama_departemen
        )

        ProfileSection(
            title = "Fakultas",
            content = userData.fakultas
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
                    .clickable {
                        context.startActivity(Intent(context, ChangePasswordActivity::class.java))
                    }  // Menambahkan fungsi klik
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Edit Profile",
                color = MaterialTheme.colorScheme.primary,  // Menggunakan warna primary #00AF4F
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .clickable { /* onEditProfileClick() */ }  // Menambahkan fungsi klik
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
    val previewData = UserData(
        id = 1,
        email = "preview@example.com",
        nama = "Preview User",
        foto_profile = null,
        departemen_id = 1,
        nama_departemen = "Preview Department",
        fakultas = "Preview Faculty"
    )

    ProfileScreen(
        userData = previewData,
        onChangePasswordClick = {},
        onEditProfileClick = {},
        onLogoutClick = {}
    )
}