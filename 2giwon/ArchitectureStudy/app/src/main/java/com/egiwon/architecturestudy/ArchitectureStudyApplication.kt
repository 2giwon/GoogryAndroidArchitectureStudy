package com.egiwon.architecturestudy

import android.app.Application
import com.egiwon.architecturestudy.di.viewModule
import com.egiwon.data.di.dataModule
import com.egiwon.local.di.localDataSourceModule
import com.egiwon.remote.di.networkModule
import com.egiwon.remote.di.remoteDataSourceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

class ArchitectureStudyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            logger(if (BuildConfig.DEBUG) AndroidLogger() else EmptyLogger())
            androidContext(applicationContext)
            modules(
                listOf(
                    viewModule,
                    dataModule,
                    remoteDataSourceModule,
                    localDataSourceModule,
                    networkModule
                )
            )
        }
    }
}