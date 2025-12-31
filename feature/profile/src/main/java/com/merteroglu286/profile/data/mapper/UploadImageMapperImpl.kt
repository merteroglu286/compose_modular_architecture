package com.merteroglu286.profile.data.mapper

import com.merteroglu286.profile.data.response.UploadImageResponse
import com.merteroglu286.profile.domain.model.UploadedImage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UploadImageMapperImpl(
    private val defaultDispatcher: CoroutineDispatcher
): UploadImageMapper{
    override suspend fun toDomain(uploadImageResponse: UploadImageResponse): UploadedImage {
        return withContext(defaultDispatcher){
            UploadedImage(
                id = uploadImageResponse.id ?: "",
                title = uploadImageResponse.title ?: "",
                url = uploadImageResponse.url ?: "",
                displayUrl = uploadImageResponse.displayUrl ?: "",
                deleteUrl = uploadImageResponse.deleteUrl ?: ""
            )
        }
    }
}