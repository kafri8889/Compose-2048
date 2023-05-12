package com.anafthdev.a2048.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anafthdev.a2048.data.Direction
import com.anafthdev.a2048.data.repository.UserPreferencesRepository
import com.anafthdev.a2048.foundation.common.GameEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
	private val userPreferencesRepository: UserPreferencesRepository
): ViewModel () {
	
	private val gameEngine = GameEngine()
	
	private val _isPlaying = MutableStateFlow(false)
	val isPlaying: StateFlow<Boolean> = _isPlaying
	
	val score = gameEngine.score
	val tiles = gameEngine.tiles
	val swipes = gameEngine.swipes
	val gameOver = gameEngine.gameOver
	val lastAddedTileIndex = gameEngine.lastAddedTileIndex
	val userPreferences = userPreferencesRepository.getUserPreferences
	
	fun gameOver() {
		_isPlaying.update { false }
		
		viewModelScope.launch {
			userPreferences.firstOrNull()?.let {
				if (it.highScore < score.value) {
					userPreferencesRepository.setHighScore(score.value)
				}
			}
		}
	}
	
	fun play() {
		_isPlaying.update { true }
		
		gameEngine.reset()
	}
	
	fun move(direction: Direction) {
		gameEngine.move(direction)
	}
	
}