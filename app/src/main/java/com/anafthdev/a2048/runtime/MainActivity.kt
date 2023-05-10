package com.anafthdev.a2048.runtime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.anafthdev.a2048.foundation.common.GameEngine
import com.anafthdev.a2048.foundation.theme._2048Theme
import com.anafthdev.a2048.uicomponent.GameBoard

class MainActivity : ComponentActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			_2048Theme {
				GameBoard(tiles = GameEngine.testBoard)
			}
		}
	}
	
}
