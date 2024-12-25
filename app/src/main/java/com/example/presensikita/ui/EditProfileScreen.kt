package com.example.presensikita.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.presensikita.R
import com.example.presensikita.ui.viewModel.EditProfileViewModel

class EditProfileActivity : ComponentActivity() {
    private val viewModel: EditProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get user data from SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("access_token", "") ?: ""
        val currentName = sharedPreferences.getString("user_name", "") ?: ""
        val currentEmail = sharedPreferences.getString("user_email", "") ?: ""
        val currentDepartemenId = sharedPreferences.getInt("user_departemen_id", 0)
        val currentPhotoUrl = sharedPreferences.getString("user_foto_profile", null)

        setContent {
            EditProfileScreen(
                viewModel = viewModel,
                token = token,
                initialName = currentName,
                initialEmail = currentEmail,
                initialDepartemenId = currentDepartemenId,
                currentPhotoUrl = currentPhotoUrl
            )
        }

        // Observe update result
        viewModel.updateResult.observe(this) { result ->
            result.fold(
                onSuccess = { response ->
                    // Update SharedPreferences with new data
                    with(sharedPreferences.edit()) {
                        putString("user_name", response.admin.nama)
                        putString("user_email", response.admin.email)
                        putInt("user_departemen_id", response.admin.departemen_id ?: 0)
                        // Get department details from the departments list
                        val department = viewModel.departments.value?.find {
                            it.departemen_id == response.admin.departemen_id
                        }
                        putString("user_departemen", department?.nama_departemen)
                        putString("user_fakultas", department?.fakultas)
                        putString("user_foto_profile", response.admin.foto_profile)
                        apply()
                    }
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    // Force refresh SharedPreferences in ProfilePageActivity
                    val intent = Intent(this, ProfilePageActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(intent)
                    finish()
                },
                onFailure = { exception ->
                    Toast.makeText(this, "Update failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }

        // Load departments
        viewModel.getDepartments()
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel,
    token: String,
    initialName: String,
    initialEmail: String,
    initialDepartemenId: Int,
    currentPhotoUrl: String?
) {

    var name by remember { mutableStateOf(initialName) }
    var email by remember { mutableStateOf(initialEmail) }
    var selectedDepartemenId by remember { mutableStateOf(initialDepartemenId) }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var isEmailValid by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }

    val departments by viewModel.departments.observeAsState(initial = emptyList())
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { profileImageUri = it }
    }

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
//                    context.startActivity(Intent(context, ProfilePageActivity::class.java))
                    (context as Activity).finish()
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
                AsyncImage(
                    model = profileImageUri,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            } else if (!currentPhotoUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = currentPhotoUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile_placeholder),
                    contentDescription = "Default Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }

            IconButton(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(32.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = "Change Photo",
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
                value = name,
                onValueChange = { name = it },
                label = { Text("Nama") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
            )

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
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

            // Department Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
//                    .padding(horizontal = 0.dp)
            ) {
                OutlinedTextField(
                    value = departments.find { it.departemen_id == selectedDepartemenId }?.let {
                        "${it.nama_departemen}/${it.fakultas}"
                    } ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Department") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    departments.forEach { department ->
                        DropdownMenuItem(
                            text = {
                                Text("${department.nama_departemen}/${department.fakultas}")
                            },
                            onClick = {
                                selectedDepartemenId = department.departemen_id
                                expanded = false
                            }
                        )
                    }
                }
            }

        }

        Spacer(modifier = Modifier.weight(1f))

        // Save Button
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    viewModel.updateProfile(
                        context,
                        token,
                        name,
                        email,
                        selectedDepartemenId,
                        profileImageUri
                    )
                },
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
fun EditProfileScreenPreview() {
    EditProfileScreen(
        viewModel = EditProfileViewModel(),
        token = "eyJ0eXAiOiJKV1QiLCJh",
        initialName = "John Doe",
        initialEmail = "johndoe@example.com",
        initialDepartemenId = 1,
        currentPhotoUrl = null
    )
}

