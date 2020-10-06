@file:Suppress("DEPRECATION")

package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.extras.getMyViewModelFactory
import `in`.bizeneed.viewmodel.MyViewModel
import android.app.AlertDialog
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import java.util.*

abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {

    open lateinit var binding : VB
    open lateinit var myViewModel: MyViewModel

    private var pDialog: ProgressDialog? = null

    @LayoutRes
    abstract fun getLayoutRes() : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myViewModel = ViewModelProvider(this, getMyViewModelFactory(this)).get(MyViewModel::class.java)
        binding = DataBindingUtil.setContentView(this,getLayoutRes())
    }

    fun showProgressBar(message: String?) {
        if (pDialog == null) {
            pDialog = ProgressDialog(this)
        }
        if (!pDialog!!.isShowing && !this.isFinishing) {
            pDialog?.setTitle(null)
            pDialog?.isIndeterminate = false
            pDialog?.setCancelable(false)
            when {
                message == null -> {
                    pDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    pDialog?.show()
                    pDialog?.setContentView(R.layout.progress_bar_layout)
                }
                message.isEmpty() -> {
                    pDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    pDialog?.show()
                    pDialog?.setContentView(R.layout.progress_bar_layout)
                }
                else -> {
                    pDialog?.setMessage(message)
                    pDialog?.show()
                }
            }
        }
    }

    fun hideProgress() {
        if (pDialog != null && pDialog!!.isShowing) {
            pDialog!!.cancel();
        }
    }
}