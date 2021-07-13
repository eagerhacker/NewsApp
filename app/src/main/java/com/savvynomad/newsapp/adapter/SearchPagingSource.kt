package com.savvynomad.newsapp.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.savvynomad.newsapp.api.ApiService
import com.savvynomad.newsapp.helper.Constants
import com.savvynomad.newsapp.model.Article

//const val FIRST_PAGE = 1
class SearchPagingSource(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val currentPage = params.key ?: FIRST_PAGE

        return try {
            val response = apiService.searchNews(
                query = query,
                language = Constants.language,
                pageSize = params.loadSize,
                page = currentPage
            ).body()

            LoadResult.Page(
                data = response?.articles ?: emptyList(),
                prevKey = if (currentPage == FIRST_PAGE) null else currentPage - 1,
                nextKey = if (response?.articles == null) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}