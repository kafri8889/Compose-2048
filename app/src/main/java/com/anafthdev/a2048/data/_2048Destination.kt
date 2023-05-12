package com.anafthdev.a2048.data

sealed class _2048Destination(val route: String) {
	
	class Game {
		object Root: _2048Destination("game/root")
		object Home: _2048Destination("game/home")
	}
	
}