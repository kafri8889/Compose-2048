package com.anafthdev.a2048.data.model

data class Tile(
	val id: Int,
	val value: Int
) {
	fun isEmpty(): Boolean = value == 0
}
