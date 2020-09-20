package `in`.bizeneed.fragments

import `in`.bizeneed.R
import `in`.bizeneed.activity.Login
import `in`.bizeneed.activity.Profile
import `in`.bizeneed.databinding.FragmentProfileBinding
import `in`.bizeneed.extras.AppPrefData
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog

class SettingsFragment : BaseFragment<FragmentProfileBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logOut.setOnClickListener {
            val builder = AlertDialog.Builder(activity1)
            builder.setMessage("Are you sure you want to Log-out ..?")
            builder.setPositiveButton("Ok"
            ) { p0, _ ->
                p0.dismiss()
                AppPrefData.isLogin(false)
                val intent = Intent(activity1,Login::class.java)
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
    }




    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_profile
}