package com.anafthdev.a2048.ui.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.anafthdev.a2048.UserPreferences
import com.anafthdev.a2048.data.Direction
import com.anafthdev.a2048.foundation.extension.toast
import com.anafthdev.a2048.uicomponent.GameBoard

@Composable
fun GameScreen(
	viewModel: GameViewModel,
	navController: NavController
) {
	
	val context = LocalContext.current
	
	val score by viewModel.score.collectAsStateWithLifecycle()
	val tiles by viewModel.tiles.collectAsStateWithLifecycle()
	val swipes by viewModel.swipes.collectAsStateWithLifecycle()
	val gameOver by viewModel.gameOver.collectAsStateWithLifecycle()
	val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
	val userPreferences by viewModel.userPreferences.collectAsStateWithLifecycle(UserPreferences())
	val lastAddedTileIndex by viewModel.lastAddedTileIndex.collectAsStateWithLifecycle()
	
	LaunchedEffect(gameOver) {
		viewModel.gameOver()
		if (gameOver) {
			"Game over".toast(context)
		}
	}
	
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = Modifier
			.fillMaxSize()
			.systemBarsPadding()
	) {
		Card {
			ConstraintLayout(
				modifier = Modifier
					.fillMaxWidth(0.9f)
					.padding(8.dp)
			) {
				val (
					scoreCol,
					swipesCol,
					highScoreCol
				) = createRefs()
				
				Column(
					horizontalAlignment = Alignment.CenterHorizontally,
					modifier = Modifier
						.constrainAs(scoreCol) {
							start.linkTo(parent.start)
							top.linkTo(parent.top)
							bottom.linkTo(parent.bottom)
							end.linkTo(swipesCol.start)
						}
				) {
					Text(
						text = "Score",
						style = MaterialTheme.typography.bodySmall
					)
					
					Spacer(modifier = Modifier.height(8.dp))
					
					Text(
						text = score.toString(),
						style = MaterialTheme.typography.titleMedium.copy(
							fontWeight = FontWeight.Bold,
							color = MaterialTheme.colorScheme.primary
						)
					)
				}
				
				Column(
					horizontalAlignment = Alignment.CenterHorizontally,
					modifier = Modifier
						.constrainAs(swipesCol) {
							start.linkTo(scoreCol.end)
							top.linkTo(parent.top)
							bottom.linkTo(parent.bottom)
							end.linkTo(highScoreCol.start)
						}
				) {
					Text(
						text = "Swipes",
						style = MaterialTheme.typography.bodySmall
					)
					
					Spacer(modifier = Modifier.height(8.dp))
					
					Text(
						text = swipes.toString(),
						style = MaterialTheme.typography.titleMedium.copy(
							fontWeight = FontWeight.Bold,
							color = MaterialTheme.colorScheme.primary
						)
					)
				}
				
				Column(
					horizontalAlignment = Alignment.CenterHorizontally,
					modifier = Modifier
						.constrainAs(highScoreCol) {
							start.linkTo(swipesCol.end)
							top.linkTo(parent.top)
							bottom.linkTo(parent.bottom)
							end.linkTo(parent.end)
						}
				) {
					Text(
						text = "High Score",
						style = MaterialTheme.typography.bodySmall
					)
					
					Spacer(modifier = Modifier.height(8.dp))
					
					Text(
						text = userPreferences.highScore.toString(),
						style = MaterialTheme.typography.titleMedium.copy(
							fontWeight = FontWeight.Bold,
							color = MaterialTheme.colorScheme.primary
						)
					)
				}
			}
		}
		
		Spacer(modifier = Modifier.height(16.dp))
		
		GameBoard(
			tiles = tiles,
			userGestureEnabled = isPlaying && !gameOver,
			lastAddedTileIndex = lastAddedTileIndex,
			onUp = {
				viewModel.move(Direction.Up)
			},
			onDown = {
				viewModel.move(Direction.Down)
			},
			onLeft = {
				viewModel.move(Direction.Left)
			},
			onRight = {
				viewModel.move(Direction.Right)
			},
			modifier = Modifier
				.fillMaxWidth(0.9f)
		)
		
		Spacer(modifier = Modifier.height(16.dp))
		
		AnimatedVisibility(
			visible = !isPlaying,
			enter = scaleIn(tween(256)),
			exit = scaleOut(tween(256))
		) {
			Button(
				onClick = viewModel::play,
				modifier = Modifier
					.fillMaxWidth(0.9f)
			) {
				Text(
					text = if (gameOver) "New Game" else "Play"
				)
			}
		}
	}
}
