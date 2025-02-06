package com.fuchsia.ebookapp.di

import com.fuchsia.ebookapp.repo.Repo
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
        fun provideFirebaseRealTimeDatabase(): FirebaseDatabase {
            return FirebaseDatabase.getInstance()
    }

    @Singleton
    @Provides
    fun provideRepo(firebaseDatabase: FirebaseDatabase): Repo {
        return Repo(firebaseDatabase)
    }

}