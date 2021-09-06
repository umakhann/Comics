package com.example.comics.di

import android.app.Application
import androidx.room.Room
import com.example.comics.source.api.Api
import com.example.comics.source.database.BookDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() =
        Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


//    https://www.googleapis.com/books/v1/volumes?q=harry+potter&callback=handleResponse

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit) =
        retrofit.create(Api::class.java)


    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: BookDatabase.Callback
    ) = Room.databaseBuilder(app, BookDatabase::class.java, "books")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun provideDao(db: BookDatabase) = db.dao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())


}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope