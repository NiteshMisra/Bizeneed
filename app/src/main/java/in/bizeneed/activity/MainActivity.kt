@file:Suppress("DEPRECATION")

package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivityMainBinding
import `in`.bizeneed.extras.AppPrefData
import `in`.bizeneed.fragments.BookingsFragment
import `in`.bizeneed.fragments.HomeFragment
import `in`.bizeneed.fragments.RewardsFragment
import `in`.bizeneed.fragments.SettingsFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addFragment(HomeFragment.newInstance())

        binding.bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> addFragment(HomeFragment.newInstance())
                R.id.nav_bookings -> addFragment(BookingsFragment.newInstance())
                R.id.nav_reward -> addFragment(RewardsFragment.newInstance())
                R.id.nav_setting -> addFragment(SettingsFragment.newInstance())
            }
            return@setOnNavigationItemSelectedListener true
        }

        tokenApi()
    }

    private fun tokenApi() {
        val token = AppPrefData.token()
        token?.let { token1 ->
            val fireBaseToken = FirebaseInstanceId.getInstance().token
            fireBaseToken?.let { token2 ->
                if (token1 != token2){
                    myViewModel.updateToken()
                }
            }
        }
    }

    fun moveToHome(){
        binding.bottomNav.selectedItemId = R.id.nav_home
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

    override fun getLayoutRes(): Int = R.layout.activity_main

}