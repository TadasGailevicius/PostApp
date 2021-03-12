package com.example.postapp.di

import android.content.Context
import androidx.room.Room
import com.example.postapp.data.local.PostsDatabase
import com.example.postapp.data.remote.PostApi
import com.example.postapp.other.Constants.BASE_URL
import com.example.postapp.other.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesPostsDatabase(
            @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, PostsDatabase::class.java , DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideNotesDao(db: PostsDatabase) = db.postDao()

    @Singleton
    @Provides
    fun provideNoteApi() : PostApi {
        val client = OkHttpClient.Builder()
                .build()
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(PostApi::class.java)
    }


}