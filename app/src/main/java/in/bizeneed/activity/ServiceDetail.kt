package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.adapter.CommentAdapter
import `in`.bizeneed.adapter.ServiceImagesAdapter
import `in`.bizeneed.databinding.ActivityServiceDetailBinding
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.extras.isConnected
import `in`.bizeneed.response.CommentData
import `in`.bizeneed.response.SubCategoryData
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
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

        Glide.with(this).load(Constants.IMAGE_URL + subCategoryData.image).into(binding.image)
        binding.name.text = subCategoryData.name
        binding.title.text = subCategoryData.name
        binding.offer.text = ("Use Code: ${subCategoryData.promoCode} to get ${subCategoryData.discount}% Additional Discount")
        binding.crossedPrice.text = ("\u20B9${subCategoryData.mrp}*")
        binding.currentPrice.text = ("\u20B9${subCategoryData.sellingPrice}*")
        binding.desc.text = subCategoryData.description

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
                    val commentList : ArrayList<CommentData> = ArrayList()
                    for (item in it.data){
                        if (commentList.size == 20){
                            break
                        }
                        commentList.add(item)
                    }

                    if (commentList.size == 0){
                        binding.commentLayout.visibility = View.GONE
                    }else{
                        binding.commentLayout.visibility = View.VISIBLE
                    }

                    val commentAdapter = CommentAdapter(this)
                    commentAdapter.addItems(commentList)
                    binding.commentRcv.adapter = commentAdapter
                    commentAdapter.notifyDataSetChanged()
                }
            })
        }else{
            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show()
        }
    }

    /*private fun detailList() {
        val list : ArrayList<DescriptionModel> = ArrayList()
        val list2 : ArrayList<DescriptionModel> = ArrayList()
        list2.add(DescriptionModel("Certificate of Incorporation",null))
        list2.add(DescriptionModel("Company Name Approval",null))
        list2.add(DescriptionModel("2 Director Identification Numbers (DIN) Digital Signature Token for 2 Directors",null))
        list2.add(DescriptionModel("Memorandum of Association",null))
        list2.add(DescriptionModel("ESI/PF Registration",null))
        list2.add(DescriptionModel("Articles of association",null))
        list2.add(DescriptionModel("Company PAN Card",null))
        list2.add(DescriptionModel("Comany TAN Number",null))
        list2.add(DescriptionModel("Bank A/c Opening Support",null))

        val list3 : ArrayList<DescriptionModel> = ArrayList()
        list3.add(DescriptionModel("One week Post Submission of documents",null))

        val list4 : ArrayList<DescriptionModel> = ArrayList()
        list4.add(DescriptionModel("Step-1 Choose a unique name for your company using Spice+ Part-A",null))
        list4.add(DescriptionModel("Step-2 Apply for DSC for Subscribers",null))
        list4.add(DescriptionModel("Step-3 Drafting prrparation of Spice+ PartB, eMOA, eAOA & AGILEPRO",null))
        list4.add(DescriptionModel("Step-5 Apply for DSC for Subscribers",null))
        list4.add(DescriptionModel("Step-6 Choose a unique name for your company using Spice+ Part-A",null))

        val list5 : ArrayList<DescriptionModel> = ArrayList()
        list5.add(DescriptionModel("Step-1 Choose a unique name for your company using Spice+ Part-A",list4))
        list5.add(DescriptionModel("Step-2 Apply for DSC for Subscribers",list4))
        list5.add(DescriptionModel("Step-3 Drafting prrparation of Spice+ PartB, eMOA, eAOA & AGILEPRO",list4))
        list5.add(DescriptionModel("Step-5 Apply for DSC for Subscribers",list4))
        list5.add(DescriptionModel("Step-6 Choose a unique name for your company using Spice+ Part-A",list4))

        list.add(DescriptionModel("Deliverables",list2))
        list.add(DescriptionModel("Time Taken",list3))
        list.add(DescriptionModel("Process",list4))
        list.add(DescriptionModel("Checklist of Documents Required",list5))

        binding.detailListRcv.layoutManager = LinearLayoutManager(this)

        val detailListAdapter = DetailListAdapter(this)
        detailListAdapter.addItems(list)
        binding.detailListRcv.adapter = detailListAdapter
        detailListAdapter.notifyDataSetChanged()
    }*/

    private fun imagesList() {
        val servicesAdapter = ServiceImagesAdapter(this)
        servicesAdapter.addItems(subCategoryData.demoImages)
        binding.detailServicesRcv.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        binding.detailServicesRcv.adapter = servicesAdapter
        servicesAdapter.notifyDataSetChanged()
    }

    override fun getLayoutRes(): Int = R.layout.activity_service_detail
}