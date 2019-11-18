package com.egiwon.architecturestudy.base

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_contents_item.view.*

abstract class BaseViewHolder(
    parent: ViewGroup,
    @LayoutRes
    private val resourceId: Int
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(resourceId, parent, false)
) {

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

    val tvTitle: TextView = itemView.tv_title
    val tvDescription: TextView = itemView.tv_description
    var linkUrl: String? = null

}