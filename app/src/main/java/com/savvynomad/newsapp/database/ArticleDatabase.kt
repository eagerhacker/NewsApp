package com.savvynomad.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.savvynomad.newsapp.model.Article
import com.savvynomad.newsapp.model.Converters

@Database(entities = [Article::class, RemoteKeys::class], version = 6, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        private var dbINSTANCE: ArticleDatabase? = null

        fun getDatabase(context: Context): ArticleDatabase {
            if(dbINSTANCE == null) {
                dbINSTANCE = Room.databaseBuilder(
                    context.applicationContext, ArticleDatabase::class.java, "news_database"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return dbINSTANCE!!
        }
    }
}