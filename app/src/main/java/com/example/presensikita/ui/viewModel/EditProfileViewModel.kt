package com.example.presensikita.ui.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presensikita.configs.RetrofitClient
import com.example.presensikita.data.model.LoginResponse
import com.example.presensikita.data.model.Departemen
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream

class EditProfileViewModel : ViewModel() {
    private val _updateResult = MutableLiveData<Result<LoginResponse>>()
    val updateResult: LiveData<Result<LoginResponse>> = _updateResult

    private val _departments = MutableLiveData<List<Departemen>>()
    val departments: LiveData<List<Departemen>> = _departments

    private val apiService = RetrofitClient.getApiService()

    fun saveImageToInternalStorage(context: Context, imageUri: Uri): String? {
        return try {
            // Buat direktori untuk menyimpan foto profile
            val directory = File(context.filesDir, "profile_images")
            if (!directory.exists()) {
                directory.mkdirs()
            }

            // Buat nama file unik
            val fileName = "profile_${System.currentTimeMillis()}.jpg"
            val file = File(directory, fileName)

            // Kompres dan simpan gambar
            context.contentResolver.openInputStream(imageUri)?.use { input ->
                val bitmap = BitmapFactory.decodeStream(input)
                FileOutputStream(file).use { output ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, output)
                }
            }

            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun getDepartments() {
        viewModelScope.launch {
            try {
                val departments = apiService.getDepartments()
                _departments.value = departments
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    // Add this function to get department details
    fun getDepartmentDetails(departmentId: Int): LiveData<Departemen?> {
        val result = MutableLiveData<Departemen?>()
        viewModelScope.launch {
            val department = departments.value?.find { it.departemen_id == departmentId }
            result.postValue(department)
        }
        return result
    }

    fun updateProfile(
        context: Context,
        token: String,
        nama: String,
        email: String,
        departemenId: Int,
        imageUri: Uri?
    ) {
        viewModelScope.launch {
            try {
                // Convert text fields to RequestBody
                val namaRequest = nama.toRequestBody("text/plain".toMediaTypeOrNull())
                val emailRequest = email.toRequestBody("text/plain".toMediaTypeOrNull())
                val departemenIdRequest = departemenId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                // Handle image file
                var photoMultipart: MultipartBody.Part? = null
                var localImagePath: String? = null

                imageUri?.let { uri ->
                    // Simpan gambar ke penyimpanan internal
                    localImagePath = saveImageToInternalStorage(context, uri)

                    val tempFile = File(context.cacheDir, "temp_profile_image.jpg")
                    context.contentResolver.openInputStream(uri)?.use { input ->
                        val bitmap = BitmapFactory.decodeStream(input)
                        FileOutputStream(tempFile).use { output ->
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, output)
                        }
                    }

                    val requestFile = tempFile.asRequestBody("image/*".toMediaTypeOrNull())
                    photoMultipart = MultipartBody.Part.createFormData("foto_profile", tempFile.name, requestFile)
                }

                // Make API call
                val response = apiService.editProfile(
                    "Bearer $token",
                    namaRequest,
                    emailRequest,
                    departemenIdRequest,
                    photoMultipart
                )

                if (response.isSuccessful) {
                    // Update SharedPreferences dengan data baru
                    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    with(sharedPreferences.edit()) {
                        putString("user_name", nama)
                        putString("user_email", email)
                        putInt("user_departemen_id", departemenId)

                        // Simpan path gambar lokal jika ada
                        localImagePath?.let {
                            putString("local_profile_image", it)
                        }

                        // Simpan juga URL dari server
                        response.body()?.admin?.foto_profile?.let { serverPath ->
                            putString("server_profile_image", serverPath)
                        }

                        apply()
                    }

                    _updateResult.value = Result.success(response.body()!!)
                } else {
                    _updateResult.value = Result.failure(Exception("Update failed: ${response.code()}"))
                }

            } catch (e: Exception) {
                _updateResult.value = Result.failure(e)
            }
        }
    }
}