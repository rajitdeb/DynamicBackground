package com.rajit.dynamicbackground.di

import com.rajit.dynamicbackground.data.network.ResultApi
import com.rajit.dynamicbackground.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideURL() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(url: String): ResultApi =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ResultApi::class.java)

}