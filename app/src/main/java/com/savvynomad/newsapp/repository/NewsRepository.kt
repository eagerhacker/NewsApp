package com.savvynomad.newsapp.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.savvynomad.newsapp.adapter.NewsRemoteMediator
import com.savvynomad.newsapp.api.ApiService
import com.savvynomad.newsapp.database.ArticleDatabase
import com.savvynomad.newsapp.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository
@Inject constructor(
    private val apiService: ApiService,
    private val database: ArticleDatabase
) {

    /*fun getNews(query: String): Flow<PagingData<Article>> =
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
*/


    @ExperimentalPagingApi
    fun getNewsStream(query: String): Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false),
            remoteMediator = NewsRemoteMediator(
                query = query,
                service = apiService,
                repoDatabase = database
            ),
            pagingSourceFactory = {
                database.articleDao().getCacheArticles()
            }
        ).flow

    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }

}