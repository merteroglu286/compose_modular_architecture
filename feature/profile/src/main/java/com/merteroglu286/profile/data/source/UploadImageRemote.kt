package com.merteroglu286.profile.data.source

import java.io.File
import com.merteroglu286.domain.result.Result
import com.merteroglu286.profile.domain.model.UploadedImage

interface UploadImageRemote {
    suspend fun uploadedImage(apiKey: String, imageFile: File) : Result<UploadedImage>
}