package `in`.bizeneed.activity

import `in`.bizeneed.BuildConfig
import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivitySummaryBinding
import `in`.bizeneed.extras.AppPrefData
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.extras.isConnected
import `in`.bizeneed.model.OrderModel
import `in`.bizeneed.response.SubCategoryData
import `in`.bizeneed.response.User
import `in`.bizeneed.rest.Coroutines
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.razorpay.RazorpayClient
import org.json.JSONObject

class Summary : BaseActivity<ActivitySummaryBinding>(), PaymentResultListener {

    private lateinit var user : User
    private lateinit var subCategoryData: SubCategoryData
    private var amountToBePaid : Int = 0
    private var orderId : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val value = intent.getStringExtra(Constants.DATA)
        subCategoryData = Gson().fromJson(value, SubCategoryData::class.java)

        user = AppPrefData.user()!!
        binding.userName.text = user.name
        binding.userAddress.text = (user.address + ", " + user.city + ", " + user.state)
        binding.userMobile.text = user.mobile
        binding.promoCode.text = subCategoryData.promoCode
        binding.crossedPrice.text = ("\u20B9${subCategoryData.mrp}")
        binding.currentPrice.text = ("\u20B9${subCategoryData.sellingPrice}")
        binding.promoSummary.text = ("Promo Code: ${subCategoryData.promoCode} is applied to give you ${subCategoryData.discount}% additional discounts")
        val offer = (subCategoryData.sellingPrice.toInt() * subCategoryData.discount.toInt())/100
        binding.promoCodeDiscount.text = ("- \u20B9${offer}")
        amountToBePaid = subCategoryData.sellingPrice.toInt() - offer
        binding.amountToBePaid.text = ("\u20B9${amountToBePaid}")

        binding.crossedPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        binding.registerBtn.setOnClickListener {
            proceedToPay()
        }

        binding.changeAddress.setOnClickListener {
            onBackPressed()
        }
    }

    private fun proceedToPay() {
        if (isConnected(this)){
            showProgressBar(null)
            val checkout = Checkout()
            checkout.setKeyID(BuildConfig.RZP_KEY);
            checkout.setImage(R.drawable.company_logo);
            val razorPay = RazorpayClient(BuildConfig.RZP_KEY, BuildConfig.RZP_SECRET)

            try {

                // creating orderID using razorPay
                val orderRequest = JSONObject()
                orderRequest.put("amount", amountToBePaid * 100)
                orderRequest.put("currency", "INR")

                Coroutines.io {
                    val order = razorPay.Orders?.create(orderRequest)
                    val jsonObject = JSONObject(order?.toString()!!)
                    orderId = jsonObject.getString("id")

                    // opens checkOut form
                    val options = JSONObject()

                    options.put("order_id", orderId)
                    options.put("name", getString(R.string.app_name))
                    options.put("description", "Add your description here")
                    options.put("currency", "INR")
                    options.put("amount", amountToBePaid * 100)
                    options.put("prefill.email", AppPrefData.user()!!.email)
                    options.put("prefill.contact", AppPrefData.user()!!.mobile)
                    options.put("payment_capture", 1)
                    options.put("theme.color","#538E01")

                    Log.e("RegistrationActivity", options.toString())
                    checkout.open(this, options)

                }

            } catch (e: Exception) {
                Log.e("RegistrationActivity", "Error in starting Razorpay Checkout : ", e);
                hideProgress()
            }
        }else{
            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_summary

    override fun onPaymentError(p0: Int, p1: String?) {
        hideProgress()
        Toast.makeText(this,"Payment Failed",Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentSuccess(paymentId : String?) {
        if (isConnected(this)){
            val orderModel = OrderModel(
                orderId, user.id.toString(), user.name!!, user.address!!, user.state!!,
                user.mobile, user.email!!, amountToBePaid.toString(), "", "",subCategoryData.name
            )
            myViewModel.createOrder(orderModel).observe(this, Observer {
                hideProgress()
                it?.let {
                    if (it){
                        Toast.makeText(this,"Payment Successful",Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }else{
            hideProgress()
            Toast.makeText(this,"Internet Connection is Lost. Contact our Team for Refund",Toast.LENGTH_SHORT).show()
        }
    }
}