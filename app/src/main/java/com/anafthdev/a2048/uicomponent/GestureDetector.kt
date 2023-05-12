package com.anafthdev.a2048.uicomponent

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import timber.log.Timber
import kotlin.math.abs

@Composable
fun GestureDetector(
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	onUp: () -> Unit,
	onDown: () -> Unit,
	onLeft: () -> Unit,
	onRight: () -> Unit,
) {
	
	val mEnabled by rememberUpdatedState(newValue = enabled)
	var direction by remember { mutableStateOf(0) }
	
	Box(
		modifier = modifier
			.pointerInput(Unit) {
				detectDragGestures(
					onDrag = { change, dragAmount ->
						change.consume()
						
						val (x,y) = dragAmount
						Timber.i("drag amon: $dragAmount")
						
						if (abs(x) > abs(y)) {
							// Focus on horizontal
							
							when {
								x > 0 -> {
									/* right */
									direction = 0
								}
								x < 0 -> {
									/* left */
									direction = 1
								}
							}
						} else {
							// Focus on vertical
							
							when {
								y > 0 -> {
									/* down */
									direction = 2
								}
								y < 0 -> {
									/* up */
									direction = 3
								}
							}
						}
					},
					onDragEnd = {
						if (mEnabled) {
							when (direction) {
								0 -> onRight()
								1 -> onLeft()
								2 -> onDown()
								3 -> onUp()
							}
						}
					}
				)
			}
	)
}
