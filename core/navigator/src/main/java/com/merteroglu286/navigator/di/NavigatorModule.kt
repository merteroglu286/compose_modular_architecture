package com.merteroglu286.navigator.di

import com.merteroglu286.navigator.core.AppNavigator
import com.merteroglu286.navigator.core.AppNavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigatorModule {
    @Provides
    @Singleton
    fun navigator(): AppNavigator {
        return AppNavigatorImpl()
    }
}