package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.adapter.LoginImageAdapter
import `in`.bizeneed.databinding.ActivityLoginBinding
import `in`.bizeneed.extras.*
import `in`.bizeneed.response.User
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.auth.api.phone.SmsRetriever

class Login : BaseActivity<ActivityLoginBinding>() {

    private var countDownTimer: CountDownTimer ?= null
    private lateinit var imageList : ArrayList<Int>
    private var otp : Int = 0
    private lateinit var mobileNo : String
    private lateinit var user : User
    private val REQ_USER_CONSENT = 200
    var smsBroadcastReceiver: SmsBroadcastReceiver? = null

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

            showProgressBar(null)
            myViewModel.checkMobileNo(mobileNo,otp.toString()).observe(this, Observer {
                hideProgress()
                it?.let {
                    Toast.makeText(this,"OTP sent successfully",Toast.LENGTH_SHORT).show()
                    startSmsUserConsent()
                    binding.otpSentTo.text = ("OTP sent to +91 XXXXX ${mobileNo.substring(7,12)}")
                    binding.loginLayout.visibility = View.GONE
                    binding.otpLayout.visibility = View.VISIBLE
                    user = it.data[0]
                }
            })
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
            if (user.name == null || user.email == null){
                val intent = Intent(this,ProfileRegistration::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }else{
                val intent = Intent(this,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
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

    private fun startSmsUserConsent() {
        val client = SmsRetriever.getClient(this)
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null)
            .addOnSuccessListener { registerBroadcastReceiver() }
            .addOnFailureListener { }
    }

    private fun registerBroadcastReceiver() {
        if (smsBroadcastReceiver != null) {
            return
        }
        smsBroadcastReceiver = SmsBroadcastReceiver()
        smsBroadcastReceiver!!.smsBroadcastReceiverListener =
            object : SmsBroadcastReceiver.SmsBroadcastReceiverListener {
                override fun onSuccess(intent: Intent?) {
                    startActivityForResult(intent, REQ_USER_CONSENT)
                }

                override fun onFailure() {}
            }
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsBroadcastReceiver, intentFilter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_USER_CONSENT) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                //That gives all message to us.
                // We need to get the code from inside with regex
                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                binding.otpEdt.setText(extractDigits(message))
            }
        }
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