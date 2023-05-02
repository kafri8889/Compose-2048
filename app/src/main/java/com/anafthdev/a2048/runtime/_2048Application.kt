package com.anafthdev.a2048.runtime

import android.app.Application
import com.anafthdev.a2048.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class _2048Application: Application() {
	
	override fun onCreate() {
		super.onCreate()
		
		if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
	}
}