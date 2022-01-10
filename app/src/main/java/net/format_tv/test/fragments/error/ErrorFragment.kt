package net.format_tv.test.fragments.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import net.format_tv.test.R
import net.format_tv.test.databinding.FragmentFailureBinding

class ErrorFragment: Fragment() {

    private lateinit var binding: FragmentFailureBinding

    var dest: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFailureBinding.bind(inflater.inflate(R.layout.fragment_failure, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textView3.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).remove(this).commit()
        }
    }

    override fun onDestroy() {
        dest?.let { it() }
        super.onDestroy()
    }
}