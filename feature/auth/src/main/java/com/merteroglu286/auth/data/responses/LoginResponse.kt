package com.merteroglu286.auth.data.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("userName")
    val userName: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("photo")
    val photo: String?
)
