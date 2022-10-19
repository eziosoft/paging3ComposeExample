package com.eziosoft

import android.app.Application
import com.eziosoft.parisinnumbers.data.apiModule
import com.eziosoft.parisinnumbers.data.local.room.roomModule
import com.eziosoft.parisinnumbers.navigation.navigationModule
import com.eziosoft.parisinnumbers.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                roomModule,
                apiModule,
                presentationModule,
                navigationModule
            )
        }
    }
}
