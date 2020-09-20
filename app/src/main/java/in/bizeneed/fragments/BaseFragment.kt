@file:Suppress("DEPRECATION")

package `in`.bizeneed.fragments

import `in`.bizeneed.extras.getMyViewModelFactory
import `in`.bizeneed.viewmodel.MyViewModel
import android.app.AlertDialog
import android.app.ProgressDialog
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
import dmax.dialog.SpotsDialog

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    open lateinit var binding : VB
    open lateinit var myViewModel: MyViewModel
    open lateinit var activity1 : FragmentActivity
    private var dialog : AlertDialog?= null

    private var pDialog: ProgressDialog? = null

    private fun init(inflater: LayoutInflater, container : ViewGroup?){
        activity1 = requireActivity()
        dialog = SpotsDialog.Builder().setContext(activity1).setMessage("Please wait...").setCancelable(false).build()
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

    fun showProgressBar(message: String?) {
        if (dialog == null){
            dialog = SpotsDialog.Builder().setContext(activity1)
                .setMessage("Please wait...")
                .setCancelable(false)
                .build()
        }
        if (!dialog!!.isShowing && !activity1.isFinishing){
            dialog!!.show()
        }
    }

    fun hideProgress() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

}