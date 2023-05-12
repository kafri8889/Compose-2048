package com.anafthdev.a2048.runtime.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.anafthdev.a2048.data._2048Destination
import com.anafthdev.a2048.ui.game.GameScreen
import com.anafthdev.a2048.ui.game.GameViewModel

fun NavGraphBuilder.GameNavHost(navController: NavController) {
	navigation(
		startDestination = _2048Destination.Game.Home.route,
		route = _2048Destination.Game.Root.route
	) {
		composable(_2048Destination.Game.Home.route) { backEntry ->
			val viewModel = hiltViewModel<GameViewModel>(backEntry)
			
			GameScreen(
				viewModel = viewModel,
				navController = navController
			)
		}
	}
}
