package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivityContactUsBinding
import `in`.bizeneed.extras.hideKeyBoard
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer

class ContactUs : BaseActivity<ActivityContactUsBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.backBtnIv.setOnClickListener { onBackPressed() }

        binding.sendFeedBack.setOnClickListener {
            if (binding.feedbackEdt.text.toString().isEmpty()){
                return@setOnClickListener
            }

            hideKeyBoard(this)
            showProgressBar(null)
            myViewModel.sendFeedBack(binding.feedbackEdt.text.toString()).observe(this, Observer {
                hideProgress()
                it.let {
                    binding.feedbackEdt.setText("")
                    Toast.makeText(this,"Thanks for your FeedBack",Toast.LENGTH_SHORT).show()
                }
            })
        }

        binding.callUs.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=+917710771301"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        binding.chatWithUs.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + "7710771301")
            startActivity(intent)
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_contact_us
}