//package com.example.presensikita
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.example.presensikita.data.UserProfile
//import com.example.presensikita.ui.EditProfileScreen
//import com.example.presensikita.ui.HomePage
//import com.example.presensikita.ui.ProfileScreen
//import com.example.presensikita.ui.jadwal_kuliah.ScheduleTableScreen
//
//@Composable
//fun NavGraph(navController: NavHostController) {
//    NavHost(navController = navController, startDestination = "profile_screen") {
//        composable("home_screen") {
//            HomePage(
//                onProfileClick = {},
//                onDosenClick = {},
//                onKelasClick = {},
//                onJadwalClick = {}
//            )
//        }
//
//        composable("tabel_jadwal_screen") {
//            ScheduleTableScreen(
//                schedules = dummySchedules(),
//                onBack = { navController.navigate("home_screen") },
////                onProfileClick = { navController.navigate("profile_screen") },
////                onAddJadwal = { navController.navigate("") },
////                onEditJadwal = {navController.navigate("")},
////                kembali = {navController.popBackStack()}
//            )
//        }
//
//        composable("profile_screen") {
//            ProfileScreen(
//                onLogoutClick = { /* Tambahkan fungsi logout */ },
//                onEditProfileClick = { navController.navigate("edit_profile_screen") },
//                onChangePasswordClick = { /* Tambahkan fungsi ubah password */ }
//            )
//        }
//        composable("edit_profile_screen") {
//            EditProfileScreen(
//                initialProfile = UserProfile(), // Anda dapat mengganti dengan data yang sesuai
//                onSaveClick = { updatedProfile ->
//                    // Simpan profil, lalu kembali ke layar profil
//                    navController.popBackStack()
//                },
//                onBackClick = {
//                    navController.popBackStack() // Kembali ke layar sebelumnya
//                }
//            )
//        }
//    }
//}
