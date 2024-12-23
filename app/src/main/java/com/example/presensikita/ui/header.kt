package com.example.presensikita.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier // 正确导入 Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.presensikita.R
import java.io.File

@Composable
fun header() {
    // Header content
    // Header

    // Mendapatkan konteks saat ini
    val context = LocalContext.current

    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    // Cek gambar lokal terlebih dahulu
    val localImagePath = sharedPreferences.getString("local_profile_image", null)
    val serverImagePath = sharedPreferences.getString("server_profile_image", null)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
//        Image(
//            painter = painterResource(id = R.drawable.solutions),
//            contentDescription = "Solutions Icon",
//            modifier = Modifier.size(144.dp, 30.dp)
//        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Presensi",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 28.sp,
                color = Color(0xFF00AF4F),
            )
            Text(
                text = "Kita",
                fontSize = 28.sp,
                style = MaterialTheme.typography.headlineLarge
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.notification),
                contentDescription = "Notification Icon",
                modifier = Modifier.size(43.dp, 31.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
//            Image(
//                painter = painterResource(id = R.drawable.profile),
//                contentDescription = "Profile Icon",
//                modifier = Modifier
//                    .size(43.dp, 31.dp)
//                    .clickable {
//                        context.startActivity(Intent(context, ProfilePageActivity::class.java))
//                    }
//            )
            // Profile Image
            if (localImagePath != null) {
                // Gunakan gambar lokal
                AsyncImage(
                    model = File(localImagePath),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(43.dp, 31.dp)
                        .clickable {
                            context.startActivity(Intent(context, ProfilePageActivity::class.java))
                        }
                        .clip(CircleShape),
                    error = painterResource(id = R.drawable.ic_profile_placeholder)
                )
            } else if (serverImagePath != null && serverImagePath.isNotEmpty()) {
                // Gunakan gambar dari server
                AsyncImage(
                    model = serverImagePath,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(43.dp, 31.dp)
                        .clickable {
                            context.startActivity(Intent(context, ProfilePageActivity::class.java))
                        }
                        .clip(CircleShape),
                    error = painterResource(id = R.drawable.profile)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(43.dp, 31.dp)
                        .clickable {
                            context.startActivity(Intent(context, ProfilePageActivity::class.java))
                        }
                        .clip(CircleShape)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    header()
}