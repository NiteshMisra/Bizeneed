package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivityRegistrationBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class Registration : AppCompatActivity() {

    private lateinit var binding : ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_registration)

        binding.continueBtn.setOnClickListener {
            startActivity(Intent(this,Summary::class.java))
        }

    }
}