package com.example.inshorts

import android.util.Log
import com.example.inshorts.di.AppContainer

/**
 * Application entry point: creates [AppContainer] once so the app can provide
 * repository and use cases to ViewModels (e.g. via ViewModelProvider.Factory).
 */
class MoviesApp : android.app.Application() {

    val appContainer: AppContainer by lazy { AppContainer(this) }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "App started")
    }

    companion object {
        private const val TAG = "Inshorts/App"
    }
}
