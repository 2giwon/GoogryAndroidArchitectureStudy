package com.egiwon.architecturestudy.tabs

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.egiwon.architecturestudy.Tab
import com.egiwon.architecturestudy.data.Content


class ContentsAdapter(
    private val tab: Tab
) : RecyclerView.Adapter<ContentViewHolder>() {

    private val list = ArrayList<Content.Item>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContentViewHolder =
        when (viewType) {
            Tab.BLOG.ordinal, Tab.NEWS.ordinal -> TextContentViewHolder(parent)
            Tab.MOVIE.ordinal, Tab.BOOK.ordinal -> ImageContentViewHolder(parent)
            else -> throw IllegalArgumentException()
        }


    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holderContent: ContentViewHolder, position: Int) =
        holderContent.bind(list[position])


    fun setList(items: List<Content.Item>) {
        with(list) {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = tab.ordinal
}