package com.dudu.common.base.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/08/20
 *     desc   : 适用于ViewPager2，FragmentAdapter，会保存Fragment状态
 * </pre>
 */
class FragmentAdapter(activity: FragmentActivity, private val fragments: List<Fragment>) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}