package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.adapter.CommentAdapter
import `in`.bizeneed.adapter.DetailListAdapter
import `in`.bizeneed.adapter.ServiceImagesAdapter
import `in`.bizeneed.databinding.ActivityServiceDetailBinding
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.extras.formattedRating
import `in`.bizeneed.response.OrderData
import `in`.bizeneed.response.SubCategoryData
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.google.gson.Gson
import java.util.*

class ServiceDetail : BaseActivity<ActivityServiceDetailBinding>() {

    private lateinit var subCategoryData: SubCategoryData
    private var isPurchased: Boolean = false
    private var orderDetail: OrderData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val value = intent.getStringExtra(Constants.DATA)
        isPurchased = intent.getBooleanExtra(Constants.IS_PURCHASED, false)
        subCategoryData = Gson().fromJson(value, SubCategoryData::class.java)

        Glide.with(this)
            .asBitmap()
            .load(Constants.IMAGE_URL + subCategoryData.headerImage)
            .placeholder(R.drawable.boy)
            .override(1600, 1600)
            .into(BitmapImageViewTarget(binding.image))

        binding.name.text = subCategoryData.name
        binding.title.text = subCategoryData.name
        binding.offer.text =
            ("Use Code: ${subCategoryData.promoCode} to get ${subCategoryData.discount}% Additional Discount")
        binding.crossedPrice.text = ("\u20B9${subCategoryData.mrp}")
        binding.currentPrice.text = ("\u20B9${subCategoryData.sellingPrice}*")

        if (isPurchased) {
            val order = intent.getStringExtra(Constants.ORDER_DATA)
            orderDetail = Gson().fromJson(order, OrderData::class.java)
            if (orderDetail!!.completed.toInt() == 0 &&
                orderDetail!!.paymentType.toLowerCase(Locale.getDefault()) == "offline"
            ) {
                binding.bookTxt.text = ("Booked")
                binding.cancelLayout.visibility = View.VISIBLE
                binding.bookServiceBtn.visibility = View.GONE
            } else if (orderDetail!!.completed.toInt() == 2){
                binding.bookTxt.text = ("Cancelled")
                binding.bookServiceBtn.text = ("Cancelled")
                binding.cancelLayout.visibility = View.GONE
                binding.bookServiceBtn.visibility = View.VISIBLE
            }else{
                binding.bookTxt.text = ("Booked")
                binding.bookServiceBtn.text = ("Booked")
                binding.cancelLayout.visibility = View.GONE
                binding.bookServiceBtn.visibility = View.VISIBLE
            }
        } else {
            binding.bookTxt.text = ("Book")
            binding.cancelLayout.visibility = View.GONE
            binding.bookServiceBtn.visibility = View.VISIBLE
            binding.bookServiceBtn.text = ("Book Service")
        }

        binding.cancelBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Cancel Order")
            builder.setMessage("Do you want to cancel the order ??")
            builder.setPositiveButton("Cancel Order") { dialogInterface, _ ->
                dialogInterface.dismiss()
                cancelOrder()
            }
            builder.setNegativeButton("Dismiss"){ dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        binding.backBtn.setOnClickListener { onBackPressed() }

        imagesList()
        commentList()
        detailList()

        binding.crossedPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        binding.bookServiceBtn.setOnClickListener {
            if (!isPurchased) {
                val intent = Intent(this, Registration::class.java)
                intent.putExtra(Constants.DATA, Gson().toJson(subCategoryData))
                startActivity(intent)
            }
        }

        binding.bookBtn.setOnClickListener {
            if (!isPurchased) {
                val intent = Intent(this, Registration::class.java)
                intent.putExtra(Constants.DATA, Gson().toJson(subCategoryData))
                startActivity(intent)
            }
        }
    }

    private fun cancelOrder() {
        if (isPurchased && orderDetail != null) {
            showProgressBar(null)
            myViewModel.cancelOrder(
                orderDetail!!.orderId,
                orderDetail!!.name,
                orderDetail!!.totalAmount,
                orderDetail!!.subCategoryName
            ).observe(this, Observer {
                hideProgress()
                it?.let {
                    if (it.errorMessage.toLowerCase(Locale.getDefault()) == "success"){
                        Toast.makeText(this,"Order is successfully Cancelled",Toast.LENGTH_SHORT).show()
                        binding.bookTxt.text = ("Cancelled")
                        binding.bookServiceBtn.text = ("Cancelled")
                        binding.cancelLayout.visibility = View.GONE
                        binding.bookServiceBtn.visibility = View.VISIBLE
                    }
                }
            })
        }
    }

    private fun commentList() {
        binding.commentRcv.layoutManager = LinearLayoutManager(this)

        showProgressBar(null)
        myViewModel.fetchAllComment(subCategoryData.name).observe(this, Observer {
            hideProgress()
            it?.let {
                var sumRating = 0F
                for (item in it.data) {
                    sumRating += item.rating.toFloat()
                }

                if (it.data.isNotEmpty()) {
                    binding.rating.text = (sumRating / (it.data.size)).toString()
                } else {
                    binding.rating.text = ("0.0")
                }
                binding.ratingCount.text = ("(${formattedRating(it.data.size)} ratings)")

                if (it.data.isEmpty()) {
                    binding.commentLayout.visibility = View.GONE
                } else {
                    binding.commentLayout.visibility = View.VISIBLE
                }

                val commentAdapter = CommentAdapter(this)
                commentAdapter.addItems(it.data)
                binding.commentRcv.adapter = commentAdapter
                commentAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun detailList() {

        if (subCategoryData.mulitpleDescription != null) {
            binding.listDescRcv.layoutManager = LinearLayoutManager(this)
            val detailListAdapter = DetailListAdapter(this)
            detailListAdapter.addItems(subCategoryData.mulitpleDescription!!)
            binding.listDescRcv.adapter = detailListAdapter
            detailListAdapter.notifyDataSetChanged()
        }
    }

    private fun imagesList() {
        val servicesAdapter = ServiceImagesAdapter(this, subCategoryData.demoImages)
        servicesAdapter.addItems(subCategoryData.demoImages)
        binding.detailServicesRcv.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.detailServicesRcv.adapter = servicesAdapter
        servicesAdapter.notifyDataSetChanged()
    }

    override fun getLayoutRes(): Int = R.layout.activity_service_detail
}