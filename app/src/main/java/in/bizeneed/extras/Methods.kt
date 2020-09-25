package `in`.bizeneed.extras

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import java.util.*

fun hideKeyBoard(fragmentActivity: FragmentActivity) {
    val inm = fragmentActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = fragmentActivity.currentFocus
    view?.apply {
        inm.hideSoftInputFromWindow(this.windowToken, 0)
    }
}

fun generateOTP(): Int {
    var otp: Int = Random().nextInt(999999)
    if (otp < 100000) {
        otp = when (otp.toString().length) {
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
fun isConnected(context: Context): Boolean {
    val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = connectivity.allNetworkInfo
    for (networkInfo in info) if (networkInfo.state == NetworkInfo.State.CONNECTED) {
        return true
    }
    return false
}

fun formattedRating(ratingCount: Int): String {
    return if (ratingCount > 1000) {
        val thousandPlace = ratingCount / 1000
        val decimalPlace = ratingCount % 1000
        ("$thousandPlace.${String.format(Locale.getDefault(), "%02d", decimalPlace)}k")
    } else {
        ratingCount.toString()
    }
}

fun getDate(date: String): String {
    val year = date.substring(0, 4)
    val day = date.substring(8, 10)
    val mon = date.substring(5, 7).toInt()
    val month = arrayOf(
        " ", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
        "Aug", "Sept", "Oct", "Nov", "Dec"
    )
    return day + " " + month[mon] + " " + year
}

fun getTime(time: String): String {
    var a = time.substring(11, 13).toInt()
    var s = " AM"
    if (a >= 12) {
        a -= 12
        s = " PM"
    }
    if (a == 0) {
        a = 12
    }
    return """$a${time.substring(13, 16)}$s"""
}
