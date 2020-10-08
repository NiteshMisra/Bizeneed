package `in`.bizeneed.extras

import `in`.bizeneed.response.User
import android.app.Activity
import android.content.SharedPreferences
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList


class AppPrefData {

    companion object {

        private val preferences: SharedPreferences
            get() = MyApplication.applicationContext()!!.getSharedPreferences(Constants.APP_NAME, Activity.MODE_PRIVATE)

        fun preferenceEditor(): SharedPreferences.Editor {
            return MyApplication.applicationContext()!!.getSharedPreferences(Constants.APP_NAME, Activity.MODE_PRIVATE).edit()
        }

        //// Add following lines to store and retrieve String

        fun isLogin(value: Boolean) {
            val editor = preferences.edit()
            editor.putBoolean("APP_SESSION", value)
            editor.apply()
        }

        fun isLogin(): Boolean {
            val mSharedPreferences = preferences
            return mSharedPreferences.getBoolean("APP_SESSION", false)
        }

        fun walletAmount(value: String) {
            val editor = preferences.edit()
            editor.putString("USER_WALLET", value)
            editor.apply()
        }

        fun walletAmount(): String? {
            val mSharedPreferences = preferences
            return mSharedPreferences.getString("USER_WALLET","0")
        }

        fun user(user: User) {
            val editor = preferences.edit()
            editor.putString("USER_DATA", Gson().toJson(user))
            editor.apply()
        }

        fun user(): User? {
            val mSharedPreferences = preferences
            val value = mSharedPreferences.getString("USER_DATA", "")
            return if (value == null){
                null
            }else{
                if (value.isNotEmpty()){
                    Gson().fromJson(value,User::class.java)
                }else{
                    null
                }
            }
        }

        fun token(token: String) {
            val editor = preferences.edit()
            editor.putString("USER_TOKEN", Gson().toJson(token))
            editor.apply()
        }

        fun token(): String? {
            val mSharedPreferences = preferences
            return mSharedPreferences.getString("USER_TOKEN", "")
        }
    }
}