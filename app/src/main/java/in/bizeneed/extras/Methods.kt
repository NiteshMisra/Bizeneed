package `in`.bizeneed.extras

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity

fun hideKeyBoard(fragmentActivity: FragmentActivity){
    val inm = fragmentActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = fragmentActivity.currentFocus
    view?.apply {
        inm.hideSoftInputFromWindow(this.windowToken,0)
    }
}