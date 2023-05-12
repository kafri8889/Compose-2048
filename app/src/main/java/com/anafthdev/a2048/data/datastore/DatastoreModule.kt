package com.anafthdev.a2048.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.anafthdev.a2048.UserPreferences
import com.anafthdev.a2048.data.Constant
import com.anafthdev.a2048.data.repository.UserPreferencesRepository
import com.anafthdev.a2048.data.serializer.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatastoreModule {
	
	@Provides
	@Singleton
	fun provideUserPreferencesDatastore(
		@ApplicationContext context: Context
	): DataStore<UserPreferences> {
		return DataStoreFactory.create(
			serializer = UserPreferencesSerializer,
			corruptionHandler = UserPreferencesRepository.corruptionHandler,
			produceFile = { context.dataStoreFile(Constant.USER_PREFERENCES) }
		)
	}
	
}
