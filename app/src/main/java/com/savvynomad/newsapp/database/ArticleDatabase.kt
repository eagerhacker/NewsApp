package com.savvynomad.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.savvynomad.newsapp.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {

        private var dbINSTANCE: ArticleDatabase? = null

        fun getDatabase(context: Context): ArticleDatabase {
            if(dbINSTANCE == null) {
                dbINSTANCE = Room.databaseBuilder<ArticleDatabase>(
                    context.applicationContext, ArticleDatabase::class.java, "news_database"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return dbINSTANCE!!
        }
    }
}