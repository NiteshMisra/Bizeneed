@file:Suppress("DEPRECATION")

package `in`.bizeneed.fragments

import `in`.bizeneed.R
import `in`.bizeneed.extras.getMyViewModelFactory
import `in`.bizeneed.viewmodel.MyViewModel
import android.app.AlertDialog
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    open lateinit var binding : VB
    open lateinit var myViewModel: MyViewModel
    open lateinit var activity1 : FragmentActivity

    private var pDialog: ProgressDialog? = null

    private fun init(inflater: LayoutInflater, container : ViewGroup?){
        activity1 = requireActivity()
        myViewModel = ViewModelProvider(activity1, getMyViewModelFactory(activity1)).get(MyViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,getLayoutRes(),container,false)
    }

    @LayoutRes
    abstract fun getLayoutRes() : Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init(inflater,container)
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    public fun showProgressBar(message: String?)
    {
        if (pDialog == null) {
            pDialog = ProgressDialog(activity1)
        }
        if (!pDialog!!.isShowing && !activity1.isFinishing) {
            pDialog?.setTitle(null)
            pDialog?.isIndeterminate = false
            pDialog?.setCancelable(false)
            if (message == null) {
                pDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                pDialog?.show()
                pDialog?.setContentView(R.layout.progress_bar_layout)
            } else if (message.isEmpty()) {
                pDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                pDialog?.show()
                pDialog?.setContentView(R.layout.progress_bar_layout)
            } else {
                pDialog?.setMessage(message)
                pDialog?.show()
            }
        }
    }

    fun hideProgress() {
        if (pDialog != null && pDialog!!.isShowing) {
            pDialog!!.cancel();
        }
    }

}