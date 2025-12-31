package com.merteroglu286.profile.domain.usecase

import com.merteroglu286.domain.result.Result
import com.merteroglu286.domain.usecase.AsyncUseCase
import com.merteroglu286.profile.data.source.UploadImageRemote
import com.merteroglu286.profile.domain.model.UploadedImage
import java.io.File
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(private val uploadImageRemote: UploadImageRemote) :
    AsyncUseCase<UploadImageUseCase.Input, UploadedImage>() {
    override suspend fun run(input: Input): Result<UploadedImage> {
        return uploadImageRemote.uploadedImage(input.apiKey, input.imageFile)
    }

    data class Input(val apiKey: String, val imageFile: File)
}