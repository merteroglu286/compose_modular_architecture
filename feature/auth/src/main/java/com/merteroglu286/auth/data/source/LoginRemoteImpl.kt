package com.merteroglu286.auth.data.source

import android.util.Log
import com.merteroglu286.auth.data.requests.LoginRequest
import com.merteroglu286.auth.data.service.LoginService
import com.merteroglu286.auth.data.mapper.LoginMapper
import com.merteroglu286.domain.model.User
import com.merteroglu286.data.mapper.toDomain
import com.merteroglu286.data.source.DataSource.Companion.UNKNOWN
import com.merteroglu286.domain.result.Result
import com.merteroglu286.data.source.NetworkDataSource
import com.merteroglu286.domain.model.Error

class LoginRemoteImpl(
    private val networkDataSource: NetworkDataSource<LoginService>,
    private val loginMapper: LoginMapper
) : LoginRemote {
    override suspend fun login(
        username: String,
        password: String
    ): Result<User> {
        return networkDataSource.performRequest(
            request = { login(
                LoginRequest(
                    username = username,
                    password = password
                )
            ) },
            onSuccess = { response, _ ->
                response.data?.let {
                    Result.success(loginMapper.toDomain(it))
                } ?: Result.error(Error(
                    errorCode = UNKNOWN,
                    errorMessage = "Veri bulunamadı",
                    errorFieldList = emptyList()
                ))
            },
            onError = { errorResponse, code ->
                Log.d("mertLog", "ErrorResponse: ${errorResponse.errorMessage}, Code: $code")
                Result.error(errorResponse.toDomain(code))
            }
        )
    }
}