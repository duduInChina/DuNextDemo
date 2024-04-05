package com.dudu.common.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewbinding.ViewBinding
import com.dudu.common.base.adapter.RecyclerAdapterX.ItemViewType.*
import com.dudu.common.databinding.LayoutRecyclerEmptyBinding

/**
 * 相比较RecyclerAdapter，加入了HeadView、FooterView、EmptyView的处理
 */
abstract class RecyclerAdapterX<M, VB : ViewBinding> :
    RecyclerView.Adapter<CommonViewHolder<VB>>() {
    // onCreateViewHolder item ViewBinding
    abstract fun viewBinding(parent: ViewGroup): VB

    // 数据绑定
    abstract fun bindViewHolder(holder: CommonViewHolder<VB>, bean: M, position: Int)

    /**
     * 初步考虑内部List的引用，和外部的传入赋值的不相同
     * 考虑做到，不被外部List变更而影响，尽量使用adapter完成数据更变操作
     */
    private var data: MutableList<M> = ArrayList()

    fun getData() = data

    fun getDataSize() = data.size

    private var onItemClick: ((bean: M, position: Int, binding: VB?) -> Unit)? = null

    fun setOnItemClick(function: (bean: M, position: Int, binding: VB?) -> Unit) {
        this.onItemClick = function
    }

    lateinit var recyclerView: RecyclerView
        private set

    /**
     * 头部
     */
    var headerView: View? = null

    /**
     * 底部
     */
    var footerView: View? = null

    /**
     * 是否展示空页面
     */
    private var showEmpty: Boolean = false

    var emptyView: View? = null

    /**
     * 是否是第一次加载数据
     */
    private var firstLoad = true

    inline fun <reified EVB : ViewBinding> emptyView(empty: EVB.() -> Unit) {
        val viewBindingClass = EVB::class.java
        val method = viewBindingClass.getDeclaredMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        val emptyViewBinding = method.invoke(
            this,
            LayoutInflater.from(recyclerView.context),
            recyclerView,
            false
        ) as EVB
        emptyView = emptyViewBinding.root
        empty(emptyViewBinding)
    }

    fun addHeaderView(@LayoutRes layoutId: Int) {
        headerView =
            LayoutInflater.from(recyclerView.context).inflate(layoutId, recyclerView, false)
    }

    inline fun <reified HVB : ViewBinding> headerView(header: HVB.() -> Unit) {
        val viewBindingClass = HVB::class.java
        val method = viewBindingClass.getDeclaredMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        val headerViewBinding = method.invoke(
            this,
            LayoutInflater.from(recyclerView.context),
            recyclerView,
            false
        ) as HVB
        headerView = headerViewBinding.root
        header(headerViewBinding)
    }

    inline fun <reified FVB : ViewBinding> footerView(footer: FVB.() -> Unit) {
        val viewBindingClass = FVB::class.java
        val method = viewBindingClass.getDeclaredMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        val footerViewBinding = method.invoke(
            this,
            LayoutInflater.from(recyclerView.context),
            recyclerView,
            false
        ) as FVB
        footerView = footerViewBinding.root
        footer(footerViewBinding)
    }

    fun addFooterView(@LayoutRes layoutId: Int) {
        footerView =
            LayoutInflater.from(recyclerView.context).inflate(layoutId, recyclerView, false)
//        notifyDataSetChanged()
    }

    /**
     * 初始化列表数据或增加数据场景
     */
    fun addData(list: List<M>) {
        val index = itemCount // 加入前data大小
        data.addAll(list)
        notifyItemRangeInserted(index, list.size) // 加入后根据位置刷新
    }

    fun addData(bean: M) {
        val index = itemCount
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
        if (getDataSize() > position && position >= 0) {
            val index = headerView?.let { position - 1 } ?: position
            data.removeAt(index)
            notifyItemRemoved(position)
        }
    }

    fun removeAllData() {
        if (getDataSize() > 0) {
            val index = itemCount
            data.clear()
            notifyItemRangeRemoved(0, index)
        }
    }

    /**
     * 重置数据
     */
    fun replaceData(newList: List<M>) {
        val index = itemCount
        data = arrayListOf<M>().apply {
            this.addAll(newList)
        }
        notifyItemRangeInserted(0, index)
    }

    override fun getItemCount(): Int {
        var size = getDataSize()
        //有头部,item的个数+1
        if (headerView != null) {
            size++
        }
        //有底部,item的个数+1
        if (footerView != null) {
            size++
        }
        if (size == 0) {
            showEmpty = true
            size = 1
        } else {
            showEmpty = false
        }
        return size
    }

    /**
     *
     * @param position Int
     * @return Int
     */
    override fun getItemViewType(position: Int): Int {
        return if (showEmpty) {
            if (position == 0 && headerView != null) {
                //当前view是头部信息
                TYPE_HEADER.value
            } else if (position == 0 && headerView == null) {
                //当前数据空位,展示空页面
                TYPE_EMPTY.value
            } else if (position == 1 && headerView != null) {
                //当前数据空位,展示空页面
                TYPE_EMPTY.value
            } else {
                getCenterViewType()
            }
        } else {
            if (position == 0 && headerView != null) {
                //当前view是头部信息
                TYPE_HEADER.value
            } else if (position == (itemCount - 1) && headerView != null && footerView != null) {
                //当前view是底部信息
                TYPE_FOOTER.value
            } else if (position == (itemCount - 1) && headerView == null && footerView != null) {
                //当前view是底部信息
                TYPE_FOOTER.value
            } else {
                getCenterViewType()
            }
        }
    }

    /**
     * 标准的item的类型
     * @return 返回参数不能小于0
     */
    @IntRange(from = 0)
    fun getCenterViewType(): Int {
        return TYPE_NORMAL.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder<VB> {
        val view = when (ItemViewType.enumOfValue(viewType)) {
            TYPE_HEADER -> {
                headerView!!
            }

            TYPE_FOOTER -> {
                footerView!!
            }

            TYPE_EMPTY -> {
                if (emptyView == null) {
                    emptyView =
                        LayoutRecyclerEmptyBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent, false
                        ).root
                }
                emptyView!!
            }

            else -> {
                return CommonViewHolder(viewBinding(parent))
            }
        }
        return CommonViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommonViewHolder<VB>, position: Int) {
        var index = position
        when (ItemViewType.enumOfValue(getItemViewType(position))) {
            TYPE_HEADER, TYPE_FOOTER -> {
                /*
                 * 当前holder是头部或者底部就直接返回,不需要去设置ViewHolder的内容
                 */
                return
            }

            TYPE_EMPTY -> {
                //第一次加载数据,不展示空页面
                if (firstLoad) {
                    holder.itemView.visibility = View.INVISIBLE
                } else {
                    holder.itemView.visibility = View.VISIBLE
                }
                return
            }

            else -> {
                if (headerView != null) {
                    /*
                     * 有头部的情况,需要要减1,否则取item的数据会取到当前数据的下一条,
                     * 取出最后一条数据的时候,会报下标溢出
                     */
                    index--
                }
                val bean = data[index]
                onItemClick?.let { listener ->
                    holder.itemView.setOnClickListener {
                        listener(bean, index, holder.itemViewBinding)
                    }
                }
                bindViewHolder(holder, bean, position)
            }
        }
    }

    // 绑定RecyclerView时调用
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (ItemViewType.enumOfValue(getItemViewType(position))) {
                        TYPE_HEADER, TYPE_EMPTY, TYPE_FOOTER -> {
                            manager.spanCount //占据一行
                        }

                        else -> {
                            1 // 占据一格
                        }
                    }
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: CommonViewHolder<VB>) {
        super.onViewAttachedToWindow(holder)
        val lp = holder.itemView.layoutParams
        if (lp is StaggeredGridLayoutManager.LayoutParams
            && holder.layoutPosition == 0
        ) {
            // 瀑布流布局处理
            lp.isFullSpan = true
        }
    }

    enum class ItemViewType(val value: Int) {
        /**
         * 头部类型
         */
        TYPE_HEADER(-0x01),

        /**
         * 底部类型
         */
        TYPE_FOOTER(-0x02),

        /**
         * 无数据类型
         */
        TYPE_EMPTY(-0x03),

        /**
         * 正常的item
         */
        TYPE_NORMAL(0x00);

        companion object {
            fun enumOfValue(value: Int): ItemViewType {
                values().forEach {
                    if (it.value == value) {
                        return it
                    }
                }
                return TYPE_NORMAL
            }
        }
    }
}