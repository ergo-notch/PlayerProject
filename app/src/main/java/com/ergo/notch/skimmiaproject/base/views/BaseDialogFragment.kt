package com.ergo.notch.skimmiaproject.base.views

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ergo.notch.skimmiaproject.R
import com.ergo.notch.skimmiaproject.utils.createDialog

abstract class BaseDialogFragment : DialogFragment() {

    abstract fun getStyle(): Int?

    abstract fun getLayout(): Int

    abstract fun initView(view: View, savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getStyle()?.let {
            setStyle(DialogFragment.STYLE_NORMAL, it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(getLayout(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
    }

    fun displayAlert(message: String) {
        createDialog(
            context!!,
            message,
            getString(R.string.accept),
            isCancelable = false,
            positiveListener = DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() }
        )
    }

    open fun showAlert(message: String) {
        createDialog(
            context!!,
            message,
            getString(R.string.accept),
            isCancelable = false,
            positiveListener = DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() }
        )
    }

    open fun showAlertWithResource(message: Int) {
        createDialog(
            context!!,
            getString(message),
            getString(R.string.accept),
            isCancelable = false,
            positiveListener = DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() }
        )
    }
}