package com.example.postapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.postapp.R
import com.example.postapp.data.local.PostsDatabase
import com.example.postapp.data.remote.JsonplaceholderPostsApi
import com.example.postapp.data.remote.JsonplaceholderUsersApi
import com.example.postapp.data.remote.PostApi
import com.example.postapp.other.Constants.BASE_URL
import com.example.postapp.other.Constants.DATABASE_NAME
import com.example.postapp.other.Constants.ENCRYPTED_SHARED_PREF_NAME
import com.example.postapp.other.Constants.JSONPLACEHOLDER
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
    fun providePostsDao(db: PostsDatabase) = db.postDao()

    @Singleton
    @Provides
    fun providePostApi() : PostApi {
        val client = OkHttpClient.Builder()
                .build()
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(PostApi::class.java)
    }

    @Singleton
    @Provides
    fun provideJPPostApi() : JsonplaceholderPostsApi {
        return Retrofit.Builder()
            .baseUrl(JSONPLACEHOLDER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JsonplaceholderPostsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideJsonplacholderUsersApi() : JsonplaceholderUsersApi {
        return Retrofit.Builder()
            .baseUrl(JSONPLACEHOLDER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JsonplaceholderUsersApi::class.java)
    }

    @Singleton
    @Provides
    fun provideEncryptedSharedPreferences(
            @ApplicationContext context: Context
    ): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
        return EncryptedSharedPreferences.create(
                context,
                ENCRYPTED_SHARED_PREF_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Singleton
    @Provides
    fun provideGlideInstance(
            @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
            RequestOptions()
                    .placeholder(R.drawable.img_avatar)
                    .error(R.drawable.img_avatar)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
    )
}