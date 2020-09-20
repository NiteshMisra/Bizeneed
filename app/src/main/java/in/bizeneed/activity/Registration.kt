package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivityRegistrationBinding
import `in`.bizeneed.extras.AppPrefData
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.extras.isConnected
import `in`.bizeneed.model.UpdateModel
import `in`.bizeneed.response.SubCategoryData
import `in`.bizeneed.response.User
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.gson.Gson

class Registration : BaseActivity<ActivityRegistrationBinding>() {

    private var user: User? = null
    private lateinit var subCategoryData : SubCategoryData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val value = intent.getStringExtra(Constants.DATA)
        subCategoryData = Gson().fromJson(value, SubCategoryData::class.java)

        user = AppPrefData.user()
        user?.let { user1 ->
            user1.name?.let {
                binding.nameEdt.setText(it)
            }
            user1.address.let {
                binding.addressEdt.setText(it)
            }
            user1.email.let {
                binding.emailEdt.setText(it)
            }
            user1.mobile.let {
                binding.mobileEdt.text = it
            }
            user1.state.let {
                binding.stateEdt.setText(it)
            }
        }

        binding.continueBtn.setOnClickListener {

            if (binding.nameEdt.text.toString().isEmpty()
                || binding.stateEdt.text.toString().isEmpty()
                || binding.addressEdt.text.toString().isEmpty()
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
        if (user?.name != binding.nameEdt.text.toString()
            || user?.address != binding.addressEdt.text.toString()
            || user?.email != binding.emailEdt.text.toString()
            || user?.state != binding.stateEdt.text.toString()
        ) {

            val newUser = User(
                user!!.id, binding.mobileEdt.text.toString(),
                binding.nameEdt.text.toString(), binding.emailEdt.text.toString(),
                binding.addressEdt.text.toString(), user!!.city,
                binding.stateEdt.text.toString(), user!!.pincode
            )

            var pinCode = ""
            if(newUser.pincode != null){
                pinCode = newUser.pincode
            }
            var city = ""
            if(newUser.city != null){
                city = newUser.city
            }
            val updateModel = UpdateModel(
                newUser.name!!, newUser.email!!, newUser.address!!,
                city, newUser.state!!, pinCode, newUser.id.toString()
            )

            if (isConnected(this)){
                myViewModel.updateUser(updateModel).observe(this, Observer {
                    hideProgress()
                    it?.let {
                        if (it){
                            AppPrefData.user(newUser)
                            val intent = Intent(this,Summary::class.java)
                            intent.putExtra(Constants.DATA,Gson().toJson(subCategoryData))
                            startActivity(intent)
                        }
                    }
                })
            }else{
                hideProgress()
                Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show()
            }

        } else {
            hideProgress()
            val intent = Intent(this,Summary::class.java)
            intent.putExtra(Constants.DATA,Gson().toJson(subCategoryData))
            startActivity(intent)
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_registration
}