package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivityLogin2Binding
import `in`.bizeneed.extras.AppPrefData
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.extras.generateOTP
import `in`.bizeneed.extras.hideKeyBoard
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer

class LoginActivity2 : BaseActivity<ActivityLogin2Binding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.continueBtn.setOnClickListener {
            if (binding.mobileNoEdt.text.toString().isEmpty()){
                Toast.makeText(this,"Enter Mobile no.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.mobileNoEdt.text.toString().length != 10){
                Toast.makeText(this,"Enter correct Mobile no.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            hideKeyBoard(this)

            val mobileNo = ("91${binding.mobileNoEdt.text}")

            val otp = generateOTP()

            showProgressBar(null)
            myViewModel.checkMobileNo(mobileNo,otp.toString()).observe(this, Observer {
                hideProgress()

                if (it == null){
                    Toast.makeText(this,"Some error occurred, Try again Later",Toast.LENGTH_SHORT).show()
                    return@Observer
                }

                if (it.data.isNotEmpty()){
                    Toast.makeText(this,"OTP sent successfully", Toast.LENGTH_SHORT).show()
                    AppPrefData.user(it.data[0])
                    val intent = Intent(this,OTPActivity::class.java)
                    intent.putExtra(Constants.MOBILE,mobileNo)
                    intent.putExtra(Constants.OTP,otp.toString())
                    startActivity(intent)
                }
            })
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_login2
}