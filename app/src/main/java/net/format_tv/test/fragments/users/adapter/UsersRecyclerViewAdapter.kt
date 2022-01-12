package net.format_tv.test.fragments.users.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import net.format_tv.test.R
import net.format_tv.test.databinding.RecyclerViewUserItemBinding
import net.format_tv.test.fragments.users.UsersFragment
import net.format_tv.test.models.User
import net.format_tv.test.models.Users
import java.util.*

class UsersRecyclerViewAdapter(private val context: Context, var users: Users, var sortType: UsersFragment.SortType): RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder>( ){

    var search: String = ""
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun updateUsers(newUsers: Users){
        this.users = sort(newUsers)
        notifyDataSetChanged()
    }

    private var listener: UsersAdapterListener? = null

    fun setListener(listener: UsersAdapterListener){
        this.listener = listener
    }

    private fun sort(users: Users): Users{
        val u = Users()
        users.forEach { user ->
            u.add(user)
        }
        if (sortType == UsersFragment.SortType.ALPHABET){
            Collections.sort(u) { o1, o2 ->
                o1!!.firstName.compareTo(o2!!.firstName)
            }
        }else{

        }
        return u
    }

    init {
        this.users = sort(users)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersRecyclerViewAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_user_item, parent, false))
    }

    override fun onBindViewHolder(holder: UsersRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.bind(users[position], listener, search)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        private var binding: RecyclerViewUserItemBinding = RecyclerViewUserItemBinding.bind(view)

        fun bind(user: User, listener: UsersAdapterListener?, search: String){
            search(binding.root.layoutParams, user.firstName+" "+user.lastName, search)

            binding.imageView.setImageResource(R.drawable.ic_user)
            binding.name.text = "${user.firstName} ${user.lastName}"
            binding.department.text = user.department
            binding.item.setOnClickListener {
                listener?.onSelectUser(user, adapterPosition)
            }
        }

        private fun search(params: ViewGroup.LayoutParams, userName: String, search: String){
            if(search != ""){
                if(!userName.contains(search, true))
                    params.height = 0
                else
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }else{
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        }
    }

    interface UsersAdapterListener{
        fun onSelectUser(user: User, position: Int)
    }
}