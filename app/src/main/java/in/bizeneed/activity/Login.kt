package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.adapter.LoginImageAdapter
import `in`.bizeneed.databinding.ActivityLoginBinding
import `in`.bizeneed.extras.AppPrefData
import `in`.bizeneed.extras.generateOTP
import `in`.bizeneed.extras.hideKeyBoard
import `in`.bizeneed.extras.isConnected
import `in`.bizeneed.response.User
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager

class Login : BaseActivity<ActivityLoginBinding>() {

    private var countDownTimer: CountDownTimer ?= null
    private lateinit var imageList : ArrayList<Int>
    private var otp : Int = 0
    private lateinit var mobileNo : String
    private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginSlider()

        binding.loginLayout.visibility = View.VISIBLE
        binding.otpLayout.visibility = View.GONE

        binding.sendOtpBtn.setOnClickListener {

            if (binding.mobileNoEdt.text.toString().isEmpty()){
                Toast.makeText(this,"Enter Mobile no.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.mobileNoEdt.text.toString().length != 10){
                Toast.makeText(this,"Enter correct Mobile no.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            hideKeyBoard(this)

            mobileNo = ("91${binding.mobileNoEdt.text}")

            otp = generateOTP()

            if (isConnected(this)){
                showProgressBar(null)
                myViewModel.checkMobileNo(mobileNo,otp.toString()).observe(this, Observer {
                    hideProgress()
                    it?.let {
                        Toast.makeText(this,"OTP sent successfully",Toast.LENGTH_SHORT).show()
                        binding.otpSentTo.text = ("OTP sent to +91 XXXXX ${mobileNo.substring(5,10)}")
                        binding.loginLayout.visibility = View.GONE
                        binding.otpLayout.visibility = View.VISIBLE
                        user = it.data[0]
                    }
                })
            }else{
                Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show()
            }
        }

        binding.loginBtn.setOnClickListener {

            if (binding.otpEdt.text.trim().toString().isEmpty()){
                Toast.makeText(this,"Enter OTP",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.otpEdt.text.toString() != otp.toString()){
                Toast.makeText(this,"Incorrect OTP",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            hideKeyBoard(this)

            AppPrefData.user(user)
            AppPrefData.isLogin(true)
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

    override fun getLayoutRes(): Int = R.layout.activity_login

}