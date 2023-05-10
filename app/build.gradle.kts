plugins {
	id("idea")
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("dagger.hilt.android.plugin")
	id("kotlin-kapt")
	id("kotlin-parcelize")
	id("com.squareup.wire")
	id("com.google.devtools.ksp")
}

android {
	namespace = "com.anafthdev.a2048"
	compileSdk = 33
	
	defaultConfig {
		applicationId = "com.anafthdev.a2048"
		minSdk = 24
		targetSdk = 33
		versionCode = 1
		versionName = "1.0.0"
		
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
	}
	
	buildTypes {
		release {
			isMinifyEnabled = true
			isShrinkResources = true
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
			kotlinOptions {
				freeCompilerArgs += listOf(
					"-Xopt-in=kotlin.RequiresOptIn",
					"-Xjvm-default=all"
				)
			}
		}
		debug {
			isMinifyEnabled = false
			isDebuggable = true
			kotlinOptions {
				freeCompilerArgs += listOf(
					"-Xopt-in=kotlin.RequiresOptIn",
					"-Xjvm-default=all"
				)
			}
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.4.4"
	}
	packagingOptions {
		resources {
			excludes.add("/META-INF/{AL2.0,LGPL2.1}")
		}
	}
}

wire {
	kotlin {
		android = true
	}
}

dependencies {
	
	val kotlin_version by extra("1.8.10")
	val compose_version by extra("1.5.0-alpha03")
	val lifecycle_version by extra("2.6.1")
	val accompanist_version by extra("0.31.0-alpha")
	
	val media3_version = "1.0.1"
	
	implementation("androidx.core:core-ktx:1.10.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("androidx.activity:activity-compose:1.7.1")
	implementation("androidx.compose.ui:ui:${extra["compose_version"]}")
	implementation("androidx.compose.ui:ui-tooling-preview:${extra["compose_version"]}")
	implementation("androidx.compose.foundation:foundation:${extra["compose_version"]}")
	implementation("androidx.compose.ui:ui-util:${extra["compose_version"]}")
	implementation("androidx.compose.runtime:runtime-livedata:${extra["compose_version"]}")
	implementation("androidx.compose.animation:animation:${extra["compose_version"]}")
	implementation("androidx.navigation:navigation-compose:2.5.3")
	implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
	
	// Constraint layout
	implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha09")

//	// Ads
//	implementation("com.google.android.gms:play-services-ads:22.0.0")
	
	// Media3
//	implementation("androidx.media3:media3-exoplayer:$media3_version")
	
	// Material Design
	implementation("com.google.android.material:material:1.9.0")
	implementation("androidx.compose.material:material:1.4.3")
	implementation("androidx.compose.material:material-icons-extended:1.4.3")
	implementation("androidx.compose.material3:material3:1.1.0-rc01")
	implementation("androidx.compose.material3:material3-window-size-class:1.0.1")
	
	// Large screen support
	implementation("androidx.window:window:1.0.0")
	implementation("androidx.window:window-java:1.0.0")
	
	// Datastore
	implementation("androidx.datastore:datastore:1.0.0")
	implementation("androidx.datastore:datastore-preferences:1.0.0")
	implementation("androidx.datastore:datastore-core:1.0.0")
//    implementation("com.google.protobuf:protobuf-javalite:3.18.0")
//    implementation("com.google.protobuf:protobuf-kotlin:3.19.1")
//    implementation("com.google.protobuf:protobuf-kotlin-lite:3.19.1")
	
	// Lifecycle
	implementation("androidx.lifecycle:lifecycle-runtime-compose:${extra["lifecycle_version"]}")
	implementation("androidx.lifecycle:lifecycle-livedata-ktx:${extra["lifecycle_version"]}")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:${extra["lifecycle_version"]}")
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${extra["lifecycle_version"]}")
	implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
	implementation("androidx.core:core-ktx:1.10.0")
	kapt("androidx.lifecycle:lifecycle-common-java8:${extra["lifecycle_version"]}")
	
	// Dependency Injection
	implementation("com.google.dagger:hilt-android:2.44.2")
	kapt("androidx.hilt:hilt-compiler:1.0.0")
	kapt("com.google.dagger:hilt-compiler:2.44.2")
	kapt("com.google.dagger:hilt-android-compiler:2.44.2")
	
	// Room
	implementation("androidx.room:room-runtime:2.5.1")
	implementation("androidx.room:room-ktx:2.5.1")
	kapt("androidx.room:room-compiler:2.5.1")
	
	// Accompanist
	implementation("com.google.accompanist:accompanist-pager:${extra["accompanist_version"]}")
	implementation("com.google.accompanist:accompanist-adaptive:${extra["accompanist_version"]}")
	implementation("com.google.accompanist:accompanist-navigation-material:${extra["accompanist_version"]}")
	implementation("com.google.accompanist:accompanist-navigation-animation:${extra["accompanist_version"]}")
	implementation("com.google.accompanist:accompanist-flowlayout:${extra["accompanist_version"]}")
	implementation("com.google.accompanist:accompanist-permissions:${extra["accompanist_version"]}")
	implementation("com.google.accompanist:accompanist-systemuicontroller:${extra["accompanist_version"]}")
	
	// Other
	implementation("com.google.code.gson:gson:2.9.0")
	implementation("com.jakewharton.timber:timber:5.0.1")
	implementation("com.squareup.wire:wire-runtime:4.4.3")
	implementation("com.airbnb.android:lottie-compose:6.0.0")
	
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	androidTestImplementation("androidx.compose.ui:ui-test-junit4:${extra["compose_version"]}")
	androidTestImplementation("com.google.dagger:hilt-android-testing:2.44.2")
	debugImplementation("androidx.compose.ui:ui-tooling:${extra["compose_version"]}")
	debugImplementation("androidx.compose.ui:ui-test-manifest:${extra["compose_version"]}")
	kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.44.2")
}