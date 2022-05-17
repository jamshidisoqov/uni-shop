package io.jamshid.unishop.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.jamshid.unishop.common.Constants.BASE_URL
import io.jamshid.unishop.data.remote.apis.CategoryApi
import io.jamshid.unishop.data.remote.apis.ClientApi
import io.jamshid.unishop.data.remote.apis.ProductApi
import io.jamshid.unishop.data.remote.apis.SaleApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// Created by Usmon Abdurakhmanv on 5/13/2022.

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }




    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()

        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.build()
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideSaleApi(retrofit: Retrofit): SaleApi {
        return retrofit.create(SaleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    fun provideClientApi(retrofit: Retrofit): ClientApi {
        return retrofit.create(ClientApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryApi(retrofit: Retrofit): CategoryApi {
        return retrofit.create(CategoryApi::class.java)
    }

}