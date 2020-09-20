@file:Suppress("DEPRECATION")

package `in`.bizeneed.activity

import `in`.bizeneed.extras.getMyViewModelFactory
import `in`.bizeneed.viewmodel.MyViewModel
import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import dmax.dialog.SpotsDialog

abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {

    open lateinit var binding : VB
    open lateinit var myViewModel: MyViewModel

    private var pDialog: ProgressDialog? = null
    private var dialog : AlertDialog?= null

    @LayoutRes
    abstract fun getLayoutRes() : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = SpotsDialog.Builder().setContext(this).setMessage("Please wait...").setCancelable(false).build()
        myViewModel = ViewModelProvider(this, getMyViewModelFactory(this)).get(MyViewModel::class.java)
        binding = DataBindingUtil.setContentView(this,getLayoutRes())
    }

    fun showProgressBar(message: String?) {
        if (dialog == null){
            dialog = SpotsDialog.Builder().setContext(this)
                .setMessage("Please wait...")
                .setCancelable(false)
                .build()
        }
        if (!dialog!!.isShowing && !this.isFinishing){
            dialog!!.show()
        }
    }

    fun hideProgress() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }
}