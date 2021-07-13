package com.savvynomad.newsapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.savvynomad.newsapp.adapter.SearchPagingSource
import com.savvynomad.newsapp.api.ApiService
import com.savvynomad.newsapp.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository
@Inject constructor(
    private val apiService: ApiService
) {

}