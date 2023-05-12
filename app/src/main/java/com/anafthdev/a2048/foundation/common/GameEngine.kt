package com.anafthdev.a2048.foundation.common

import com.anafthdev.a2048.data.Direction
import com.anafthdev.a2048.data.model.Tile
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
	
	private val _score = MutableStateFlow(0)
	val score: StateFlow<Int> = _score
	
	private val _swipes = MutableStateFlow(0)
	val swipes: StateFlow<Int> = _swipes
	
	private val _gameOver = MutableStateFlow(false)
	val gameOver: StateFlow<Boolean> = _gameOver
	
	private val _tiles = MutableStateFlow(toTiles(emptyBoard))
	val tiles: StateFlow<Array<Tile>> = _tiles
	
	// used to trigger an animation when a new tile added
	private val _lastAddedTileIndex = MutableStateFlow(-1)
	val lastAddedTileIndex: StateFlow<Int> = _lastAddedTileIndex
	
	private fun checkGameOver(mTiles: Array<Tile>): Boolean {
		val notEmpty = mTiles.map { it.value }.contains(0)
		
		Timber.i("tiles not empty: $notEmpty")
		
		if (notEmpty) return false
		
		for (i in 0 until TILE_SIZE - 1) {
			for (j in 0 until TILE_SIZE) {
				val currentIndex = i * TILE_SIZE + j
				var nextVerticalIndex = (i + 1) * TILE_SIZE + j
				
				val currentTile = mTiles[currentIndex]
				var nextTile = mTiles[nextVerticalIndex]
				
				while (nextTile.value == 0) {
					if (nextVerticalIndex == TILE_SIZE - 1) break
					
					val x = nextVerticalIndex % TILE_SIZE
					val y = nextVerticalIndex / TILE_SIZE
					
					nextVerticalIndex = (y + 1) * TILE_SIZE + x
					nextTile = mTiles[nextVerticalIndex]
				}
				
				val equals = currentTile.value == nextTile.value
				
				Timber.i("$currentIndex == $nextVerticalIndex | $equals")
				
				if (equals) return false
			}
		}
		
		for (i in 0 until TILE_SIZE) {
			for (j in 0 until TILE_SIZE - 1) {
				val currentIndex = i * TILE_SIZE + j
				var nextHorizontalIndex = i * TILE_SIZE + (j + 1)
				
				val currentTile = mTiles[currentIndex]
				var nextTile = mTiles[nextHorizontalIndex]
				
				while (nextTile.value == 0) {
					if (nextHorizontalIndex == TILE_SIZE - 1) break
					
					val x = nextHorizontalIndex % TILE_SIZE
					val y = nextHorizontalIndex / TILE_SIZE
					
					nextHorizontalIndex = y * TILE_SIZE + (x + 1)
					nextTile = mTiles[nextHorizontalIndex]
				}
				
				val equals = currentTile.value == nextTile.value
				
				Timber.i("$currentIndex == $nextHorizontalIndex | $equals")
				
				if (equals) return false
			}
		}
		
		return true
	}
	
	fun reset() {
		_score.update { 0 }
		_tiles.update { toTiles(emptyBoard) }
		addNewTile(tiles.value)
	}
	
	fun move(direction: Direction) {
		Timber.d("Move direction: $direction")
		
		val newTiles = tiles.value.clone()
		val tileRange = 0 until TILE_SIZE
		
		val indexAddition = if (direction.isUp() or direction.isLeft()) -1 else 1
		val dRange = if (direction.isUp() or direction.isLeft()) 1 until TILE_SIZE
		else (TILE_SIZE - 2) downTo 0
		
		when (direction) {
			Direction.Up, Direction.Down -> {
				for (i in dRange) {
					for (j in 0 until TILE_SIZE) {
						var currentIndex = i * TILE_SIZE + j
						var nextIndex = (i + indexAddition).coerceIn(tileRange) * TILE_SIZE + j
						var currentTile = newTiles[currentIndex]
						var nextTile = newTiles[nextIndex]
						
						Timber.i("nextTile ($i, $j): $nextTile")
						Timber.i("current index: $currentIndex")
						
						whileLoop@ while (true) {
							if (currentTile.value == nextTile.value) {
								newTiles[currentIndex] = currentTile.copy(
									value = 0
								)
								
								newTiles[nextIndex] = nextTile.copy(
									value = nextTile.value * 2
								)
								
								_score.update { it + nextTile.value * 2 }
							}
							
							if (nextTile.value != 0) {
								newTiles[currentIndex] = newTiles[currentIndex]
								Timber.i("break because `nextTile != 0`")
								break@whileLoop
							}
							
							newTiles[nextIndex] = newTiles[currentIndex]
							newTiles[currentIndex] = nextTile
							
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
							currentTile = newTiles[currentIndex]
							nextTile = newTiles[nextIndex]
							
							Timber.i("updated current index: $currentIndex")
							Timber.i("updated next index: $nextIndex")
							Timber.i("updated next tile: $nextTile")
						}
					}
				}
			}
			Direction.Left, Direction.Right -> {
				for (i in 0 until TILE_SIZE) {
					for (j in dRange) {
						var currentIndex = i * TILE_SIZE + j
						var nextIndex = i * TILE_SIZE + (j + indexAddition).coerceIn(tileRange)
						var currentTile = newTiles[currentIndex]
						var nextTile = newTiles[nextIndex]
						
						Timber.i("nextTile ($i, $j): $nextTile")
						Timber.i("current index: $currentIndex")
						
						whileLoop@ while (true) {
							if (currentTile.value == nextTile.value) {
								newTiles[currentIndex] = currentTile.copy(
									value = 0
								)
								
								newTiles[nextIndex] = nextTile.copy(
									value = nextTile.value * 2
								)
								
								_score.update { it + nextTile.value * 2 }
							}
							
							if (nextTile.value != 0) {
								newTiles[currentIndex] = newTiles[currentIndex]
								Timber.i("break because `nextTile != 0`")
								break@whileLoop
							}
							
							newTiles[nextIndex] = newTiles[currentIndex]
							newTiles[currentIndex] = nextTile
							
							val xNextIndex = nextIndex % TILE_SIZE
							val yNextIndex = nextIndex / TILE_SIZE
							
							Timber.i("xNextIndex: $xNextIndex")
							Timber.i("yNextIndex: $yNextIndex")
							
							val newXNextIndex = xNextIndex + indexAddition
							
							Timber.i("next x: $newXNextIndex")
							
							if (newXNextIndex >= TILE_SIZE || newXNextIndex < 0) {
								Timber.i("break because `$newXNextIndex >= TILE_SIZE || $newXNextIndex < 0`")
								break@whileLoop
							}
							
							currentIndex = nextIndex
							nextIndex = yNextIndex * TILE_SIZE + newXNextIndex
							currentTile = newTiles[currentIndex]
							nextTile = newTiles[nextIndex]
							
							Timber.i("updated current index: $currentIndex")
							Timber.i("updated next index: $nextIndex")
							Timber.i("updated next tile: $nextTile")
						}
					}
				}
			}
		}
		
		_tiles.update { newTiles }
		_swipes.update { it + 1 }
		
		addNewTile(newTiles)
	}
	
	private fun addNewTile(mTiles: Array<Tile>) {
		val newTiles = mTiles.clone()
		val emptyTileWithIndex = arrayListOf<Pair<Int, Tile>>()
		
		newTiles.forEachIndexed { i, tile ->
			if (tile.isEmpty()) emptyTileWithIndex.add(i to tile)
		}
		
		try {
			val (randomIndex, randomTile) = emptyTileWithIndex.random()
			
			newTiles[randomIndex] = randomTile.copy(
				value = 2
			)
			
			CoroutineScope(Dispatchers.IO).launch {
				// Wait until animation finished
				delay(150)
				
				withContext(Dispatchers.Main) {
					_lastAddedTileIndex.update { randomIndex }
					_tiles.update { newTiles }
					
					val gameOver = checkGameOver(newTiles)
					
					Timber.i("game over: $gameOver")
					
					_gameOver.update { gameOver }
				}
			}
		} catch (e: NoSuchElementException) {
			Timber.e(e)
		}
	}
	
	companion object {
		
		val emptyBoard: Array<Int> = arrayOf(
			0, 0, 0, 0,
			0, 0, 0, 0,
			0, 0, 0, 0,
			0, 0, 0, 0,
		)
		
		val testBoard: Array<Int> = arrayOf(
			2, 4, 2, 4,
			4, 2, 4, 0,
			2, 4, 2, 4,
			4, 2, 4, 0,
		)
		
		fun toTiles(t: Array<Int>): Array<Tile> {
			return t.mapIndexed { i, v -> Tile(i, v) }.also {
				Timber.i("Convert to tile: $it")
			}.toTypedArray()
		}
	}
	
}