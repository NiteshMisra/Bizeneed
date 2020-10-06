package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivityOtpBinding
import `in`.bizeneed.extras.AppPrefData
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.extras.SmsBroadcastReceiver
import `in`.bizeneed.extras.extractDigits
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.phone.SmsRetriever

class OTPActivity : BaseActivity<ActivityOtpBinding>() {

    private lateinit var mobile : String
    private lateinit var otp : String
    private val consent = 200
    private var smsBroadcastReceiver: SmsBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mobile = intent.getStringExtra(Constants.MOBILE)!!
        otp = intent.getStringExtra(Constants.OTP)!!

        binding.mobileTxt.text = ("+91 ${mobile.substring(2)}")
        startSmsUserConsent()

        binding.verifyBtn.setOnClickListener {

            val otpString = binding.otpTxt.otp
            if (otpString == null ){
                Toast.makeText(this,"Some error occurred",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (otpString.isEmpty() || otpString.length < 6){
                Toast.makeText(this,"Invalid OTP",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (otpString == otp){
                val user = AppPrefData.user()!!
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
            }else{
                Toast.makeText(this,"Invalid OTP",Toast.LENGTH_SHORT).show()
            }

        }

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
                    startActivityForResult(intent, consent)
                }

                override fun onFailure() {}
            }
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsBroadcastReceiver, intentFilter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == consent) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                //That gives all message to us.
                // We need to get the code from inside with regex
                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                binding.otpTxt.setOTP(extractDigits(message)!!)
            }
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_otp
}