package com.dudu.common.widget

import android.content.Context
import android.view.View
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDialog
import com.dudu.common.R
import com.dudu.common.databinding.DialogLoadingMiniBinding

class MiniLoadingDialog(
    context: Context,
    @StyleRes theme: Int = R.style.CommonDialog_Custom_MiniLoading,
    tip: String = ""
) : AppCompatDialog(context, theme) {

    private var _viewBinding: DialogLoadingMiniBinding? = null
    private val viewBinding
        get() = _viewBinding?:run {
            _viewBinding = DialogLoadingMiniBinding.inflate(layoutInflater)
            _viewBinding!!
        }

    init {
        setContentView(viewBinding.root)
        setCanceledOnTouchOutside(true)
        updateMessage(tip)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    fun updateMessage(tipMessage: String) {
        if(tipMessage.isNotEmpty()){
            viewBinding.tipView.run {
                text = tipMessage
                visibility = View.VISIBLE
            }
        } else {
            viewBinding.tipView.run {
                text = ""
                visibility = View.GONE
            }
        }
    }

    fun performShow() {
        super.show()
        viewBinding.miniLoadingView.start()
    }

    override fun dismiss() {
        viewBinding.miniLoadingView.stop()
        super.dismiss()
    }

}