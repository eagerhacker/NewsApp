package com.savvynomad.newsapp.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.savvynomad.newsapp.api.ApiService
import com.savvynomad.newsapp.utils.Constants
import com.savvynomad.newsapp.model.Article

const val FIRST_PAGE = 1

class NewsPagingSource(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val currentPage = params.key ?: FIRST_PAGE

        return try {
            val response = apiService.getNews(
                q = query,
                language = Constants.language,
                category = "general",
                pageSize = params.loadSize,
                page = currentPage
            )
//            val data = mutableListOf<Article>()
//            data.addAll(response.body()?.articles ?: emptyList())

            val articles = response.articles
            LoadResult.Page(
                data = articles,
                prevKey = if (currentPage == FIRST_PAGE) null else currentPage - 1,
                nextKey = if (articles.isEmpty()) null else currentPage + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}