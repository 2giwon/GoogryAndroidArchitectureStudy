package com.egiwon.architecturestudy.tabs.viewholder

import android.view.ViewGroup
import android.widget.ImageView
import com.egiwon.architecturestudy.R
import com.egiwon.architecturestudy.data.model.Content
import com.egiwon.architecturestudy.ext.loadAsync
import kotlinx.android.synthetic.main.rv_contents_item.view.*

class ImageViewHolder(
    parent: ViewGroup
) : ContentViewHolder(
    parent,
    R.layout.rv_contents_item
) {
    private val imageThumbnail: ImageView = itemView.iv_thumbnail

    override fun bind(item: Content) {
        super.bind(item)
        item.run {
            imageThumbnail.loadAsync(image) {
                placeholder(R.mipmap.ic_launcher)
            }
        }
    }
}