package com.dudu.common.bean

import androidx.annotation.StringRes
import com.dudu.common.util.getResString

data class Title(
    @StringRes val id: Int = 0,
    val isBack: Boolean = true,
    val titleType: TitleType = TitleType.DEF,
    var text: String = "",
) {
    init {
        if (id != 0) getResString(id)?.let { text = it }
    }
}

enum class TitleType {
    DEF, // 默认固定
    COLL, // 伸缩动态
}