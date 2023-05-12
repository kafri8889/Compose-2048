package com.anafthdev.a2048.runtime.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.anafthdev.a2048.data._2048Destination
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun _2048Navigation(darkTheme: Boolean = isSystemInDarkTheme()) {
	
	val systemUiController = rememberSystemUiController()
	val navController = rememberNavController()
	
	SideEffect {
		systemUiController.setSystemBarsColor(
			color = Color.Transparent,
			darkIcons = !darkTheme
		)
	}
	
	Surface {
		NavHost(
			navController = navController,
			startDestination = _2048Destination.Game.Root.route
		) {
			GameNavHost(navController)
		}
	}
	
}
