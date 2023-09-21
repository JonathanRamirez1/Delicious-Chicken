package com.ramirez.deliciouschicken

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeliciousModule {

    @Singleton
    @Provides
    fun provideDataSource(@ApplicationContext context: Context): LoginDataSource = LoginDataSourceImpl(context)

    @Singleton
    @Provides
    fun provideRepository(dataSource: LoginDataSource): LoginRepository = LoginRepositoryImpl(dataSource)
}
