@file:Suppress("DEPRECATION")

package `in`.bizeneed.activity

import `in`.bizeneed.R
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    private var pDialog: ProgressDialog? = null

    fun showProgressBar(message: String?) {
        if (pDialog == null) {
            pDialog = ProgressDialog(this)
        }
        if (!pDialog!!.isShowing && !this.isFinishing) {
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