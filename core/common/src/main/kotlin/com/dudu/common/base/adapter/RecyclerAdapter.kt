package com.dudu.common.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * 基础抽象adapter
 */
abstract class RecyclerAdapter<M, VB : ViewBinding> : RecyclerView.Adapter<CommonViewHolder<VB>>() {
    // onCreateViewHolder item ViewBinding
    abstract fun viewBinding(parent: ViewGroup): VB
    // 数据绑定
    abstract fun bindViewHolder(holder: CommonViewHolder<VB>, bean: M, position: Int)

    private var onItemClick: ((bean: M, position: Int, binding: VB?) -> Unit)? = null

    fun setOnItemClick(function: (bean: M, position: Int, binding: VB?) -> Unit) {
        this.onItemClick = function
    }

    fun getOnItemClick() = onItemClick

    /**
     * 初步考虑内部List的引用，和外部的传入赋值的不相同
     * 考虑做到，不被外部List变更而影响，尽量使用adapter完成数据更变操作
     */
    private var data: MutableList<M> = ArrayList()

    fun getData() = data

    fun getDataSize() = data.size

    /**
     * 初始化列表数据或增加数据场景
     */
    fun addData(list: List<M>) {
        val index = getDataSize() // 加入前data大小
        data.addAll(list)
        notifyItemRangeInserted(index, list.size) // 加入后根据位置刷新
    }

    fun addData(bean: M) {
        val index = getDataSize()
        data.add(bean)
        notifyItemRangeInserted(index, 1)
    }

    /**
     * 刷新item数据
     */
    fun updateData(position: Int) {
        if (getDataSize() > position && position >= 0) {
            notifyItemChanged(position)
        }
    }

    fun updateData(bean: M, position: Int) {
        if (getDataSize() > position && position >= 0) {
            data[position] = bean
            notifyItemChanged(position)
        }
    }

    /**
     * 删除item
     */
    fun removeData(position: Int) {
        if(getDataSize() > position && position >= 0){
            data.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun removeAllData(){
        if(getDataSize() > 0){
            val index = getDataSize()
            data.clear()
            notifyItemRangeRemoved(0, index)
        }
    }

    /**
     * 重置数据
     */
    fun replaceData(newList: List<M>){
        val index = getDataSize()
        data = arrayListOf<M>().apply {
            this.addAll(newList)
        }
        notifyItemRangeChanged(0, index)
    }

    override fun getItemCount(): Int {
        return getDataSize()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder<VB> {
        return CommonViewHolder(viewBinding(parent))
    }

    override fun onBindViewHolder(holder: CommonViewHolder<VB>, position: Int) {
        val bean = data[position]
        onItemClick?.let { listener ->
            holder.itemView.setOnClickListener {
                listener(bean, position, holder.itemViewBinding)
            }
        }
        bindViewHolder(holder, data[position], position)
    }


}