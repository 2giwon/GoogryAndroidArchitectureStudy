package com.egiwon.architecturestudy.tabs

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.egiwon.architecturestudy.R
import com.egiwon.architecturestudy.Tab
import com.egiwon.architecturestudy.base.BaseFragment
import com.egiwon.architecturestudy.data.Content
import com.egiwon.architecturestudy.data.source.NaverRepositoryImpl
import kotlinx.android.synthetic.main.fg_contents.*

class ContentsFragment : BaseFragment(
    R.layout.fg_contents
), ContentsContract.View {


    override val presenter: ContentsContract.Presenter by lazy {

        ContentsPresenter(
            this,
            NaverRepositoryImpl.getInstance()
        )
    }

    private val type: Tab by lazy {
        arguments?.get(ARG_TYPE) as? Tab
            ?: throw IllegalArgumentException()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(rv_contents) {
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )

            setAdapter()
        }

        btn_search.setOnClickListener {
            context?.let {
                requestSearch()
            }
        }

        presenter.contents.observe(
            this,
            Observer {
                (rv_contents.adapter as? ContentsAdapter)?.submitList(it)
            }
        )
    }

    override fun onUpdateUi(contents: PagedList<Content.Item>) {
        (rv_contents.adapter as? ContentsAdapter)?.submitList(contents)
        hideProgress()
    }

    override fun onFail(throwable: Throwable) {
        showToast(getString(R.string.callback_fail))
        hideProgress()
    }

    private fun RecyclerView.setAdapter() {
        try {
            adapter = ContentsAdapter(type)
            setHasFixedSize(true)
        } catch (ignore: Exception) {
        }
    }


    private fun requestSearch() {
        presenter.loadContents(
            type.name,
            et_search.text.toString()
        )
        (rv_contents.adapter as? ContentsAdapter)?.submitList(null)

    }

    private fun showProgress() {
        progress_circular.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress_circular.visibility = View.GONE
    }


    companion object {
        private const val ARG_TYPE = "type"

        fun newInstance(type: Tab) =
            ContentsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_TYPE, type)
                }
            }

    }
}

