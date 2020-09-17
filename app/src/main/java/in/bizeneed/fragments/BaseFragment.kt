@file:Suppress("DEPRECATION")

package `in`.bizeneed.fragments

import `in`.bizeneed.R
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

open class BaseFragment : Fragment() {

    private var pDialog: ProgressDialog? = null
    private lateinit var activity1 : FragmentActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity1 = requireActivity()
    }

    fun showProgressBar(message: String?) {
        if (pDialog == null) {
            pDialog = ProgressDialog(activity1)
        }
        if (!pDialog!!.isShowing && !activity1.isFinishing) {
            pDialog!!.setTitle(null)
            pDialog!!.isIndeterminate = false
            pDialog!!.setCancelable(false)
            when {
                message == null -> {
                    pDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    pDialog!!.show()
                    pDialog!!.setContentView(R.layout.progress_bar_layout)
                }
                message.isEmpty() -> {
                    pDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    pDialog!!.show()
                    pDialog!!.setContentView(R.layout.progress_bar_layout)
                }
                else -> {
                    pDialog!!.setMessage(message)
                    pDialog!!.show()
                }
            }
        }
    }

    fun hideProgress() {
        if (pDialog != null && pDialog!!.isShowing) {
            pDialog!!.cancel()
        }
    }

}