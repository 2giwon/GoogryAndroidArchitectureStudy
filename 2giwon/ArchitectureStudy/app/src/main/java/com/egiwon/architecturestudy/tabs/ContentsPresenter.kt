package com.egiwon.architecturestudy.tabs

import androidx.lifecycle.MutableLiveData
import com.egiwon.architecturestudy.Tab
import com.egiwon.architecturestudy.base.BasePresenter
import com.egiwon.architecturestudy.data.NaverDataRepository
import io.reactivex.android.schedulers.AndroidSchedulers

class ContentsPresenter(
    private val contentsView: ContentsContract.View,
    private val naverDataRepository: NaverDataRepository
) : BasePresenter(), ContentsContract.Presenter {

    private val queryLiveData = MutableLiveData<String>()
    private val typeLiveData = MutableLiveData<String>()

    private var oneTime = true

    override fun loadContents(
        type: Tab,
        query: String
    ) {
        setQuery(type.name, query)
        requestContents(type, query)
    }

    private fun setQuery(type: String, query: String) {
        queryLiveData.postValue(query)
        typeLiveData.postValue(type)
    }

    private fun requestContents(type: Tab, query: String) {
        if (query.isBlank()) {
            contentsView.showErrorQueryEmpty()
        } else {
            naverDataRepository.loadContents(
                type = type.name,
                query = query
            ).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    contentsView.showLoading()
                }
                .doAfterTerminate {
                    contentsView.hideLoading()
                }
                .subscribe({
                    with(it) {
                        if (contentItems.isNullOrEmpty()) {
                            contentsView.showErrorResultEmpty()
                        } else {
                            contentsView.showQueryResult(this.contentItems)
                        }
                    }

                }, {
                    contentsView.showErrorLoadFail()
                }).addDisposable()

        }
    }

    override fun getCacheContents(type: Tab) {
        naverDataRepository.getCache(type.name)
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterSuccess {
                setQuery(type.name, it.query)
            }
            .subscribe({
                contentsView.showCacheContents(it.contentItems, it.query)
            }, {}).addDisposable()
    }

    override fun listScrolled(
        visibleItemCount: Int,
        lastVisibleItemPosition: Int,
        totalItemCount: Int
    ) {
        if (oneTime && lastVisibleItemPosition + 1 >= totalItemCount) {
            val immutableQuery = lastQueryValue()
            val immutableType = lastTypeValue()

            if (immutableQuery != null && immutableType != null) {
                oneTime = false
                naverDataRepository.requestMore(immutableType, immutableQuery)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterSuccess { setQuery(immutableType, immutableQuery) }
                    .doAfterTerminate { oneTime = true }
                    .subscribe({
                        contentsView.showQueryMoreResult(it.contentItems)
                    }, {
                        contentsView.showErrorLoadFail()
                    }).addDisposable()
            }
        }
    }

    private fun lastQueryValue(): String? = queryLiveData.value

    private fun lastTypeValue(): String? = typeLiveData.value
}