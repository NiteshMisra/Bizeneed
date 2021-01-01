package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivityRegistrationBinding
import `in`.bizeneed.extras.*
import `in`.bizeneed.model.UpdateModel
import `in`.bizeneed.response.SubCategoryData
import `in`.bizeneed.response.User
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.gson.Gson

class Registration : BaseActivity<ActivityRegistrationBinding>(), AdapterView.OnItemSelectedListener {

    private lateinit var user: User
    private lateinit var subCategoryData : SubCategoryData
    private var state : String = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val value = intent.getStringExtra(Constants.DATA)
        subCategoryData = Gson().fromJson(value, SubCategoryData::class.java)

        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item,getStates())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.stateSpinner.adapter = adapter
        binding.stateSpinner.onItemSelectedListener = this

        binding.backBtn.setOnClickListener { onBackPressed() }

        user = AppPrefData.user()!!
        binding.nameEdt.setText(user.name)
        binding.mobileEdt.text = user.mobile
        binding.emailEdt.setText(user.email)
        if(!user.businessName.equals("null"))
        {
            binding.businessEdt.setText(user.businessName)
        }
        if(!user.gst.equals("null"))
        {
            binding.gstEdt.setText(user.gst)
        }
        user.state?.let {
            if (user.state!!.isEmpty()){
                binding.stateSpinner.setSelection(0)
            }else{
                val list = getStates()
                val index = list.indexOf(user.state!!)
                binding.stateSpinner.setSelection(index)
            }
        }

        binding.continueBtn.setOnClickListener {

            if (binding.nameEdt.text.toString().isEmpty()
                || binding.emailEdt.text.toString().isEmpty()
                || state.isEmpty()
            ) {

                Toast.makeText(this, "Please fill the credentials", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isEmailValid(binding.emailEdt.text.toString())){
                Toast.makeText(this, "Please enter valid email-id", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.additionalMsg.text.toString().isNotEmpty())
            {
                sendAdditionalMsg()
            }else
            { updateData() }
        }

    }

    private fun sendAdditionalMsg()
    {
        showProgressBar(null)
        myViewModel.sendFeedBack(binding.additionalMsg.text.toString()).observe(this, Observer {
            hideProgress()
            it.let {
                binding.additionalMsg.setText("")
                Toast.makeText(this,"Message for the information",Toast.LENGTH_SHORT).show()
                updateData()
            }
        })
    }

    private fun updateData() {
        showProgressBar(null)
        if (user.name != binding.nameEdt.text.toString()
            || user.email != binding.emailEdt.text.toString()
            || user.gst != binding.gstEdt.text.toString()
            || user.businessName != binding.gstEdt.text.toString()
            || (user.state != state && state.isNotEmpty())
        )
        {

            var address = ""
            if (user.address != null){
                address = user.address!!
            }


            val newUser = User(
                user.id, user.mobile,
                binding.nameEdt.text.toString(), binding.emailEdt.text.toString(),
                address, user.city,
                state, user.pincode, user.wallet,user.profile,binding.gstEdt.text.toString()
                ,binding.businessEdt.text.toString()
            )

            var pinCode = ""
            if(newUser.pincode != null){
                pinCode = newUser.pincode
            }
            var city = ""
            if(newUser.city != null){
                city = newUser.city
            }
            val profile = ""
            val updateModel = UpdateModel(
                newUser.name!!, newUser.email!!, newUser.address!!,
                city, newUser.state!!, pinCode, newUser.id.toString(),profile, getUserReferCode()
                ,newUser.gst!!,
                newUser.businessName!!
            )

            myViewModel.updateUser(updateModel,newUser).observe(this, Observer {
                hideProgress()
                if (it == null){
                    Toast.makeText(this,"Failed to update",Toast.LENGTH_SHORT).show()
                    return@Observer
                }

                if (it){
                    val intent = Intent(this,Summary::class.java)
                    intent.putExtra(Constants.DATA,Gson().toJson(subCategoryData))
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"Failed to update",Toast.LENGTH_SHORT).show()
                }
            })

        }
        else {
            hideProgress()
            val intent = Intent(this,Summary::class.java)
            intent.putExtra(Constants.DATA,Gson().toJson(subCategoryData))
            startActivity(intent)
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_registration

    override fun onNothingSelected(p0: AdapterView<*>?) {
        state = ""
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        state = if(p2 != 0){
            p0?.getItemAtPosition(p2).toString()
        }else{
            ""
        }
    }
}