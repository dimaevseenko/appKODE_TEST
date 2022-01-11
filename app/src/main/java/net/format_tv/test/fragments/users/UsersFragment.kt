package net.format_tv.test.fragments.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.format_tv.test.R
import net.format_tv.test.databinding.FragmentUsersBinding
import net.format_tv.test.fragments.error.ErrorFragment
import net.format_tv.test.fragments.main.MainFragment
import net.format_tv.test.fragments.users.adapter.UsersRecyclerViewAdapter
import net.format_tv.test.fragments.users.swipe.SwipeHelper
import net.format_tv.test.models.Users

open class UsersFragment: Fragment(), UsersViewModel.LoadUserListener, SwipeHelper.SwipeListener {

    private lateinit var binding: FragmentUsersBinding
    private lateinit var viewModel: UsersViewModel

    private var updateSwipe: SwipeHelper? = null
    private var recyclerViewAdapter: UsersRecyclerViewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUsersBinding.bind(inflater.inflate(R.layout.fragment_users, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(UsersViewModel::class.java)
        viewModel.getUsers(this, getCategory())?.let { onLoaded(it) }
        binding.la.startShimmer()

        getSwipeHelper().attachRecyclerView(binding.recycler)
        getSwipeHelper().attachProgressBar(binding.progress)
        getSwipeHelper().setSwipeListener(this)
    }

    override fun onLoaded(users: Users) {
       loadView(users)
    }

    fun loadView(users: Users = Users()){
        binding.la.stopShimmer()
        binding.la.hideShimmer()
        binding.la.visibility = View.GONE

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = getAdapter(users)
    }

    fun updateSort(){
        getAdapter().sortType = (parentFragment as MainFragment).getSortType()
        getAdapter().updateUsers(viewModel.getUsers(null, getCategory())!!)
    }

    override fun onUpdate() {
        viewModel.updateUsers(this, getCategory())
    }

    override fun onUpdated(users: Users) {
        getSwipeHelper().indeterminateProgress(false)
        getAdapter().updateUsers(users)
    }

    override fun onFailure() {
        parentFragment?.let {
            it.parentFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).add(R.id.fragmentContainerView, ErrorFragment().apply { dest = {
                    onUpdate()
                    loadView()
                } }).commit()
        }
    }

    fun getSort(): SortType{
        return getAdapter().sortType
    }

    open fun getCategory(): String{
        return "all"
    }

    private fun getAdapter(users: Users = Users()): UsersRecyclerViewAdapter{
        if(recyclerViewAdapter == null)
            recyclerViewAdapter = UsersRecyclerViewAdapter(
                requireContext(),
                users,
                (parentFragment as MainFragment).getSortType()
            )
        return recyclerViewAdapter!!
    }

    private fun getSwipeHelper(): SwipeHelper{
        if(updateSwipe == null)
            updateSwipe = SwipeHelper()
        return updateSwipe!!
    }

    enum class SortType{
        ALPHABET, DATE
    }
}