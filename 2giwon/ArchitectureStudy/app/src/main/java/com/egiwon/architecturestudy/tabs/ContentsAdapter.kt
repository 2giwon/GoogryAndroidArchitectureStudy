package com.egiwon.architecturestudy.tabs

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.egiwon.architecturestudy.Tab
import com.egiwon.architecturestudy.data.source.remote.response.ContentItem

class ContentsAdapter(private val tab: Tab) :
    ListAdapter<ContentItem, ContentViewHolder>(CONTENT_COMPARATOR) {

    private val list = ArrayList<ContentItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder =
        when (Tab.values()[viewType]) {
            Tab.BLOG, Tab.NEWS -> ContentViewHolder(parent)
            Tab.MOVIE, Tab.BOOK -> ImageContentViewHolder(parent)
        }


    override fun onBindViewHolder(holderContent: ContentViewHolder, position: Int) {
        if (getItem(position) != null) {
            holderContent.bind(getItem(position))
        }
    }

    fun addList(items: List<ContentItem>) {
        with(list) {
            addAll(items)
            notifyDataSetChanged()
        }
    }

    fun setList(items: List<ContentItem>) {
        with(list) {
            clear()
            addAll(items)
            submitList(this@ContentsAdapter.list)
        }
    }

    override fun getItemViewType(position: Int): Int = tab.ordinal

    companion object {
        private val CONTENT_COMPARATOR = object : DiffUtil.ItemCallback<ContentItem>() {
            override fun areItemsTheSame(oldItem: ContentItem, newItem: ContentItem): Boolean =
                oldItem.link == newItem.link

            override fun areContentsTheSame(oldItem: ContentItem, newItem: ContentItem): Boolean =
                oldItem == newItem
        }
    }
}

