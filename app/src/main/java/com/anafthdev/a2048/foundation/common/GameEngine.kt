package com.anafthdev.a2048.foundation.common

import com.anafthdev.a2048.data.Direction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class GameEngine {
	
	private val TILE_SIZE = 4
	
	private val _tiles = MutableStateFlow(testBoard)
	val tiles: StateFlow<Array<Int>> = _tiles
	
	// For testing
	init {
		CoroutineScope(Dispatchers.IO).launch {
			delay(3000)
			
			withContext(Dispatchers.Main) {
				move(Direction.Down)
			}
		}
	}
	
	fun move(direction: Direction) {
		val newTiles = tiles.value.clone()
		val tileRange = 0 until TILE_SIZE
		
		when (direction) {
			Direction.Up -> {
			
			}
			Direction.Down -> {
				// `(TILE_SIZE - 2)` => Exclude last index
				for (i in (TILE_SIZE - 2) downTo 0) {
					for (j in 0 until TILE_SIZE) {
						var currentIndex = i * TILE_SIZE + j
						var nextIndex = (i + 1).coerceIn(tileRange) * TILE_SIZE + j
						var nextTile = newTiles[nextIndex]
						
						Timber.i("nextTile ($i, $j): $nextTile")
						
						whileLoop@ while (true) {
							if (nextTile != 0) {
								newTiles[currentIndex] = newTiles[currentIndex]
								Timber.i("break `because nextTile != 0`")
								break@whileLoop
							}

							newTiles[nextIndex] = newTiles[currentIndex]
							newTiles[currentIndex] = 0
							
							val xNextIndex = nextIndex % TILE_SIZE
							val yNextIndex = nextIndex / TILE_SIZE
							
							val newYNextIndex = yNextIndex + 1
							
							Timber.i("next y: $newYNextIndex")
							
							if (newYNextIndex >= TILE_SIZE) {
								Timber.i("break because `newYNextIndex >= TILE_SIZE`")
								break@whileLoop
							}
							
							currentIndex = nextIndex
							nextIndex = newYNextIndex * TILE_SIZE + xNextIndex
							nextTile = newTiles[nextIndex]
							
							Timber.i("updated current index: $currentIndex")
							Timber.i("updated next index: $nextIndex")
							Timber.i("updated next tile: $nextTile")
						}
					}
				}
			}
			Direction.Left -> {
			
			}
			Direction.Right -> {
			
			}
		}
		
		_tiles.update { newTiles }
	}
	
	companion object {
		
		val emptyBoard: Array<Int> = arrayOf(
			0, 0, 0, 0,
			0, 0, 0, 0,
			0, 0, 0, 0,
			0, 0, 0, 0,
		)
		
		val testBoard: Array<Int> = arrayOf(
			16, 8, 2048, 0,
			0, 2, 0, 512,
			0, 1024, 32, 4,
			128, 2, 0, 256,
		)
	}
	
}