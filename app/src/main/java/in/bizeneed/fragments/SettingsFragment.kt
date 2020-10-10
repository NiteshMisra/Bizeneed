package `in`.bizeneed.fragments

import `in`.bizeneed.BuildConfig
import `in`.bizeneed.R
import `in`.bizeneed.activity.*
import `in`.bizeneed.databinding.FragmentProfileBinding
import `in`.bizeneed.extras.AppPrefData
import `in`.bizeneed.extras.getUserReferCode
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer

class SettingsFragment : BaseFragment<FragmentProfileBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = AppPrefData.user()!!
        binding.walletProgress.visibility = View.VISIBLE
        binding.walletBalance.visibility = View.GONE
        myViewModel.currentBalance().observe(activity1, Observer {
            binding.walletProgress.visibility = View.GONE
            binding.walletBalance.visibility = View.VISIBLE
            if (it == null){
                binding.walletBalance.text = ("\u20B9 ${user.wallet}")
            }else{
                if (it.toInt() > 0){
                    user.wallet = it
                    AppPrefData.user(user)
                    binding.walletBalance.text = ("\u20B9 $it")
                }else{
                    user.wallet = "0"
                    AppPrefData.user(user)
                    binding.walletBalance.text = ("\u20B9 0")
                }
            }
        })

        binding.logOut.setOnClickListener {
            val builder = AlertDialog.Builder(activity1)
            builder.setMessage("Are you sure you want to Log-out ..?")
            builder.setPositiveButton("Ok"
            ) { p0, _ ->
                p0.dismiss()
                AppPrefData.isLogin(false)
                val intent = Intent(activity1,GetStartedActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            builder.setNegativeButton("Cancel"){p0,_ ->
                p0.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        binding.profile.setOnClickListener {
            startActivity(Intent(activity1,Profile::class.java))
        }

        binding.share.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"Hey check out my app: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + ". Use my Refer Code : ${getUserReferCode()}, to get cashBack in your wallet")
            intent.type = "text/plain"
            startActivity(intent)
        }

        binding.contactUs.setOnClickListener {
            startActivity(Intent(activity1,ContactUs::class.java))
        }

        binding.walletLayout.setOnClickListener {
            val user1 = AppPrefData.user()!!
            if(user1.wallet != null && user1.wallet!!.isNotEmpty() && (user1.wallet!!.toInt() > 0 )){
                startActivity(Intent(activity1,WalletHistory::class.java))
            }
        }

        binding.policies.setOnClickListener {
            startActivity(Intent(activity1,Policies::class.java))
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_profile
}