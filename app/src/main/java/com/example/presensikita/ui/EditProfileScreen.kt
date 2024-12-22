package com.example.presensikita.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.example.presensikita.R
import com.example.presensikita.data.UserProfile
import com.example.presensikita.finish

class EditProfileScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EditProfileScreen(
                onSaveClick = { profile ->
                    // Lakukan sesuatu dengan data yang diperoleh
                    finish()
                },
                initialProfile = UserProfile(
                    nama = "John Doe",
                    email = "johndoe@example.com",
                    departemen = "Computer Science",
                    fakultas = "Science"
                )
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onSaveClick: (UserProfile) -> Unit,
    initialProfile: UserProfile = UserProfile()
) {
    var profile by remember { mutableStateOf(initialProfile) }
    val scrollState = rememberScrollState()

    val context = LocalContext.current

    // State untuk validasi email
    var isEmailValid by remember { mutableStateOf(true) }

    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher untuk membuka galeri
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) profileImageUri = uri
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
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
                    context.startActivity(Intent(context, ProfilePageActivity::class.java))
                }
        )

        Spacer(modifier = Modifier.height(10.dp))


        Text(
            text = "Edit Profile",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Foto profil
        Box(
            modifier = Modifier
                .size(145.dp),
//                .clip(CircleShape),
//                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            if (profileImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(profileImageUri),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.profile), // Gambar default
                    contentDescription = "Default Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }

            // Tombol untuk mengubah foto
            IconButton(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(32.dp)
                    .offset(x = 4.dp, y = 8.dp) // Offset untuk memperbaiki posisi
                    .zIndex(1f) // Prioritas rendering lebih tinggi
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit), // Ikon edit
                    contentDescription = "Edit Icon",
                    tint = Color.White
                )
            }
        }

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
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
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
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                ),
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
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
            )

//            OutlinedTextField(
//                value = profile.fakultas,
//                onValueChange = { profile = profile.copy(fakultas = it) },
//                label = { Text("Fakultas") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true,
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Color.Black,
//                    unfocusedBorderColor = Color.Black
//                )
//            )
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


@Preview(showBackground = true)
@Composable
fun PreviewEditProfileScreen() {
    EditProfileScreen(
        onSaveClick = {},
        initialProfile = UserProfile()
    )
}

