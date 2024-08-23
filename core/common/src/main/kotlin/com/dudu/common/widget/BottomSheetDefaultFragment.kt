package com.dudu.common.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dudu.common.R
import com.dudu.common.databinding.FragmentBottomSheetDefaultBinding
import com.dudu.common.util.DensityUtils
import com.dudu.common.util.DensityUtils.getScreenWidthPixels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/08/16
 *     desc   : 底部弹出窗口模版
 * </pre>
 */
@AndroidEntryPoint
class BottomSheetDefaultFragment : BottomSheetDialogFragment() {

    //private val viewModel: PlanViewModel by activityViewModels()

    private lateinit var bodyBinding: FragmentBottomSheetDefaultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.defaultBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bodyBinding = FragmentBottomSheetDefaultBinding.inflate(layoutInflater, container, false)
        return bodyBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (dialog == null || dialog !is BottomSheetDialog) {
            return
        }

        val sheetDialog = dialog as BottomSheetDialog
        val behavior = sheetDialog.behavior

        behavior.peekHeight = (DensityUtils.getScreenHeightPixels() * 0.6).toInt()
        behavior.maxHeight = (DensityUtils.getScreenHeightPixels() * 0.6).toInt()
        behavior.maxWidth = getScreenWidthPixels()

        initView()
    }

    private fun initView() {

    }

}