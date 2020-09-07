package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivityAddressBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class Address : AppCompatActivity() {

    private lateinit var binding : ActivityAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_address)
    }
}