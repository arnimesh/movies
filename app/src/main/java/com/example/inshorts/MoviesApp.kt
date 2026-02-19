package com.example.inshorts

import android.util.Log
import com.example.inshorts.di.AppContainer

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
