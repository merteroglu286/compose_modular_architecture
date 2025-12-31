package com.merteroglu286.common.image

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class ImageFileManager(
    private val context: Context
) {
    private fun createImageFile(): File {
        val timeStamp = System.currentTimeMillis()
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }

    private fun getUriFromFile(file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    fun getTempImageUri(): Uri {
        return getUriFromFile(createImageFile())
    }

    fun getFileFromUri(uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val tempFile = File.createTempFile(
                "upload_${System.currentTimeMillis()}",
                ".jpg",
                context.cacheDir
            )

            FileOutputStream(tempFile).use { output ->
                inputStream.copyTo(output)
            }

            inputStream.close()
            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}