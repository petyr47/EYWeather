package com.aneke.peter.eyweather.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.aneke.peter.eyweather.R

class LoadingDialog :  DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        isCancelable = false

        return inflater.inflate(R.layout.dialog_loader, container, false)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        } catch (ignored: IllegalStateException) {

        }
    }
}


open class LoadableActivity : AppCompatActivity() {

    private val loader : LoadingDialog by lazy { LoadingDialog() }

    fun showLoader() {
        hideLoader()
        loader.show(supportFragmentManager, loader.tag)
    }

    fun hideLoader() {
        if (loader.isAdded){
            loader.dismiss()
        }
    }

}