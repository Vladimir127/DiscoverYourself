package com.example.discoveryourself.di

import com.example.discoveryourself.data.retrofit.AlarmService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://51.250.112.213/api/v1/"

@Module
class NetworkModule {
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideProductService(retrofit: Retrofit): AlarmService {
        return retrofit.create(AlarmService::class.java)
    }

    /*@Provides
    fun provideWebDataSource(AlarmService): com.example.onlinestoretest.data.retrofit.WebDataSource {
        return com.example.onlinestoretest.data.retrofit.WebDataSource(productService)
    }*/
}