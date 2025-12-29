package com.merteroglu286.common.di

import android.content.Context
import com.merteroglu286.common.image.ImageFileManager
import com.merteroglu286.common.permission.PermissionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ManagerModule {

    @Singleton
    @Provides
    fun providePermissionManager(@ApplicationContext context: Context): PermissionManager {
        return PermissionManager(context)
    }

    @Singleton
    @Provides
    fun provideImagePickerManager(
        @ApplicationContext context: Context
    ): ImageFileManager {
        return ImageFileManager(context)
    }

}