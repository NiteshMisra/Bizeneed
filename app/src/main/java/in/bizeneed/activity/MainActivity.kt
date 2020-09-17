package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivityMainBinding
import `in`.bizeneed.fragments.BookingsFragment
import `in`.bizeneed.fragments.HomeFragment
import `in`.bizeneed.fragments.SettingsFragment
import `in`.bizeneed.fragments.RewardsFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

class MainActivity : BaseActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        addFragment(HomeFragment.newInstance())

        binding.bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> addFragment(HomeFragment.newInstance())
                R.id.nav_bookings -> addFragment(BookingsFragment.newInstance())
                //R.id.nav_safe -> {} // TODO move to safe Activity
                R.id.nav_rewards -> addFragment(RewardsFragment.newInstance())
                R.id.nav_profile -> addFragment(SettingsFragment.newInstance())
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.home_container,fragment)
            .addToBackStack("homeTag")
            .commit()
    }

    override fun onBackPressed() {
        if (binding.bottomNav.selectedItemId == R.id.nav_home){
            finish()
        }else{
            binding.bottomNav.selectedItemId = R.id.nav_home
        }
    }

}