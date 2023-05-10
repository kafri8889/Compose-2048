package com.anafthdev.a2048.runtime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anafthdev.a2048.foundation.common.GameEngine
import com.anafthdev.a2048.foundation.theme._2048Theme
import com.anafthdev.a2048.uicomponent.GameBoard

class MainActivity : ComponentActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			_2048Theme {
				val gameEngine = remember {
					GameEngine()
				}
				
				val tiles by gameEngine.tiles.collectAsStateWithLifecycle()
				
				GameBoard(tiles = tiles)
			}
		}
	}
	
}
