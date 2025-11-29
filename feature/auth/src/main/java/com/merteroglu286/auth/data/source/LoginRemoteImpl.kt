package com.merteroglu286.auth.data.source

import com.merteroglu286.auth.data.requests.LoginRequest
import com.merteroglu286.auth.data.service.LoginService
import com.merteroglu286.auth.data.mapper.LoginMapper
import com.merteroglu286.domain.model.User
import com.merteroglu286.data.mapper.toDomain
import com.merteroglu286.domain.result.Result
import com.merteroglu286.data.source.NetworkDataSource

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
            onSuccess = { response, _ -> Result.success(loginMapper.toDomain(response)) },
            onError = { errorResponse, code -> Result.error(errorResponse.toDomain(code)) }
        )
    }
}