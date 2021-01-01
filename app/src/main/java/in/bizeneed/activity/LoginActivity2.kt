@file:Suppress("DEPRECATION")

package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivityLogin2Binding
import `in`.bizeneed.extras.AppPrefData
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.extras.generateOTP
import `in`.bizeneed.extras.hideKeyBoard
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.firebase.iid.FirebaseInstanceId

class LoginActivity2 : BaseActivity<ActivityLogin2Binding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // making notification bar transparent
        // Checking for first time launch - before calling setContentView()
        // prefManager = new UserSession(this);
        // if (!prefManager.isFirstTimeLaunch()) {
        //            launchHomeScreen();
        //            finish();
        //        }

        //UserSession userSession = new UserSession(this);
        // making notification bar transparent
        changeStatusBarColor()

        // Making notification bar transparent

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

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

            var otp = generateOTP()
            while (otp.toString().length < 6){
                otp = generateOTP()
            }

            var token = ""
            val firebaseToken = FirebaseInstanceId.getInstance().token
            firebaseToken?.let {
                token = it
                Log.e("data",token)
                AppPrefData.token(token)
            }
            showProgressBar(null)
            myViewModel.checkMobileNo(mobileNo,otp.toString(),token).observe(this, Observer {
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

    /**
     * Making notification bar transparent
     */
    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }
    override fun getLayoutRes(): Int = R.layout.activity_login2
}