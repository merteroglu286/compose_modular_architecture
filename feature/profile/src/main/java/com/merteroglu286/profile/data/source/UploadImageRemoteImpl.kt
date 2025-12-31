package com.merteroglu286.profile.data.source

import android.util.Log
import com.merteroglu286.data.mapper.toDomain
import com.merteroglu286.data.source.DataSource.Companion.UNKNOWN
import com.merteroglu286.data.source.NetworkDataSource
import com.merteroglu286.domain.model.Error
import com.merteroglu286.domain.result.Result
import com.merteroglu286.profile.data.mapper.UploadImageMapper
import com.merteroglu286.profile.data.service.UploadImageService
import com.merteroglu286.profile.domain.model.UploadedImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.net.URLConnection

class UploadImageRemoteImpl(
    private val networkDataSource: NetworkDataSource<UploadImageService>,
    private val uploadImageMapper: UploadImageMapper
) : UploadImageRemote {

    override suspend fun uploadedImage(
        apiKey: String,
        imageFile: File
    ): Result<UploadedImage> {
        val mimeType = URLConnection.guessContentTypeFromName(imageFile.name) ?: "image/jpeg"
        val requestBody = imageFile.asRequestBody(mimeType.toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            imageFile.name,
            requestBody
        )

        return networkDataSource.performRequest(
            request = {
                uploadImage(
                    apiKey = apiKey,
                    image = multipartBody
                )
            },
            onSuccess = { response, _ ->
                response.data?.let {
                    Result.success(uploadImageMapper.toDomain(it))
                } ?: Result.error(Error(
                    errorCode = UNKNOWN,
                    errorMessage = "Veri bulunamadı",
                    errorFieldList = emptyList()
                ))
            },
            onError = { errorResponse, code ->
                Log.e("UploadImageRemote", "onError: code=$code errorResponse=$errorResponse")
                Result.error(errorResponse.toDomain(code))
            }
        )
    }
}