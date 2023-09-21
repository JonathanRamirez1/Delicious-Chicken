package com.ramirez.deliciouschicken.di

import android.content.Context
import com.ramirez.deliciouschicken.domain.repository.LoginRepository
import com.ramirez.deliciouschicken.domain.repository.impl.LoginRepositoryImpl
import com.ramirez.deliciouschicken.data.datasource.LoginDataSource
import com.ramirez.deliciouschicken.data.datasource.impl.LoginDataSourceImpl
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
