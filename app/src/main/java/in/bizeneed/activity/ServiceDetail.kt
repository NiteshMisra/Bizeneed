package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.adapter.CommentAdapter
import `in`.bizeneed.adapter.DetailListAdapter
import `in`.bizeneed.adapter.ServiceImagesAdapter
import `in`.bizeneed.databinding.ActivityServiceDetailBinding
import `in`.bizeneed.databinding.ElementEffectivePriceBinding
import `in`.bizeneed.extras.AppPrefData
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
import com.ethanhua.skeleton.Skeleton
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
            .override(1600, 1600)
            .into(BitmapImageViewTarget(binding.image))

        binding.name.text = subCategoryData.name
        binding.title.text = subCategoryData.name
        binding.offer.text =
            ("Use Code: ${subCategoryData.promoCode} to get \u20B9 ${subCategoryData.discount} Additional Cashback")
        binding.crossedPrice.text = ("\u20B9${subCategoryData.mrp}")
        binding.currentPrice.text = ("\u20B9${subCategoryData.sellingPrice}*")
        binding.effectivePriceDescription.text =  ("Get it at just \u20B9${(subCategoryData.sellingPrice.toInt()-subCategoryData.discount.toInt())} with paying online")

        if (!subCategoryData.description.isNullOrEmpty()){
            binding.priceDesc.text = subCategoryData.description
        }else{
            binding.priceDesc.visibility = View.GONE
        }

        if (isPurchased) {
            val order = intent.getStringExtra(Constants.ORDER_DATA)
            orderDetail = Gson().fromJson(order, OrderData::class.java)
            binding.effectivePriceBox.visibility=View.GONE
            if(orderDetail!!.completed.toInt()!=1 && orderDetail!!.completed.toInt()!=2 && orderDetail!!.paymentType.toLowerCase(Locale.getDefault()) == "offline")
            {
                binding.payNow.visibility = View.VISIBLE
            }
            if (orderDetail!!.completed.toInt() == 0 &&
                orderDetail!!.paymentType.toLowerCase(Locale.getDefault()) == "offline"
            ) {
                binding.bookTxt.text = ("Placed")
                binding.cancelLayout.visibility = View.VISIBLE
                binding.bookServiceBtn.text = ("Order Placed")
                binding.bookServiceBtn.visibility = View.GONE
            } else if (orderDetail!!.completed.toInt() == 2){
                binding.bookTxt.text = ("Cancelled")
                binding.bookServiceBtn.text = ("Cancelled")
                binding.cancelLayout.visibility = View.GONE
                binding.bookServiceBtn.visibility = View.VISIBLE
            }else if(orderDetail!!.completed.toInt() == 1) {
                binding.bookTxt.text = ("Delivered")
                binding.bookServiceBtn.text = ("Work Delivered")
                binding.cancelLayout.visibility = View.GONE
                binding.bookServiceBtn.visibility = View.VISIBLE
            }
            else if(orderDetail!!.completed.toInt() == 3) {
                binding.bookTxt.text = ("Assigned")
                binding.bookServiceBtn.text = ("Assigned")
                binding.cancelLayout.visibility = View.GONE
                binding.bookServiceBtn.visibility = View.VISIBLE
            }
            else if(orderDetail!!.completed.toInt() == 4) {
                binding.bookTxt.text = ("pending")
                binding.bookServiceBtn.text = ("Documentation pending")
                binding.cancelLayout.visibility = View.GONE
                binding.bookServiceBtn.visibility = View.VISIBLE
            }
            else if(orderDetail!!.completed.toInt() == 5) {
                binding.bookTxt.text = ("Progress")
                binding.bookServiceBtn.text = ("Work In Progress")
                binding.cancelLayout.visibility = View.GONE
                binding.bookServiceBtn.visibility = View.VISIBLE
            }
            else{
                binding.bookTxt.text = ("Placed")
                binding.bookServiceBtn.text = ("Order Placed")
                binding.cancelLayout.visibility = View.GONE
                binding.bookServiceBtn.visibility = View.VISIBLE
            }
        } else {
            binding.bookTxt.text = ("Book")
            binding.cancelLayout.visibility = View.GONE
            binding.bookServiceBtn.visibility = View.VISIBLE
            binding.bookServiceBtn.text = ("Book Service")
        }
        binding.payNow.setOnClickListener{
            val intent = Intent(this, PayNowActivity::class.java)
            intent.putExtra("orderId",orderDetail!!.orderId);
            intent.putExtra("email", AppPrefData.user()!!.email);
            intent.putExtra("mobile",AppPrefData.user()!!.mobile);
            startActivityForResult(intent,0)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==0)
            if(resultCode==0)
                this.finish()
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
        var commentAdapter = CommentAdapter(this)
        val skeletonScreen = Skeleton.bind(binding.commentRcv)
            .adapter(commentAdapter)
            .load(R.layout.element_comment)
            .count(10)
            .show()
        myViewModel.fetchAllComment(subCategoryData.name).observe(this, Observer {
            skeletonScreen.hide()
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

                commentAdapter = CommentAdapter(this)
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