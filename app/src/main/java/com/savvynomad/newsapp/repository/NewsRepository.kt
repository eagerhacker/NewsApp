package com.savvynomad.newsapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.savvynomad.newsapp.adapter.NewsPagingSource
import com.savvynomad.newsapp.adapter.SearchPagingSource
import com.savvynomad.newsapp.api.ApiService
import com.savvynomad.newsapp.database.ArticleDao
import com.savvynomad.newsapp.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository
@Inject constructor(
    private val apiService: ApiService,
    private val articleDao: ArticleDao
) {

    fun getNews(query: String): Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),

            pagingSourceFactory = {
                NewsPagingSource(apiService, query)
            }
        ).flow

    fun getSearchResult(query: String): Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 20,
                enablePlaceholders = false,
                maxSize = 100
            ),

            pagingSourceFactory = {
                SearchPagingSource(apiService, query)
            }
        ).flow

    suspend fun saveArticle(article: Article) = articleDao.addArticle(article)
    suspend fun deleteArticle(article: Article) = articleDao.removeArticle(article)
    fun getAllArticles() = articleDao.getAllArticles()
}