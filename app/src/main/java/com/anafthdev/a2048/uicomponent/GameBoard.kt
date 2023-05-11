package com.anafthdev.a2048.uicomponent

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.anafthdev.a2048.data.model.Tile
import com.anafthdev.a2048.foundation.common.GameEngine
import com.anafthdev.a2048.foundation.theme._2048Theme

@Preview
@Composable
private fun GameBoardPreview() {
	_2048Theme {
		GameBoard(
			tiles = GameEngine.toTiles(GameEngine.testBoard),
			lastAddedTileIndex = -1
		)
	}
}

@Composable
fun GameBoard(
	tiles: Array<Tile>,
	lastAddedTileIndex: Int,
	onUp: () -> Unit = {},
	onDown: () -> Unit = {},
	onLeft: () -> Unit = {},
	onRight: () -> Unit = {}
) {
	
	Box {
		// Tiles background
		LazyVerticalGrid(
			columns = GridCells.Fixed(4),
			verticalArrangement = Arrangement.spacedBy(8.dp),
			horizontalArrangement = Arrangement.spacedBy(8.dp),
			modifier = Modifier
				.fillMaxWidth(0.9f)
				.aspectRatio(1f / 1f)
				.clip(RoundedCornerShape(8.dp))
				.background(colorDark)
				.padding(8.dp)
				.zIndex(1f)
		) {
			items(GameEngine.toTiles(GameEngine.emptyBoard)) {
				Box(
					modifier = Modifier
						.aspectRatio(1f / 1f)
						.clip(RoundedCornerShape(8.dp))
						.background(colorEmpty.copy(alpha = 0.64f))
				)
			}
		}
		
		LazyVerticalGrid(
			userScrollEnabled = false,
			columns = GridCells.Fixed(4),
			verticalArrangement = Arrangement.spacedBy(8.dp),
			horizontalArrangement = Arrangement.spacedBy(8.dp),
			modifier = Modifier
				.fillMaxWidth(0.9f)
				.aspectRatio(1f / 1f)
				.clip(RoundedCornerShape(8.dp))
				.padding(8.dp)
				.zIndex(2f)
		) {
			itemsIndexed(
				items = tiles,
				key = { _, item: Tile -> item.id }
			) { i, (_, value) ->
				val scaleAnimatable = remember { Animatable(0f) }
				
				if (i == lastAddedTileIndex) {
					LaunchedEffect(Unit) {
						scaleAnimatable.animateTo(
							1f, tween(128)
						)
					}
				}
				
				TileBox(
					value = value,
					modifier = Modifier
						.graphicsLayer {
							if (i == lastAddedTileIndex) {
								scaleX = scaleAnimatable.value
								scaleY = scaleAnimatable.value
							}
						}
				)
			}
		}
		
		GestureDetector(
			onUp = onUp,
			onDown = onDown,
			onLeft = onLeft,
			onRight = onRight,
			modifier = Modifier
				.fillMaxWidth(0.9f)
				.aspectRatio(1f / 1f)
				.zIndex(3f)
		)
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LazyGridItemScope.TileBox(
	value: Int,
	modifier: Modifier = Modifier
) {
	
	val tilesColor = getTilesColor(value)
	
	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier
			.aspectRatio(1f / 1f)
			.clip(RoundedCornerShape(8.dp))
			.background(tilesColor)
			.animateItemPlacement(
				animationSpec = tween(128)
			)
	) {
		Text(
			text = if (value != 0) "$value" else "",
			style = MaterialTheme.typography.titleMedium.copy(
				fontWeight = FontWeight.Bold,
				color = if (value >= 8) colorLight else colorDark
			)
		)
	}
}

private fun getTilesColor(value: Int): Color {
	return when (value) {
		2 -> color2
		4 -> color4
		8 -> color8
		16 -> color16
		32 -> color32
		64 -> color64
		128 -> color128
		256 -> color256
		512 -> color512
		1024 -> color1024
		2048 -> color2048
		else -> Color.Transparent
	}
}

private val colorEmpty: Color = Color(204, 192, 179)
private val color2: Color = Color(238, 228, 218)
private val color4: Color = Color(237, 224, 200)
private val color8: Color = Color(242, 177, 121)
private val color16: Color = Color(245, 149, 99)
private val color32: Color = Color(246, 124, 95)
private val color64: Color = Color(246, 94, 59)
private val color128: Color = Color(237, 207, 114)
private val color256: Color = Color(237, 204, 97)
private val color512: Color = Color(237, 200, 80)
private val color1024: Color = Color(237, 197, 63)
private val color2048: Color = Color(224, 182, 37, 255)

private val colorLight: Color = Color(249, 246, 242)

private val colorDark: Color = Color(119, 110, 101)
