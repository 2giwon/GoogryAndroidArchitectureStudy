package com.egiwon.architecturestudy.tabs

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.request.RequestOptions
import com.egiwon.architecturestudy.R
import com.egiwon.architecturestudy.Tab
import com.egiwon.architecturestudy.base.BaseViewHolder
import com.egiwon.architecturestudy.data.model.Content
import com.egiwon.architecturestudy.ext.fromHtml
import com.egiwon.architecturestudy.ext.loadAsync


class ContentsAdapter(
    private val tab: Tab
) : PagedListAdapter<Content, BaseViewHolder>(CONTENT_COMPARATOR) {

    private val list = ArrayList<Content>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (viewType) {
            Tab.BLOG.ordinal, Tab.NEWS.ordinal -> TextViewHolder(parent)
            Tab.MOVIE.ordinal, Tab.BOOK.ordinal -> ImageViewHolder(parent)
            else -> throw IllegalArgumentException()
        }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            with(holder) {

                tvTitle.text =
                    item.title?.fromHtml()

                tvDescription.text =
                    (item.actor + item.description)
                        .fromHtml()


                linkUrl = item.link

                (holder as? ImageViewHolder)?.run {
                    loadImage(holder, item.image)
                }
            }
        }

    }

    private fun loadImage(
        holder: ImageViewHolder,
        imageUrl: String?
    ) {
        holder.imageThumbnail.loadAsync(
            imageUrl,
            RequestOptions.placeholderOf(R.mipmap.ic_launcher)
        )
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