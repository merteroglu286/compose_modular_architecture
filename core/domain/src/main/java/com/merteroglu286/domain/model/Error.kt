package com.merteroglu286.domain.model

data class Error(
    val errorCode: Int,
    val errorMessage: String,
    val errorFieldList: List<String>
)