package com.savvynomad.newsapp.di

import android.app.Application
import androidx.room.Room
import com.savvynomad.newsapp.api.ApiService
import com.savvynomad.newsapp.database.ArticleDao
import com.savvynomad.newsapp.database.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = "https://newsapi.org/v2/"

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideApiService(baseUrl: String, okHttpClient: OkHttpClient): ApiService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(context: Application): ArticleDatabase {
        return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            "news_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDao =
        articleDatabase.articleDao()

}


