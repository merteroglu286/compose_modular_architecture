package com.merteroglu286.profile.domain.model

data class UploadedImage(
    val id: String,
    val title: String,
    val url: String,
    val displayUrl: String,
    val deleteUrl: String
)