package io.jamshid.unishop.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.jamshid.unishop.BuildConfig
import timber.log.Timber

// Created by Usmon Abdurakhmanv on 5/13/2022.

@HiltAndroidApp
class UniShopApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree());
        }
    }
}