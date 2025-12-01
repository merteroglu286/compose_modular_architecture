package com.merteroglu286.data.interceptors

import com.merteroglu286.data.response.ApiResponse
import com.merteroglu286.data.response.TokenResponse
import com.merteroglu286.data.service.SessionService
import com.merteroglu286.data.source.DataSource.Companion.UNAUTHORIZED
import com.merteroglu286.protodatastore.manager.session.SessionDataStoreInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Provider

class AuthenticationInterceptor @Inject constructor(
    private val sessionDataStoreInterface: SessionDataStoreInterface,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val sessionServiceProvider: Provider<SessionService>
) : Interceptor {

    private val mutex = Mutex()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val accessToken =
            runBlocking(coroutineDispatcher) { sessionDataStoreInterface.getAccessToken() }

        val authenticationRequest =
            request.newBuilder().header(AUTHORIZATION_HEADER, "Bearer $accessToken").build()

        val response = chain.proceed(authenticationRequest)

        if (response.code != UNAUTHORIZED) {
            // your access token is valid you can resume hitting APIs
            return response
        }

        // Token is un authorized so try to refresh your access token and refresh token
        val tokenResponse: TokenResponse? = runBlocking {
            mutex.withLock {
                val apiResponse = getUpdatedToken()
                apiResponse.body()?.data.also {
                    sessionDataStoreInterface.setAccessToken(it?.accessToken ?: "")
                    sessionDataStoreInterface.setRefreshToken(it?.refreshToken ?: "")
                }
            }
        }

        return if (tokenResponse?.accessToken != null) {
            response.close()

            // retry the original request with the new token
            val authenticatedRequest =
                request.newBuilder().header(AUTHORIZATION_HEADER, "Bearer ${tokenResponse.accessToken}").build()

            val newResponse = chain.proceed(authenticatedRequest)

            newResponse
        } else {
            response
        }
    }

    private suspend fun getUpdatedToken(): retrofit2.Response<ApiResponse<TokenResponse>> {
        val refreshToken = sessionDataStoreInterface.getRefreshToken()
        return withContext(coroutineDispatcher) {
            sessionServiceProvider.get().getTokens(refreshToken)
        }
    }

}