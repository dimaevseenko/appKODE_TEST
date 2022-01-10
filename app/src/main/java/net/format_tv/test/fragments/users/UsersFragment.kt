package net.format_tv.test.fragments.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import net.format_tv.test.R
import net.format_tv.test.databinding.FragmentUsersBinding
import net.format_tv.test.fragments.users.adapter.UsersRecyclerViewAdapter
import net.format_tv.test.fragments.users.swipe.SwipeHelper
import net.format_tv.test.models.Users

class UsersFragment: Fragment(), UsersViewModel.LoadUserListener, SwipeHelper.UpdateListener {

    private lateinit var binding: FragmentUsersBinding
    private lateinit var viewModel: UsersViewModel

    private var updateSwipe: SwipeHelper? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUsersBinding.bind(inflater.inflate(R.layout.fragment_users, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(UsersViewModel::class.java)
        viewModel.getUsers(this)?.let { onLoaded(it) }
        binding.la.startShimmer()
        getSwipeHelper()
    }

    override fun onLoaded(users: Users) {
        binding.la.stopShimmer()
        binding.la.hideShimmer()
        binding.la.visibility = View.GONE

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = UsersRecyclerViewAdapter(requireContext(), users)
        getSwipeHelper().attachRecyclerView(binding.recycler)
        getSwipeHelper().attachProgressBar(binding.progress)
        getSwipeHelper().setUpdateListener(this)
    }

    override fun onUpdate(flag: Boolean) {
        println(flag)
    }

    private fun getSwipeHelper(): SwipeHelper{
        if(updateSwipe == null)
            updateSwipe = SwipeHelper()
        return updateSwipe!!
    }
}