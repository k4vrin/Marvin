package com.kavrin.marvin

import android.app.Application
import android.provider.UserDictionary.Words.APP_ID
import com.parse.Parse
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MarvinApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(BuildConfig.APP_ID)
                .clientKey(BuildConfig.CLIENT_KEY)
                .server(BuildConfig.SERVER)
                .build()
        )

    }
}