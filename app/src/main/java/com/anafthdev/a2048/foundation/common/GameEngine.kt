package com.anafthdev.a2048.foundation.common

import com.anafthdev.a2048.data.Direction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

class GameEngine {
	
	private val TILE_SIZE = 4
	
	private val _tiles = MutableStateFlow(testBoard)
	val tiles: StateFlow<Array<Int>> = _tiles
	
	fun move(direction: Direction) {
		val newTiles = tiles.value.clone()
		val tileRange = 0 until TILE_SIZE
		
		when (direction) {
			Direction.Up, Direction.Down -> {
				val indexAddition = if (direction.isUp()) -1 else 1
				val iRange = if (direction.isUp()) 1 until TILE_SIZE
				else (TILE_SIZE - 2) downTo 0
				
				for (i in iRange) {
					for (j in 0 until TILE_SIZE) {
						var currentIndex = i * TILE_SIZE + j
						var nextIndex = (i + indexAddition).coerceIn(tileRange) * TILE_SIZE + j
						var nextTile = newTiles[nextIndex]
						
						Timber.i("nextTile ($i, $j): $nextTile")
						Timber.i("current index: $currentIndex")
						
						whileLoop@ while (true) {
							if (nextTile != 0) {
								newTiles[currentIndex] = newTiles[currentIndex]
								Timber.i("break because `nextTile != 0`")
								break@whileLoop
							}

							newTiles[nextIndex] = newTiles[currentIndex]
							newTiles[currentIndex] = 0
							
							val xNextIndex = nextIndex % TILE_SIZE
							val yNextIndex = nextIndex / TILE_SIZE
							
							Timber.i("xNextIndex: $xNextIndex")
							Timber.i("yNextIndex: $yNextIndex")
							
							val newYNextIndex = yNextIndex + indexAddition
							
							Timber.i("next y: $newYNextIndex")
							
							if (newYNextIndex >= TILE_SIZE || newYNextIndex < 0) {
								Timber.i("break because `$newYNextIndex >= TILE_SIZE || $newYNextIndex < 0`")
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