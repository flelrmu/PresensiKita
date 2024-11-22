package com.example.presensikita

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.presensikita.data.UserProfile

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "profile_screen") {
        composable("profile_screen") {
            ProfileScreen(
                onLogoutClick = { /* Tambahkan fungsi logout */ },
                onEditProfileClick = { navController.navigate("edit_profile_screen") },
                onChangePasswordClick = { /* Tambahkan fungsi ubah password */ }
            )
        }
        composable("edit_profile_screen") {
            EditProfileScreen(
                initialProfile = UserProfile(), // Anda dapat mengganti dengan data yang sesuai
                onSaveClick = { updatedProfile ->
                    // Simpan profil, lalu kembali ke layar profil
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack() // Kembali ke layar sebelumnya
                }
            )
        }
    }
}
