package com.anafthdev.a2048.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import com.anafthdev.a2048.UserPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
	private val userPreferencesDataStore: DataStore<UserPreferences>
) {
	
	suspend fun setHighScore(score: Int) {
		userPreferencesDataStore.updateData {
			it.copy(highScore = score)
		}
	}
	
	val getUserPreferences: Flow<UserPreferences> = userPreferencesDataStore.data
	
	companion object {
		val corruptionHandler = ReplaceFileCorruptionHandler(
			produceNewData = { UserPreferences() }
		)
	}
}
