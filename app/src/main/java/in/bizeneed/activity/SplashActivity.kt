@file:Suppress("DEPRECATION")

package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivitySplashBinding
import `in`.bizeneed.extras.AppPrefData
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        val fadeIn: Animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.fade_in)
        binding.imageView.startAnimation(fadeIn)

        Handler().postDelayed({
            val intent = if (AppPrefData.isLogin())
            {
                val user = AppPrefData.user()!!
                if (user.name == null || user.email == null){
                    Intent(this, ProfileRegistration::class.java)
                }else{
                    Intent(this, MainActivity::class.java)
                }
            } else {
                Intent(this, WelcomeActivity::class.java)
            }
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
        }, 3000)
    }
}