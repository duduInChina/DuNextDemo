package com.dudu.common.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * ViewHolder封装类
 */
class CommonViewHolder<VB : ViewBinding>(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    var itemViewBinding: VB? = null

    var tag: Any? = null

    constructor(itemViewBinding: VB) : this(itemViewBinding.root) {
        this.itemViewBinding = itemViewBinding
    }

    fun viewBanding(block: VB.() -> Unit) {
        itemViewBinding?.run {
            block(this)
        }
    }
}