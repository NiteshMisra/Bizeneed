@file:Suppress("DEPRECATION")

package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivityProfileBinding
import `in`.bizeneed.extras.*
import `in`.bizeneed.model.UpdateModel
import `in`.bizeneed.response.User
import `in`.bizeneed.rest.Coroutines
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.iceteck.silicompressorr.SiliCompressor
import java.io.ByteArrayOutputStream
import java.io.File

class Profile : BaseActivity<ActivityProfileBinding>(), AdapterView.OnItemSelectedListener {

    private lateinit var user: User
    private var profile: String = ""
    private var state : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = AppPrefData.user()!!
        binding.mobile.text = user.mobile
        binding.nameEdt.setText(user.name)
        binding.emailEdt.setText(user.email)
        user.address?.let {
            binding.addressEdt.setText(it)
        }
        if (user.city != null) {
            binding.cityEdt.setText(user.city)
        } else {
            binding.cityEdt.setText(("New Delhi"))
        }
        if(!user.businessName.equals("null"))
        {
            binding.businessEdt.setText(user.businessName)
        }
        if(!user.gst.equals("null"))
        {
            binding.gstEdt.setText(user.gst)
        }
        user.pincode?.let {
            binding.pinCodeEdt.setText(it)
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item,getStates())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.stateSpinner.adapter = adapter
        binding.stateSpinner.onItemSelectedListener = this

        user.state?.let {
            if (user.state!!.isEmpty()){
                binding.stateSpinner.setSelection(0)
            }else{
                val list = getStates()
                val index = list.indexOf(user.state!!)
                binding.stateSpinner.setSelection(index)
            }
        }

        user.profile?.let {
            Glide.with(this)
                .asBitmap()
                .load(Constants.PROFILE_URL + user.profile)
                .placeholder(R.drawable.boy)
                .override(1600,1600)
                .into(BitmapImageViewTarget(binding.userProfile))
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.userProfile.setOnClickListener {

            TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("We need storage permission to get the image from the device.\n Please turn on permissions at [Setting] > [Permission]")
                .setPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .check()
        }

        binding.updateProfile.setOnClickListener {
            if (binding.addressEdt.text.toString().isEmpty()
                || binding.cityEdt.text.toString().isEmpty()
                || state.isEmpty()
                || binding.pinCodeEdt.text.toString().isEmpty()
                || binding.nameEdt.text.toString().isEmpty()
                || binding.emailEdt.text.toString().isEmpty()
            ) {

                Toast.makeText(this, "Please fill the credentials", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isEmailValid(binding.emailEdt.text.toString())){
                Toast.makeText(this, "Please enter valid email-id", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            updateData()
        }
    }

    private val permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i, 1)
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {}
    }

    private fun updateData() {
        showProgressBar(null)
        if (user.address != binding.addressEdt.text.toString()
            || user.city != binding.cityEdt.text.toString()
            || user.pincode != binding.pinCodeEdt.text.toString()
            || (user.state != state && state.isNotEmpty())
            || user.name != binding.nameEdt.text.toString()
            || user.email != binding.emailEdt.text.toString()
            || user.profile != profile
            || user.gst != binding.gstEdt.text.toString()
            || user.businessName != binding.businessEdt.text.toString()
        ) {

            var profile2 = ""
            if (user.profile != null){
                profile2 = user.profile!!
            }

            val newUser = User(
                user.id, user.mobile,
                binding.nameEdt.text.toString(), binding.emailEdt.text.toString(),
                binding.addressEdt.text.toString(), binding.cityEdt.text.toString(),
                state, binding.pinCodeEdt.text.toString(),
                user.wallet, profile2,binding.gstEdt.text.toString(),binding.businessEdt.text.toString()
            )

            val updateModel = UpdateModel(
                newUser.name!!, newUser.email!!, newUser.address!!,
                newUser.city!!, newUser.state!!, newUser.pincode!!, newUser.id.toString(), profile,
                getUserReferCode(),newUser.gst!!,newUser.businessName!!
            )

            myViewModel.updateUser(updateModel,newUser).observe(this, Observer {
                hideProgress()

                if (it == null){
                    Toast.makeText(this,"Failed to update",Toast.LENGTH_SHORT).show()
                    return@Observer
                }

                Toast.makeText(this, "Profile successfully Updated", Toast.LENGTH_SHORT).show()
                onBackPressed()

            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            showProgressBar(null)
            Coroutines.io {
                val uri = data.data
                if (uri != null) {
                    val path = getRealPathFromURI(this, uri)
                   if (path != null){
                       val bitmap = SiliCompressor.with(this).getCompressBitmap(path)
                       val file: File = saveImageToFile(this, bitmap)!!
                       if (bitmap != null) {
                           val stream = ByteArrayOutputStream()
                           bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream)
                           val byteArray = stream.toByteArray()
                           Coroutines.main {
                               hideProgress()
                               binding.userProfile.setImageBitmap(BitmapFactory.decodeFile(file.absolutePath))
                               profile = Base64.encodeToString(byteArray, Base64.DEFAULT)
                           }
                       } else {
                           hideProgress()
                       }
                   }
                } else {
                    hideProgress()
                }
            }
        }
    }

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

    override fun getLayoutRes(): Int = R.layout.activity_profile
}