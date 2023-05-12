package com.anafthdev.a2048.runtime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anafthdev.a2048.data.Direction
import com.anafthdev.a2048.foundation.common.GameEngine
import com.anafthdev.a2048.foundation.theme._2048Theme
import com.anafthdev.a2048.uicomponent.GameBoard

class MainActivity : ComponentActivity() {
	
	private lateinit var gameEngine: GameEngine
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		gameEngine = GameEngine()
		
		setContent {
			_2048Theme {
				val tiles by gameEngine.tiles.collectAsStateWithLifecycle()
				val lastAddedTileIndex by gameEngine.lastAddedTileIndex.collectAsStateWithLifecycle()
				
				LaunchedEffect(gameEngine) {
					gameEngine.init()
				}
				
				Column(
					horizontalAlignment = Alignment.CenterHorizontally,
					modifier = Modifier
						.fillMaxSize()
				) {
					GameBoard(
						tiles = tiles,
						lastAddedTileIndex = lastAddedTileIndex,
						onUp = {
							gameEngine.move(Direction.Up)
						},
						onDown = {
							gameEngine.move(Direction.Down)
						},
						onLeft = {
							gameEngine.move(Direction.Left)
						},
						onRight = {
							gameEngine.move(Direction.Right)
						},
					)
				}
			}
		}
	}
	
}
