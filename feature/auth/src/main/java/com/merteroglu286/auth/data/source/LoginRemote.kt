package com.merteroglu286.auth.data.source

import com.merteroglu286.domain.model.User
import com.merteroglu286.domain.result.Result

interface LoginRemote {
    suspend fun login(username: String, password: String): Result<User>
}