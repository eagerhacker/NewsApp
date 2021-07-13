package com.savvynomad.newsapp.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.savvynomad.newsapp.model.Article
import com.savvynomad.newsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel
@Inject constructor(private val repository: NewsRepository) : ViewModel() {

    /* private var _response = MutableLiveData<NewsResponse>()
     val response: LiveData<NewsResponse>
         get() = _response
 */
    /*init {
        getNews()
    }*/

    private val current = MutableLiveData("")
    val news = current.switchMap {
        repository.getNews(it).asLiveData().cachedIn(viewModelScope)
    }

//    fun getNews() = repository.news.cachedIn(viewModelScope)

    private val searchTerm = MutableLiveData("")
    val search = searchTerm.switchMap {
        repository.getSearchResult(it).asLiveData().cachedIn(viewModelScope)
    }

    fun searchArticles(query: String) {
        current.value = query

    }
//
//    fun searchArticles(query: String): Flow<PagingData<Article>> =
//        repository.getSearchResult(query).cachedIn(viewModelScope)

    fun saveArticle(article: Article) =
        viewModelScope.launch {
            repository.saveArticle(article)
        }

    fun deleteArticle(article: Article) =
        viewModelScope.launch {
            repository.deleteArticle(article)
        }

    fun getAllArticles() = repository.getAllArticles().asLiveData()

}