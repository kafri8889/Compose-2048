package com.anafthdev.a2048.data

enum class Direction {
	Up,
	Down,
	Left,
	Right;
	
	fun isUp(): Boolean = this == Up
	fun isDown(): Boolean = this == Down
	fun isLeft(): Boolean = this == Left
	fun isRight(): Boolean = this == Right
}