@file:Suppress("DEPRECATION")

package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.extras.AppPrefData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
           if (AppPrefData.isLogin()){
               val intent = Intent(this,MainActivity::class.java)
               intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
               startActivity(intent)
           }else{
               val intent = Intent(this,Login::class.java)
               intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
               startActivity(intent)
           }
        },3000)
    }
}