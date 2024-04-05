package com.dudu.app

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dudu.app.databinding.ItemMainBinding
import com.dudu.common.base.adapter.CommonViewHolder
import com.dudu.common.base.adapter.RecyclerAdapter
import javax.inject.Inject

class MainAdapter @Inject constructor() : RecyclerAdapter<MainData, ItemMainBinding>() {
    override fun viewBinding(parent: ViewGroup): ItemMainBinding {
        return ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bindViewHolder(
        holder: CommonViewHolder<ItemMainBinding>,
        bean: MainData,
        position: Int
    ) {
        holder.viewBanding {
            titleTextView.text = bean.title
            contentTitleView.text = bean.content
            cardView.setOnClickListener {
//                onItemClick?.let {
//                    it(bean, position, this)
//                }

                getOnItemClick()?.let {
                    it(bean, position, this)
                }
            }
        }
    }
}