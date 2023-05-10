package com.anafthdev.a2048.foundation.common

import com.anafthdev.a2048.data.Direction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameEngine {
	
	private val _tiles = MutableStateFlow(emptyBoard)
	val tiles: StateFlow<Array<Int>> = _tiles
	
	fun move(direction: Direction) {
	
	}
	
	companion object {
		
		val emptyBoard: Array<Int> = arrayOf(
			0, 0, 0, 0,
			0, 0, 0, 0,
			0, 0, 0, 0,
		)
		
		val testBoard: Array<Int> = arrayOf(
			0, 8, 2048, 0,
			16, 0, 32, 512,
			0, 1024, 64, 4,
			128, 2, 0, 256,
		)
	}
	
}