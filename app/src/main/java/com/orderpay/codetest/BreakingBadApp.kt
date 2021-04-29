package com.orderpay.codetest

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.*
import com.orderpay.codetest.background.SyncDatabaseWM
import com.orderpay.codetest.di.apiModule
import com.orderpay.codetest.di.characterRepoModule
import com.orderpay.codetest.di.databaseModule
import com.orderpay.codetest.di.netModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import java.util.concurrent.TimeUnit

class BreakingBadApp : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate() {
        super.onCreate()

        /**
         * Start Koin
         * */
       startKoin{
           androidContext(this@BreakingBadApp)
           androidLogger(Level.DEBUG)
           modules(listOf(characterRepoModule, netModule, apiModule, databaseModule))
       }

        // Start WorkManager
        delayedInit()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun delayedInit(){
        applicationScope.launch {
            Timber.plant(Timber.DebugTree())
            setUpRecurringWork()// work performed by the Worker
        }
    }

    /**
     *  Constraints in which your WM will run
     *  WM background job to fetch new network data daily
     */
    private fun setUpRecurringWork() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(false)
            .build()


        val repeatingRequest= PeriodicWorkRequestBuilder<SyncDatabaseWM>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        Timber.d("WorkManager: Work is scheduled")
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            SyncDatabaseWM.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}