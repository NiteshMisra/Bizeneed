package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivityPoliciesBinding
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Policies : BaseActivity<ActivityPoliciesBinding>() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("file:///android_asset/index.html")
    }

    override fun getLayoutRes(): Int = R.layout.activity_policies
}