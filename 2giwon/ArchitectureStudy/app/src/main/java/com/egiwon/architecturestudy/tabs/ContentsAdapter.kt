package com.egiwon.architecturestudy.tabs

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.request.RequestOptions
import com.egiwon.architecturestudy.R
import com.egiwon.architecturestudy.Tab
import com.egiwon.architecturestudy.base.BaseViewHolder
import com.egiwon.architecturestudy.data.Content
import com.egiwon.architecturestudy.ext.fromHtml
import com.egiwon.architecturestudy.ext.loadAsync


class ContentsAdapter(
    private val tab: Tab
) : PagedListAdapter<Content.Item, BaseViewHolder>(CONTENT_COMPARATOR) {

    private val list = ArrayList<Content.Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (viewType) {
            Tab.BLOG.ordinal, Tab.NEWS.ordinal -> TextViewHolder(parent)
            Tab.MOVIE.ordinal, Tab.BOOK.ordinal -> ImageViewHolder(parent)
            else -> throw IllegalArgumentException()
        }


    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        with(holder) {

            tvTitle.text =
                list[position].title.fromHtml()

            tvDescription.text =
                (list[position].actor + list[position].description)
                    .fromHtml()


            linkUrl = list[position].link

            (holder as? ImageViewHolder)?.run {
                loadImage(holder, position)
            }
        }
    }

    private fun loadImage(
        holder: ImageViewHolder,
        position: Int
    ) {
        holder.imageThumbnail.loadAsync(
            list[position].image,
            RequestOptions.placeholderOf(R.mipmap.ic_launcher)
        )
    }

    fun setList(items: List<Content.Item>) {
        with(list) {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = tab.ordinal

    companion object {
        private val CONTENT_COMPARATOR = object : DiffUtil.ItemCallback<Content.Item>() {
            override fun areItemsTheSame(oldItem: Content.Item, newItem: Content.Item): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Content.Item, newItem: Content.Item): Boolean =
                oldItem == newItem
        }
    }
}