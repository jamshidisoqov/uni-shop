package io.jamshid.unishop.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.jamshid.unishop.common.Constants.BASE_URL
import io.jamshid.unishop.data.remote.apis.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Created by Isoqov Jamshid on 5/13/2022.

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

    @Provides
    @Singleton
    fun provideDebtApi(retrofit: Retrofit): DebtApi {
        return retrofit.create(DebtApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWorkerApi(retrofit: Retrofit): WorkerApi {
        return retrofit.create(WorkerApi::class.java)
    }
}