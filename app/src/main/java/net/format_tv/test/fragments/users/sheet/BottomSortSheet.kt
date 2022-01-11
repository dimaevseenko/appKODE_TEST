package net.format_tv.test.fragments.users.sheet

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment.STYLE_NORMAL
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import net.format_tv.test.R
import net.format_tv.test.databinding.DialogSortBinding
import net.format_tv.test.fragments.main.MainFragment
import net.format_tv.test.fragments.users.UsersFragment

class BottomSortSheet: BottomSheetDialogFragment() {

    private lateinit var binding: DialogSortBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.ThemeOverlay_App_BottomSheetDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogSortBinding.bind(inflater.inflate(R.layout.dialog_sort, container, false))
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.root.setBackgroundColor(Color.TRANSPARENT)

        checkSorts()

        binding.radioButton.setOnClickListener { (parentFragment as MainFragment).setSortType(UsersFragment.SortType.ALPHABET) }
        binding.radioButton2.setOnClickListener { (parentFragment as MainFragment).setSortType(UsersFragment.SortType.DATE) }
    }

    private fun checkSorts(){
        if((parentFragment as MainFragment).getCurrentViewPagerItemFragment()?.getSort() == UsersFragment.SortType.ALPHABET)
            binding.radioButton.isChecked = true
        else
            binding.radioButton2.isChecked = true
    }
}