package com.ergo.notch.skimmiaproject.ui.users.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.ergo.notch.skimmiaproject.R
import com.ergo.notch.skimmiaproject.base.views.BaseFragment
import com.ergo.notch.skimmiaproject.ui.users.model.entities.UserEntity
import com.ergo.notch.skimmiaproject.ui.users.view.adapters.UsersAdapterListener
import com.ergo.notch.skimmiaproject.ui.users.view.adapters.UsersListAdapter
import com.ergo.notch.skimmiaproject.ui.users.viewmodel.UserViewModel
import com.ergo.notch.skimmiaproject.utils.clickEvent
import kotlinx.android.synthetic.main.fragment_user_list_layout.*

/**
 * A simple [Fragment] subclass.
 */
class UserListFragment : BaseFragment() {

    private lateinit var adapter: UsersListAdapter
    private lateinit var userViewModel: UserViewModel

    override fun getResourceLayout(): Int = R.layout.fragment_user_list_layout

    override fun initView(view: View, savedInstanceState: Bundle?) {
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        this.loadAdapter()
        this.setClickListener()
        this.observeResults()
    }

    private fun loadAdapter() {
        this.adapter = UsersListAdapter()
        this.rvUsers.adapter = adapter
        this.rvUsers.layoutManager = LinearLayoutManager(requireContext())
        adapter.setListener(object : UsersAdapterListener {
            override fun onGetUser(userEntity: UserEntity) {
                this@UserListFragment.replaceFragment(
                    AddNewUserFragment.newInstance(userEntity),
                    R.id.frame_container,
                    true
                )
            }
        })
    }

    private fun setClickListener() {
        this.fabAddUser.clickEvent().observe(this, Observer {
            this.replaceFragment(
                AddNewUserFragment.newInstance(null),
                R.id.frame_container,
                true
            )
        })
    }

    private fun observeResults() {
        this.userViewModel.getAllUsers().observe(this, Observer { users ->
            if (!users.isNullOrEmpty()) {
                this.adapter.setUsers(users)
            }
        })
    }


}
