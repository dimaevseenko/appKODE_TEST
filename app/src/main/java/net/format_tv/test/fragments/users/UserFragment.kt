package net.format_tv.test.fragments.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import net.format_tv.test.R
import net.format_tv.test.databinding.FragmentUserBinding
import net.format_tv.test.models.User

class UserFragment: Fragment(){

    private lateinit var binding: FragmentUserBinding

    private lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserBinding.bind(inflater.inflate(R.layout.fragment_user, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        user = arguments?.getParcelable<User>("user") as User
        binding.imageButtonCancel.setOnClickListener { parentFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).remove(this).commit() }
        binding.name.text = user.firstName+" "+user.lastName
        binding.department.text = user.department
        binding.date.text = user.birthday
        binding.phone.text = user.phone
        binding.years.text = user.userTag
        binding.imageView4.setImageResource(R.drawable.ic_full)
    }
}