package `in`.bizeneed.activity

import `in`.bizeneed.BuildConfig
import `in`.bizeneed.R
import `in`.bizeneed.adapter.CouponAdapter
import `in`.bizeneed.databinding.ActivitySummaryBinding
import `in`.bizeneed.dialog.PayLaterDialog
import `in`.bizeneed.dialog.PaymentStatus
import `in`.bizeneed.extras.*
import `in`.bizeneed.model.OrderModel
import `in`.bizeneed.response.CouponData
import `in`.bizeneed.response.OrderData
import `in`.bizeneed.response.SubCategoryData
import `in`.bizeneed.response.User
import `in`.bizeneed.rest.Coroutines
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.razorpay.RazorpayClient
import kotlinx.android.synthetic.main.activity_summary.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Summary : BaseActivity<ActivitySummaryBinding>(), PaymentResultListener {

    private lateinit var user: User
    private lateinit var subCategoryData: SubCategoryData
    private var amountToBePaid: Int = 0
    private var orderId: String = ""
    private lateinit var couponAdapter: CouponAdapter
    private var lastAppliedCouponPos = -1
    private var walletDeductAmount: Int = 0
    private var amount: Int = 0
    private var cashBack: Int = 0
    private var discount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val value = intent.getStringExtra(Constants.DATA)
        subCategoryData = Gson().fromJson(value, SubCategoryData::class.java)

        user = AppPrefData.user()!!
//        binding.userName.text = user.name
//        binding.userAddress.text = (user.address + ", " + user.city + ", " + user.state)
//        binding.userMobile.text = user.mobile
        binding.promoCode.text = subCategoryData.promoCode
        binding.mrp.text = ("\u20B9${subCategoryData.mrp}")
        binding.sellingPrice.text = ("\u20B9${subCategoryData.sellingPrice}")
        binding.promoSummary.text =
            ("On Successful payment, ${subCategoryData.discount}% cashback will be added in your wallet.")
        amountToBePaid = subCategoryData.sellingPrice.toInt()
        binding.discount.text =
            ("- \u20B9${(subCategoryData.mrp.toInt() - subCategoryData.sellingPrice.toInt())}")
        binding.promoDiscount.text = ("- \u20B90")

        //binding.walletDeductText.text = ("Deduct ${subCategoryData.walletWithdrawalPercent}% from my wallet")
        //binding.crossedPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        binding.walletLayout.visibility = View.GONE

        couponList()
        currentBalance()

        binding.registerBtn.setOnClickListener {
            var walletDeduct = 0
            if (binding.walletCheckBox.isChecked) {
                amount = if (walletDeductAmount != 0) {
                    if (walletDeductAmount < amountToBePaid) {
                        amountToBePaid - walletDeductAmount
                    } else {
                        0
                    }
                } else {
                    amountToBePaid
                }
                walletDeduct = walletDeductAmount
            } else {
                amount = amountToBePaid
                walletDeduct = 0
            }
            cashBack = ((amount * subCategoryData.discount.toInt()) / 100)
            val dialog = PayLaterDialog(
                subCategoryData.sellingPrice.toInt(),
                (subCategoryData.sellingPrice.toInt() - amount),
                cashBack,
                walletDeduct,
                amount,
                false
            )
            dialog.show(supportFragmentManager, "PaymentDialog")
        }

//        binding.changeAddress.setOnClickListener { onBackPressed() }

        binding.payLaterBtn.setOnClickListener {
            if (binding.walletCheckBox.isChecked) {
                binding.walletCheckBox.isChecked = false
                checkBoxHandle()
            }
            amount = amountToBePaid + discount
            cashBack = 0
            val dialog = PayLaterDialog(
                subCategoryData.sellingPrice.toInt(),
                0,
                cashBack,
                0,
                amount,
                true
            )
            dialog.show(supportFragmentManager, "PaymentDialog")
        }

        binding.walletCheckBox.setOnClickListener {
            checkBoxHandle()
        }

    }

    private fun checkBoxHandle() {
        if (walletCheckBox.isChecked) {
            if (walletDeductAmount != 0) {
                if (walletDeductAmount < amountToBePaid) {
                    binding.sellingPrice.text = ("\u20B9${amountToBePaid - walletDeductAmount}")
                } else {
                    binding.sellingPrice.text = ("\u20B90")
                }
            } else {
                binding.sellingPrice.text = ("\u20B9$amountToBePaid")
            }
        } else {
            binding.sellingPrice.text = ("\u20B9$amountToBePaid")
        }
    }

    private fun currentBalance() {
        myViewModel.currentBalance().observe(this, Observer {
            it?.let {
                AppPrefData.walletAmount(it)
                if (it.toInt() > 0) {
                    binding.walletLayout.visibility = View.VISIBLE
                    binding.walletDeduct.text = ("- \u20B9$it")
                    walletDeductAmount = if (amountToBePaid > it.toInt()) {
                        it.toInt()
                    } else {
                        amountToBePaid
                    }

                } else {
                    binding.walletLayout.visibility = View.GONE
                }
            }
        })
    }

    fun applyCoupon(couponData: CouponData, position: Int) {
        if (lastAppliedCouponPos != position) {
            showProgressBar(null)
            myViewModel.checkPromoCode(subCategoryData.name, couponData.couponCode).observe(this,
                Observer {
                    hideProgress()
                    it?.let {
                        if (it.success.toInt() == 1 && it.data[0].isValid == "Valid") {
                            val discountPrice = it.data[0].discountPrice
                            if (lastAppliedCouponPos != -1) {
                                couponAdapter.setSelected(false, lastAppliedCouponPos)
                            }
                            lastAppliedCouponPos = position
                            couponAdapter.setSelected(true, lastAppliedCouponPos)
                            binding.promoDiscount.text = ("- \u20B9$discountPrice")
                            amountToBePaid =
                                subCategoryData.sellingPrice.toInt() - discountPrice.toInt()
                            discount = discountPrice.toInt()
                            checkBoxHandle()
                            Toast.makeText(
                                this,
                                "Coupon is Successfully Applied",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
        } else {
            Toast.makeText(this, "Coupon is already Applied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun couponList() {
        showProgressBar(null)
        binding.couponRcv.layoutManager = LinearLayoutManager(this)
        myViewModel.fetchCoupons(subCategoryData.name).observe(this, Observer {
            hideProgress()
            it?.let {
                val list: ArrayList<CouponData> = ArrayList()
                for (item in it.data) {
                    if (item.couponCode != subCategoryData.promoCode) {
                        list.add(item)
                    }
                }
                couponAdapter = CouponAdapter(this)
                couponAdapter.addItems(list)
                binding.couponRcv.adapter = couponAdapter
                couponAdapter.notifyDataSetChanged()
            }
        })
    }

    fun proceedToPay() {
        if (isConnected(this)) {
            showProgressBar("Processing Order")
            val checkout = Checkout()
            checkout.setKeyID(BuildConfig.RZP_KEY)
            checkout.setImage(R.drawable.company_logo)
            val razorPay = RazorpayClient(BuildConfig.RZP_KEY, BuildConfig.RZP_SECRET)

            try {

                // creating orderID using razorPay
                val orderRequest = JSONObject()
                orderRequest.put("amount", amount * 100)
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
                    options.put("amount", amount * 100)
                    options.put("prefill.email", AppPrefData.user()!!.email)
                    options.put("prefill.contact", AppPrefData.user()!!.mobile)
                    options.put("payment_capture", 1)
                    options.put("theme.color", "#538E01")

                    checkout.open(this, options)

                }

            } catch (e: Exception) {
                showPaymentDialog(false)
                hideProgress()
            }
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_summary

    override fun onPaymentError(p0: Int, p1: String?) {
        showPaymentDialog(false)
    }

    private fun showPaymentDialog(isSuccess: Boolean) {
        Coroutines.main {
            hideProgress()
            val dialog = PaymentStatus(isSuccess)
            dialog.show(supportFragmentManager, "PaymentDialog")
        }
    }

    override fun onPaymentSuccess(paymentId: String?) {
        proceedOrderApi("Online")
    }

    fun proceedOrderApi(paymentMode: String) {

        showProgressBar("Processing Order")

        if (orderId.isEmpty()) {
            val randomOrderId = System.currentTimeMillis()
            orderId = randomOrderId.toString()
        }

        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        var currDate = df.format(c)
        currDate = "$currDate.000Z"
        val currDate1 = currDate.replace(" ", "T")

        val date = getDate(currDate1)
        val time = getTime(currDate1)

        val orderModel = OrderModel(
            orderId,
            user.id.toString(),
            user.name!!,
            user.address!!,
            user.state!!,
            user.mobile,
            user.email!!,
            amount.toString(),
            time,
            date,
            subCategoryData.name,
            paymentMode,
            cashBack.toString()
        )
        myViewModel.createOrder(orderModel).observe(this, Observer {

            if (it == null) {
                hideProgress()
                showPaymentDialog(false)
                return@Observer
            }

            if (it) {
                val orderData = OrderData(
                    orderId, subCategoryData.name, user.id.toString(),
                    user.name!!, user.address!!, user.state!!,
                    user.mobile, amount.toString(), date,
                    time, "1", paymentMode, arrayListOf(subCategoryData)
                )
                createOrderNotification(this, subCategoryData, orderData, subCategoryData.name)

                if (walletDeductAmount > 0 && paymentMode == "Online") {
                    deductFromWallet()
                } else {
                    hideProgress()
                    showPaymentDialog(true)
                }
            } else {
                hideProgress()
                showPaymentDialog(false)
            }
        })
    }

    private fun deductFromWallet() {
        myViewModel.updateBalance(walletDeductAmount.toString()).observe(this,
            Observer {
                hideProgress()
                if (it == null) {
                    showPaymentDialog(false)
                }

                showPaymentDialog(true)
            })
    }
}