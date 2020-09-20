package `in`.bizeneed.extras

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import java.util.*

fun hideKeyBoard(fragmentActivity: FragmentActivity){
    val inm = fragmentActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = fragmentActivity.currentFocus
    view?.apply {
        inm.hideSoftInputFromWindow(this.windowToken,0)
    }
}

fun generateOTP() : Int {
    var otp : Int = Random().nextInt(999999)
    if (otp < 100000){
        otp = when(otp.toString().length){
            5 -> "0$otp".toInt()
            4 -> "00$otp".toInt()
            3 -> "000$otp".toInt()
            2 -> "0000$otp".toInt()
            1 -> "00000$otp".toInt()
            else -> generateOTP().toInt()
        }
    }
    return otp;
}

@Suppress("DEPRECATION")
fun isConnected(context : Context): Boolean {
    val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = connectivity.allNetworkInfo
    for (networkInfo in info) if (networkInfo.state == NetworkInfo.State.CONNECTED) {
        return true
    }
    return false
}