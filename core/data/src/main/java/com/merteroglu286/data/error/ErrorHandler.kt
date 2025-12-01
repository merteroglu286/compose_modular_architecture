package com.merteroglu286.data.error

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.merteroglu286.data.response.ApiResponse
import com.merteroglu286.data.response.ErrorResponse

fun getDefaultErrorResponse(): ErrorResponse {
    return ErrorResponse(
        errorCode = "UNKNOWN",
        errorMessage = "Bilinmeyen bir hata oluştu",
        errorFieldList = null
    )
}

fun getErrorResponse(gson: Gson, errorBody: String): ErrorResponse {
    return try {
        // ApiResponse<Unit> olarak parse et
        val type = object : TypeToken<ApiResponse<Unit>>() {}.type
        val apiResponse: ApiResponse<Unit> = gson.fromJson(errorBody, type)

        // error field'ını al
        apiResponse.error ?: ErrorResponse(
            errorCode = apiResponse.code.toString(),
            errorMessage = apiResponse.message,
            errorFieldList = null
        )
    } catch (e: Exception) {
        Log.e("NetworkError", "Error parsing error response", e)
        getDefaultErrorResponse()
    }
}