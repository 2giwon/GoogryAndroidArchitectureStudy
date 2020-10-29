package com.egiwon.architecturestudy.ui.tabs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.egiwon.architecturestudy.R
import com.egiwon.architecturestudy.base.BaseViewModel
import com.egiwon.architecturestudy.ui.Tab
import com.egiwon.data.NaverDataRepository
import com.egiwon.data.Result
import com.egiwon.data.model.ContentEntity
import com.egiwon.data.model.ContentItem
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ContentViewModel(
    private val tab: Tab,
    private val naverDataRepository: NaverDataRepository
) : BaseViewModel() {

    private val _searchQueryResultList = MutableLiveData<List<ContentItem>>()
    val searchQueryResultList: LiveData<List<ContentItem>> get() = _searchQueryResultList

    private val _isShowLoadingProgressBar = MutableLiveData<Boolean>()
    val isShowLoadingProgressBar: LiveData<Boolean> get() = _isShowLoadingProgressBar

    val searchQuery = MutableLiveData<String>()

    val isResultEmptyError: LiveData<Boolean> =
        Transformations.map(searchQueryResultList) { it.isNullOrEmpty() }

    fun loadContents(): Job = viewModelScope.launch {
        if (searchQuery.value.isNullOrBlank()) {
            mutableErrorTextResId.value = (R.string.error_query_empty)
        } else {
            showLoading()

            val contents: Deferred<Result<ContentEntity>> = async {
                naverDataRepository.getContents(
                    type = tab.name,
                    query = requireNotNull(searchQuery.value)
                )
            }

            when (val resultContents: Result<ContentEntity> = contents.await()) {
                is Result.Success -> {
                    _searchQueryResultList.value = resultContents.data.contentItems
                }
                is Result.Failure -> {
                    mutableErrorTextResId.value = R.string.error_load_fail
                }
            }

            hideLoading()
        }
    }

//    fun loadContents() {
//        if (searchQuery.value.isNullOrBlank()) {
//            mutableErrorTextResId.value = (R.string.error_query_empty)
//        } else {
//            naverDataRepository.getContents(
//                type = tab.name,
//                query = searchQuery.value!!
//            ).observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe {
//                    showLoading()
//                }
//                .doAfterTerminate {
//                    hideLoading()
//                }
//                .subscribe({
//                    _searchQueryResultList.value = it.contentItems
//                }, {
//                    mutableErrorTextResId.value = R.string.error_load_fail
//                }).addDisposable()
//
//        }
//    }

    private fun hideLoading() {
        _isShowLoadingProgressBar.value = false
    }

    private fun showLoading() {
        _isShowLoadingProgressBar.value = true
    }

    fun getCacheContents() = viewModelScope.launch {
        when (val cache = naverDataRepository.getCache(tab.name)) {
            is Result.Success -> {
                _searchQueryResultList.value = cache.data.contentItems
                searchQuery.value = cache.data.query
            }
        }

    }

//    fun getCacheContents() {
//        naverDataRepository.getCache(tab.name)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                _searchQueryResultList.value = it.contentItems
//                searchQuery.value = it.query
//            }, {}).addDisposable()
//    }

    fun loadContentsByHistory(query: String) = viewModelScope.launch {
        when (val contents =
            naverDataRepository.getContentsByHistory(tab.name, query)) {
            is Result.Success -> {
                _searchQueryResultList.value = contents.data.contentItems
                searchQuery.value = contents.data.query
                loadContents()
            }
            is Result.Failure -> {
                mutableErrorTextResId.value = R.string.error_load_fail
            }
        }
    }

//    fun loadContentsByHistory(query: String) {
//        naverDataRepository.getContentsByHistory(tab.name, query)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                _searchQueryResultList.value = it.contentItems
//                searchQuery.value = it.query
//                loadContents()
//            }, {
//                mutableErrorTextResId.value = R.string.error_load_fail
//            }).addDisposable()
//    }

}