package com.app.bamboo.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        RepositoriesModule::class,
        DaoModule::class,
        NotifyModule::class,
    ]
)
@InstallIn(SingletonComponent::class)
object AppModule