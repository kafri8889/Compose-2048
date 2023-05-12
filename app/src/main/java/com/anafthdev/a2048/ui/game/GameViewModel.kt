package com.anafthdev.a2048.ui.game

import androidx.lifecycle.ViewModel
import com.anafthdev.a2048.data.Direction
import com.anafthdev.a2048.data.repository.UserPreferencesRepository
import com.anafthdev.a2048.foundation.common.GameEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
	private val userPreferencesRepository: UserPreferencesRepository
): ViewModel () {
	
	private val gameEngine = GameEngine()
	
	val score = gameEngine.score
	val tiles = gameEngine.tiles
	val swipes = gameEngine.swipes
	val lastAddedTileIndex = gameEngine.lastAddedTileIndex
	val userPreferences = userPreferencesRepository.getUserPreferences
	
	fun play() {
	
	}
	
	fun move(direction: Direction) {
		gameEngine.move(direction)
	}
	
}