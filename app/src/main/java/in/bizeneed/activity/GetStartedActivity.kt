package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivityGetStartedBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GetStartedActivity : BaseActivity<ActivityGetStartedBinding>(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.getStarted.setOnClickListener {
            startActivity(Intent(this,LoginActivity2::class.java))
        }

    }

    override fun getLayoutRes(): Int = R.layout.activity_get_started
}