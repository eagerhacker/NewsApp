package com.savvynomad.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import com.savvynomad.newsapp.repository.NewsRepository
import javax.inject.Inject

class DetailViewModel
@Inject constructor(repository: NewsRepository) : ViewModel() {

}