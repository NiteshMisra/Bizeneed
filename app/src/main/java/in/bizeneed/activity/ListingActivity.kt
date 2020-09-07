package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivityListingBinding
import `in`.bizeneed.fragments.SearchFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

class ListingActivity : AppCompatActivity(){

    private lateinit var binding : ActivityListingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_listing)

        binding.backBtn.setOnClickListener { onBackPressed() }
        binding.title.text = ("Salon at home")

        supportFragmentManager.beginTransaction()
            .replace(R.id.search_container,SearchFragment.newInstance(""))
            .commit()

    }
}