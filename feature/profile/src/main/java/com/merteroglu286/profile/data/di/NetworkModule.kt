package com.merteroglu286.profile.data.di

import com.google.gson.Gson
import com.merteroglu286.data.constants.DISPATCHER_DEFAULT_TAG
import com.merteroglu286.data.constants.USER_ID_TAG
import com.merteroglu286.data.factory.ServiceFactory
import com.merteroglu286.data.source.NetworkDataSource
import com.merteroglu286.profile.data.mapper.UploadImageMapper
import com.merteroglu286.profile.data.mapper.UploadImageMapperImpl
import com.merteroglu286.profile.data.service.UploadImageService
import com.merteroglu286.profile.data.source.UploadImageRemote
import com.merteroglu286.profile.data.source.UploadImageRemoteImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideUploadImageService(serviceFactory: ServiceFactory): UploadImageService {
        return serviceFactory.create(UploadImageService::class.java)
    }

    @Provides
    @Singleton
    fun provideUploadImageNetworkDataSource(
        uploadImageService: UploadImageService,
        gson: Gson
    ): NetworkDataSource<UploadImageService> {
        return NetworkDataSource(uploadImageService, gson)
    }

    @Provides
    @Singleton
    fun provideUploadImageMapper(
        @Named(DISPATCHER_DEFAULT_TAG) coroutineDispatcher: CoroutineDispatcher
    ): UploadImageMapper {
        return UploadImageMapperImpl(coroutineDispatcher)
    }

    @Provides
    @Singleton
    fun provideUploadImageRemote(
        networkDataSource: NetworkDataSource<UploadImageService>,
        uploadImageMapper: UploadImageMapper
    ): UploadImageRemote {
        return UploadImageRemoteImpl(networkDataSource, uploadImageMapper)
    }

}