package com.ergo.notch.skimmiaproject.ui.users.view.fragments

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ergo.notch.skimmiaproject.R
import com.ergo.notch.skimmiaproject.base.views.BaseFragment
import com.ergo.notch.skimmiaproject.ui.galleryMedia.view.fragments.GalleryMediaCallback
import com.ergo.notch.skimmiaproject.ui.galleryMedia.view.fragments.GalleryMediaFragment
import com.ergo.notch.skimmiaproject.ui.users.model.entities.UserEntity
import com.ergo.notch.skimmiaproject.ui.users.viewmodel.UserViewModel
import com.ergo.notch.skimmiaproject.utils.clickEvent
import com.ergo.notch.skimmiaproject.utils.gone
import kotlinx.android.synthetic.main.fragment_add_new_user_layout.*
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class AddNewUserFragment : BaseFragment(), GalleryMediaCallback {

    private var updateUser: UserEntity? = null
    private var imageUri: String? = null
    private lateinit var userViewModel: UserViewModel

    override fun getResourceLayout(): Int = R.layout.fragment_add_new_user_layout

    override fun initView(view: View, savedInstanceState: Bundle?) {
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        this.loadImage()
        this.setClickListeners()
        this.loadArguments()
    }

    private fun loadArguments() {
        this.arguments?.let {
            (it.getSerializable(USER_ENTITY) as UserEntity?)?.let { user ->
                this.updateUser = user
                this.loadUserInformation(user)
            }
        }
    }

    private fun loadUserInformation(user: UserEntity) {
        this.etDescription.setText(user.biography)
        this.etName.setText(user.name)
        this.etLastName.setText(user.lastName)
        this.etUsername.setText(user.userName)
        this.imageUri = user.photo
        this.loadImage()
    }

    private fun loadImage() {
        this.imageUri?.let {
            this.civUserPhoto.setImageURI(Uri.fromFile(File(it)))
            this.ivAddPhoto.gone()
        }
    }

    private fun setClickListeners() {
        this.btnSaveUser.clickEvent().observe(this, Observer {
            if (this.updateUser != null) {
                userViewModel.update(generateUser())
            } else {
                userViewModel.insert(generateUser())
            }
            requireActivity().supportFragmentManager.popBackStack()
        })

        this.civUserPhoto.clickEvent().observe(this, Observer {
            this.replaceFragment(
                GalleryMediaFragment.newInstance(true, this),
                R.id.frame_container,
                true
            )
        })

    }

    private fun generateUser(): UserEntity {
        return UserEntity(
            if (updateUser == null) 0 else updateUser!!.id,
            name = this.etName.text.toString(),
            lastName = this.etLastName.text.toString(),
            userName = this.etUsername.text.toString(),
            photo = this.imageUri.toString(),
            biography = this.etDescription.text.toString()
        )
    }

    companion object {
        private const val USER_ENTITY = "user.entity"

        fun newInstance(userEntity: UserEntity?) = AddNewUserFragment().apply {
            arguments = bundleOf(
                USER_ENTITY to userEntity
            )
        }
    }

    override fun onSelectUserImage(imagePath: String) {
        this.updateUser?.let {
            it.photo = imagePath
        }
        this.imageUri = imagePath
    }


}
