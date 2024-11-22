package com.example.presensikita

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.presensikita.data.UserProfile
import com.example.presensikita.ui.theme.PresensiKitaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PresensiKitaTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { /* innerPadding -> */
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                    ProfileScreen(
//                        onLogoutClick = { /* Handle logout */ },
//                        onEditProfileClick = { /* Handle edit profile */ },
//                        onChangePasswordClick = { /* Handle password change */ }
//                    )
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }

//                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    PresensiKitaTheme {
//        Greeting("Android")
//        EditProfileScreen(onSaveClick = { updatedProfile ->
//            // Handle saving profile here
//            // Misalnya update ke database atau API
//        },
//            initialProfile = UserProfile(
//                nama = "John Doe",
//                email = "john.doe@example.com",
//                departemen = "Sistem Informasi",
//                fakultas = "Teknologi Informasi"
//            )
//        )

    }
}