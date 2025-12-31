package com.merteroglu286.profile.data.mapper

import com.merteroglu286.profile.data.response.UploadImageResponse
import com.merteroglu286.profile.domain.model.UploadedImage

interface UploadImageMapper {
    suspend fun toDomain(uploadImageResponse: UploadImageResponse): UploadedImage
}