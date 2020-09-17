package `in`.bizeneed.activity

import `in`.bizeneed.DescriptionModel
import `in`.bizeneed.R
import `in`.bizeneed.adapter.CommentAdapter
import `in`.bizeneed.adapter.DetailListAdapter
import `in`.bizeneed.adapter.DetailServicesAdapter
import `in`.bizeneed.databinding.ActivityServiceDetailBinding
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ServiceDetail : AppCompatActivity() {

    private lateinit var binding : ActivityServiceDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_service_detail)

        binding.backBtn.setOnClickListener { onBackPressed() }

        servicesList()
        detailList()
        commentList()

        binding.crossedPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        binding.bookServiceBtn.setOnClickListener {
            startActivity(Intent(this,Registration::class.java))
        }

        binding.bookBtn.setOnClickListener {
            startActivity(Intent(this,Registration::class.java))
        }
    }

    private fun commentList() {
        val list : ArrayList<Int> = ArrayList()
        for (i in 0..5)
            list.add(i)

        binding.commentRcv.layoutManager = LinearLayoutManager(this)

        val commentAdapter = CommentAdapter(this)
        commentAdapter.addItems(list)
        binding.commentRcv.adapter = commentAdapter
        commentAdapter.notifyDataSetChanged()
    }

    private fun detailList() {
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
    }

    private fun servicesList() {
        val list: ArrayList<Int> = ArrayList()
        for (i in 0..8)
            list.add(i)

        val servicesAdapter = DetailServicesAdapter(this)
        servicesAdapter.addItems(list)
        binding.detailServicesRcv.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        binding.detailServicesRcv.adapter = servicesAdapter
        servicesAdapter.notifyDataSetChanged()
    }
}