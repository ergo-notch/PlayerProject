package com.ergo.notch.skimmiaproject.ui.users.view.adapters

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ergo.notch.skimmiaproject.R
import com.ergo.notch.skimmiaproject.ui.users.model.entities.UserEntity
import com.ergo.notch.skimmiaproject.utils.inflate
import kotlinx.android.synthetic.main.user_item_layout.view.*
import java.io.File

class UsersListAdapter :
    RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {

    private var listener: UsersAdapterListener? = null
    private var users = emptyList<UserEntity>() // Cached copy of words

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.user_item_layout))

    override fun getItemCount(): Int = this.users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateItem(this.users[position])
        holder.itemView.setOnClickListener {
            this.listener?.onGetUser(this.users[position])
        }
    }

    internal fun setUsers(users: List<UserEntity>) {
        this.users = users
        notifyDataSetChanged()
    }

    internal fun setListener(listener: UsersAdapterListener) {
        this.listener = listener
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun updateItem(userEntity: UserEntity) {
            itemView.tvName.text = userEntity.userName
            itemView.ivUserPreview.setImageURI(Uri.fromFile(File(userEntity.photo)))
        }

    }
}

interface UsersAdapterListener {
    fun onGetUser(userEntity: UserEntity)
}
