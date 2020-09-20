package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivityProfileBinding
import `in`.bizeneed.extras.AppPrefData
import `in`.bizeneed.extras.isConnected
import `in`.bizeneed.model.UpdateModel
import `in`.bizeneed.response.User
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer

class Profile : BaseActivity<ActivityProfileBinding>() {

    private var user: User?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = AppPrefData.user()!!
        user?.let { user1 ->
            user1.mobile.let {
                binding.mobile.text = it
            }
            user1.name.let {
                binding.nameEdt.setText(it)
            }
            user1.email.let {
                binding.emailEdt.setText(it)
            }
            user1.address.let {
                binding.addressEdt.setText(it)
            }
            if (user1.city != null){
                binding.cityEdt.setText(user1.city)
            }else{
                binding.cityEdt.setText(("New Delhi"))
            }
            user1.pincode.let {
                binding.pinCodeEdt.setText(it)
            }
            user1.state.let {
                binding.stateEdt.setText(it)
            }
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.updateProfile.setOnClickListener {
            if (binding.addressEdt.text.toString().isEmpty()
                || binding.cityEdt.text.toString().isEmpty()
                || binding.stateEdt.text.toString().isEmpty()
                || binding.pinCodeEdt.text.toString().isEmpty()
                || binding.nameEdt.text.toString().isEmpty()
                || binding.emailEdt.text.toString().isEmpty()
            ) {

                Toast.makeText(this, "Please fill the credentials", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            updateData()
        }
    }

    private fun updateData() {
        showProgressBar(null)
        if (user?.address != binding.addressEdt.text.toString()
            || user?.city != binding.cityEdt.text.toString()
            || user?.pincode != binding.pinCodeEdt.text.toString()
            || user?.state != binding.stateEdt.text.toString()
            || user?.name != binding.nameEdt.text.toString()
            || user?.email != binding.emailEdt.text.toString()
        ) {

            val newUser = User(
                user!!.id, user!!.mobile,
                binding.nameEdt.text.toString(), binding.emailEdt.text.toString(),
                binding.addressEdt.text.toString(), binding.cityEdt.text.toString(),
                binding.stateEdt.text.toString(), binding.pinCodeEdt.text.toString()
            )

            val updateModel = UpdateModel(
                newUser.name!!, newUser.email!!, newUser.address!!,
                newUser.city!!, newUser.state!!, newUser.pincode!!, newUser.id.toString()
            )

            if (isConnected(this)){
                myViewModel.updateUser(updateModel).observe(this, Observer {
                    hideProgress()
                    it?.let {
                        if (it){
                            AppPrefData.user(newUser)
                            Toast.makeText(this,"Profile successfully Updated",Toast.LENGTH_SHORT).show()
                            onBackPressed()
                        }
                    }
                })
            }else{
                hideProgress()
                Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_profile
}