package com.aneke.peter.eyweather.startup

import android.app.Application
import com.aneke.peter.eyweather.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        insertKoin()
    }

    private fun insertKoin(){
        startKoin {
            androidContext(this@App)
            modules(appModules)
        }
    }

}