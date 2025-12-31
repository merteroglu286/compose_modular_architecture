package com.merteroglu286.profile.data.response

import com.google.gson.annotations.SerializedName

data class UploadImageResponse(
    val id: String?,
    val title: String?,
    @SerializedName("url_viewer") val urlViewer: String?,
    val url: String?,
    @SerializedName("display_url") val displayUrl: String?,
    val width: String?,
    val height: String?,
    val size: String?,
    val time: String?,
    val expiration: String?,
    val image: ImageData?,
    val thumb: ImageData?,
    val medium: ImageData?,
    @SerializedName("delete_url") val deleteUrl: String?
)

data class ImageData(
    val filename: String?,
    val name: String?,
    val mime: String?,
    val extension: String?,
    val url: String?
)