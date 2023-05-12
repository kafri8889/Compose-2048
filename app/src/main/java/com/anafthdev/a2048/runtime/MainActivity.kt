package com.anafthdev.a2048.runtime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.anafthdev.a2048.foundation.theme._2048Theme
import com.anafthdev.a2048.runtime.navigation._2048Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		WindowCompat.setDecorFitsSystemWindows(window, false)
		
		setContent {
			_2048Theme {
				_2048Navigation()
			}
		}
	}
	
}
