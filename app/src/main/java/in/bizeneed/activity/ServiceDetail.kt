package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.adapter.CommentAdapter
import `in`.bizeneed.adapter.DetailListAdapter
import `in`.bizeneed.adapter.ServiceImagesAdapter
import `in`.bizeneed.databinding.ActivityServiceDetailBinding
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.extras.formattedRating
import `in`.bizeneed.extras.isConnected
import `in`.bizeneed.response.CommentData
import `in`.bizeneed.response.SubCategoryData
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson

class ServiceDetail : BaseActivity<ActivityServiceDetailBinding>() {

    private lateinit var subCategoryData: SubCategoryData
    private var isPurchased : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val value = intent.getStringExtra(Constants.DATA)
        isPurchased = intent.getBooleanExtra(Constants.IS_PURCHASED,false)
        subCategoryData = Gson().fromJson(value, SubCategoryData::class.java)

        Glide.with(this).load(Constants.IMAGE_URL + subCategoryData.headerImage).into(binding.image)
        binding.name.text = subCategoryData.name
        binding.title.text = subCategoryData.name
        binding.offer.text = ("Use Code: ${subCategoryData.promoCode} to get ${subCategoryData.discount}% Additional Discount")
        binding.crossedPrice.text = ("\u20B9${subCategoryData.mrp}")
        binding.currentPrice.text = ("\u20B9${subCategoryData.sellingPrice}*")
        //binding.desc.text = subCategoryData.description

        if (isPurchased){
            binding.bookBtn.visibility = View.GONE
            binding.bookServiceBtn.visibility = View.GONE
        }else{
            binding.bookBtn.visibility = View.VISIBLE
            binding.bookServiceBtn.visibility = View.VISIBLE
        }

        binding.backBtn.setOnClickListener { onBackPressed() }

        imagesList()
        commentList()
        detailList()

        binding.crossedPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        binding.bookServiceBtn.setOnClickListener {
            val intent = Intent(this,Registration::class.java)
            intent.putExtra(Constants.DATA,Gson().toJson(subCategoryData))
            startActivity(intent)
        }

        binding.bookBtn.setOnClickListener {
            val intent = Intent(this,Registration::class.java)
            intent.putExtra(Constants.DATA,Gson().toJson(subCategoryData))
            startActivity(intent)
        }
    }

    private fun commentList() {
        binding.commentRcv.layoutManager = LinearLayoutManager(this)

        if (isConnected(this)){
            showProgressBar(null)
            myViewModel.fetchAllComment(subCategoryData.name).observe(this, Observer {
                hideProgress()
                it.let {
                    var sumRating : Float = 0F
                    for (item in it.data){
                       sumRating += item.rating.toFloat()
                    }

                    if (it.data.isNotEmpty()){
                        binding.rating.text = (sumRating/(it.data.size)).toString()
                    }else{
                        binding.rating.text = ("0.0")
                    }
                    binding.ratingCount.text = ("(${formattedRating(it.data.size)} ratings)")

                    if (it.data.isEmpty()){
                        binding.commentLayout.visibility = View.GONE
                    }else{
                        binding.commentLayout.visibility = View.VISIBLE
                    }

                    val commentAdapter = CommentAdapter(this)
                    commentAdapter.addItems(it.data)
                    binding.commentRcv.adapter = commentAdapter
                    commentAdapter.notifyDataSetChanged()
                }
            })
        }else{
            hideProgress()
            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show()
        }
    }

    private fun detailList() {

        if (subCategoryData.mulitpleDescription != null){
            binding.listDescRcv.layoutManager = LinearLayoutManager(this)

            val detailListAdapter = DetailListAdapter(this)
            detailListAdapter.addItems(subCategoryData.mulitpleDescription!!)
            binding.listDescRcv.adapter = detailListAdapter
            detailListAdapter.notifyDataSetChanged()
        }
    }

    private fun imagesList() {
        val servicesAdapter = ServiceImagesAdapter(this,subCategoryData.demoImages)
        servicesAdapter.addItems(subCategoryData.demoImages)
        binding.detailServicesRcv.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        binding.detailServicesRcv.adapter = servicesAdapter
        servicesAdapter.notifyDataSetChanged()
    }

    override fun getLayoutRes(): Int = R.layout.activity_service_detail
}