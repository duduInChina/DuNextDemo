package com.dudu.weather.ui.place

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dudu.common.base.adapter.CommonViewHolder
import com.dudu.common.base.adapter.RecyclerAdapter
import com.dudu.weather.bean.PlaceResponse
import com.dudu.weather.databinding.ItemPlaceBinding
import javax.inject.Inject

class PlaceAdapter @Inject constructor(): RecyclerAdapter<PlaceResponse.Place, ItemPlaceBinding>() {
    override fun viewBinding(parent: ViewGroup): ItemPlaceBinding {
        return ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bindViewHolder(
        holder: CommonViewHolder<ItemPlaceBinding>,
        bean: PlaceResponse.Place,
        position: Int
    ) {
        holder.viewBanding {
            placeName.text = bean.name
            placeAddress.text = bean.address
        }
    }
}