package com.savvynomad.newsapp.database

import androidx.paging.PagingSource
import androidx.room.*
import com.savvynomad.newsapp.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addArticle(article: Article) : Long

    @Delete
    suspend fun removeArticle(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(article: List<Article>)

    @Query("DELETE FROM articles_table")
    suspend fun clearArticles()


    @Query("SELECT * FROM articles_table")
    fun getCacheArticles(): PagingSource<Int, Article>
}