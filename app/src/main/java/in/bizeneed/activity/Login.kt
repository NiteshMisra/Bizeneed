package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.adapter.LoginImageAdapter
import `in`.bizeneed.databinding.ActivityLoginBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager

class Login : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private var countDownTimer: CountDownTimer ?= null
    private lateinit var imageList : ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        loginSlider()

        binding.loginLayout.visibility = View.VISIBLE
        binding.otpLayout.visibility = View.GONE

        binding.sendOtpBtn.setOnClickListener {
            binding.loginLayout.visibility = View.GONE
            binding.otpLayout.visibility = View.VISIBLE
        }

        binding.loginBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

    }

    private fun loginSlider() {
        imageList = ArrayList()
        imageList.add(R.raw.salon)
        imageList.add(R.raw.cleaning)
        imageList.add(R.raw.safety)
        val loginImageAdapter = LoginImageAdapter(this,imageList)
        binding.viewPager.adapter = loginImageAdapter
        loginImageAdapter.notifyDataSetChanged()

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
                startBannerTimer()
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                startBannerTimer()
            }

            override fun onPageSelected(position: Int) {

            }

        })

    }

    private fun startBannerTimer(){
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(5000,1000) {
            override fun onFinish() {
                val currentPosition = binding.viewPager.currentItem
                val nextPosition = if (currentPosition == imageList.size - 1){
                    0
                }else{
                    currentPosition + 1
                }

                binding.viewPager.setCurrentItem(nextPosition,true)
            }

            override fun onTick(p0: Long) {

            }

        }
        countDownTimer?.start()
    }

    override fun onBackPressed() {
        if (binding.otpLayout.visibility == View.VISIBLE){
            binding.loginLayout.visibility = View.VISIBLE
            binding.otpLayout.visibility = View.GONE
        }else{
            super.onBackPressed()
        }
    }

}