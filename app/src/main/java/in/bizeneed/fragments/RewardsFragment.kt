package `in`.bizeneed.fragments

import `in`.bizeneed.BuildConfig
import `in`.bizeneed.R
import `in`.bizeneed.databinding.FragmentRewardsBinding
import `in`.bizeneed.dialog.CouponAddDialog
import `in`.bizeneed.extras.getUserReferCode
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import java.util.*

class RewardsFragment : BaseFragment<FragmentRewardsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.referBtn.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"Hey check out this app: https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID} and use my code : ${getUserReferCode()}, to get extra offers")
            intent.type = "text/plain"
            startActivity(intent)
        }

        binding.applyCoupon.setOnClickListener {
            if (binding.couponCodeEdt.text.toString().isEmpty()){
                Toast.makeText(activity1,"Enter Coupon Code",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showProgressBar(null)
            myViewModel.checkReferAll(binding.couponCodeEdt.text.toString()).observe(activity1, Observer {
                hideProgress()
                it?.let {
                    if (it.data[0].isValid.toLowerCase(Locale.getDefault()) == "in valid"){
                        Toast.makeText(activity1,"This device has been already registered rewards and benefits will not applicable",Toast.LENGTH_SHORT).show()
                    }
                    else if(it.data[0].isValid.toLowerCase(Locale.getDefault()) == "no")
                    {
                        Toast.makeText(activity1,"Invalid Coupon",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val dialog = CouponAddDialog(it.data[0].discountPrice.toInt())
                        dialog.show(activity1.supportFragmentManager,"CouponDialog")
                    }
                }
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RewardsFragment()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_rewards
}