package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivityProfileRegistrationBinding
import `in`.bizeneed.dialog.CouponAddDialog
import `in`.bizeneed.extras.AppPrefData
import `in`.bizeneed.extras.getBitmap
import `in`.bizeneed.extras.getUserReferCode
import `in`.bizeneed.extras.isEmailValid
import `in`.bizeneed.model.UpdateModel
import `in`.bizeneed.response.User
import `in`.bizeneed.rest.Coroutines
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.lifecycle.Observer
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.coroutines.delay
import java.io.ByteArrayOutputStream
import java.util.*

class ProfileRegistration : BaseActivity<ActivityProfileRegistrationBinding>() {

    private lateinit var user: User
    private var profile = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = AppPrefData.user()!!
        binding.mobile.text = user.mobile

        binding.backBtn.setOnClickListener { onBackPressed() }

        binding.addBtn.setOnClickListener {
            if (binding.nameEdt.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.emailEdt.text.toString().isNotEmpty() && !isEmailValid(binding.emailEdt.text.toString())) {
                Toast.makeText(this, "Please enter valid email-id", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            updateData()

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

    }

    private val permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1)
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {}
    }

    private fun updateData() {
        showProgressBar(null)

        var wallet = ""
        if (user.wallet != null) {
            wallet = user.wallet!!
        }

        var email = ""
        if (binding.emailEdt.text.toString().trim().isNotEmpty()) {
            email = binding.emailEdt.text.toString().trim()
        }

        val newUser = User(
            user.id, user.mobile,
            binding.nameEdt.text.toString(), email,
            "", "",
            "", "",
            wallet, ""
        )

        val updateModel = UpdateModel(
            newUser.name!!,
            newUser.email!!,
            newUser.address!!,
            newUser.city!!,
            newUser.state!!,
            newUser.pincode!!,
            newUser.id.toString(),
            profile,
            getUserReferCode()
        )

        myViewModel.checkReferalNo(binding.couponCodeEdt.text.toString().trim(),binding.nameEdt.text.toString()).observe(this, Observer { it1 ->

            if (it1 == null){
                hideProgress()
                Toast.makeText(this,"Some error occurred,Try again Later",Toast.LENGTH_SHORT).show()
                return@Observer
            }

            if (it1.data[0].isReferalValid.toLowerCase(Locale.getDefault()) == "in valid"){
                hideProgress()
                Toast.makeText(this,"Invalid Refer Code",Toast.LENGTH_SHORT).show()
            }else{
                val dialog = CouponAddDialog(0)
                dialog.show(supportFragmentManager,"CouponDialog")

                Coroutines.main {
                    delay(2500)
                    updateApi(updateModel,newUser)
                }

            }

        })
    }

    private fun updateApi(updateModel : UpdateModel,newUser : User) {
        myViewModel.updateUser(updateModel, newUser).observe(this, Observer {
            hideProgress()
            if (it == null) {
                Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()
                return@Observer
            }

            if (it) {
                moveToNextActivity()
            } else {
                Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun moveToNextActivity() {
        hideProgress()
        Toast.makeText(this, "Profile successfully Updated", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            showProgressBar(null)
            Coroutines.io {
                val uri = data.data
                if (uri != null) {
                    val bitmap = getBitmap(this, uri)
                    if (bitmap != null) {
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream)
                        val byteArray = stream.toByteArray()
                        Coroutines.main {
                            hideProgress()
                            binding.userProfile.setImageBitmap(bitmap)
                            profile = Base64.encodeToString(byteArray, Base64.DEFAULT)
                        }
                    } else {
                        hideProgress()
                    }
                } else {
                    hideProgress()
                }
            }
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_profile_registration
}