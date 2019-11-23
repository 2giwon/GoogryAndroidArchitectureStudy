package com.egiwon.architecturestudy.tabs.viewholder

import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.egiwon.architecturestudy.base.BaseViewHolder
import com.egiwon.architecturestudy.data.model.Content
import com.egiwon.architecturestudy.ext.fromHtml
import kotlinx.android.synthetic.main.rv_contents_item.view.*

open class ContentViewHolder(
    parent: ViewGroup,
    @LayoutRes
    private val resourceId: Int
) : BaseViewHolder<Content>(
    parent,
    resourceId
) {
    private val tvTitle: TextView = itemView.tv_title
    private val tvDescription: TextView = itemView.tv_description
    private var linkUrl: String? = null

    override fun bind(item: Content) {
        with(item) {
            tvDescription.text = (actor + description).fromHtml()
            tvTitle.text = title?.fromHtml()
            linkUrl = link
        }
    }

    init {
        itemView.setOnClickListener {
            linkUrl?.let { url ->
                ContextCompat.startActivity(
                    it.context,
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse(url)
                    ),
                    null
                )
            }
        }
    }
}