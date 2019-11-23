package com.egiwon.architecturestudy.tabs

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.egiwon.architecturestudy.Tab
import com.egiwon.architecturestudy.base.BaseViewHolder
import com.egiwon.architecturestudy.data.model.Content
import com.egiwon.architecturestudy.tabs.viewholder.ImageViewHolder
import com.egiwon.architecturestudy.tabs.viewholder.TextViewHolder


class ContentsAdapter(
    private val tab: Tab
) : PagedListAdapter<Content, BaseViewHolder<Content>>(CONTENT_COMPARATOR) {

    private val list = ArrayList<Content>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Content> =
        when (viewType) {
            Tab.BLOG.ordinal, Tab.NEWS.ordinal -> TextViewHolder(
                parent
            )
            Tab.MOVIE.ordinal, Tab.BOOK.ordinal -> ImageViewHolder(
                parent
            )
            else -> throw IllegalArgumentException()
        }


    override fun onBindViewHolder(holder: BaseViewHolder<Content>, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    fun setList(items: List<Content>) {
        with(list) {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = tab.ordinal

    companion object {
        private val CONTENT_COMPARATOR = object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean =
                oldItem == newItem
        }
    }
}