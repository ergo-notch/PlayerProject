package com.ergo.notch.skimmiaproject.base.views

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import android.view.View
import com.ergo.notch.skimmiaproject.R

/**
 * A simple [Fragment] subclass.
 */
class LoaderFragment : BaseDialogFragment() {

    private var isShowing = false


    companion object {
        fun newInstance() =
            LoaderFragment()
    }

    init {
        isCancelable = false
    }

    override fun getStyle(): Int? = R.style.DialogTransparent

    override fun getLayout(): Int = R.layout.fragment_loader_layout
    override fun initView(view: View, savedInstanceState: Bundle?) {
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (!isShowing) {
            isShowing = true
            try {
                val fragmentTransaction = manager.beginTransaction()
                fragmentTransaction.add(this, LoaderFragment::class.java.name)
                fragmentTransaction.commitAllowingStateLoss()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }


    override fun dismiss() {
        isShowing = false
        super.dismissAllowingStateLoss()
    }


}
