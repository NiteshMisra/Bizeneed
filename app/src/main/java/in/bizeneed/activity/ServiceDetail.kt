package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.adapter.CommentAdapter
import `in`.bizeneed.adapter.DetailListAdapter
import `in`.bizeneed.adapter.DetailServicesAdapter
import `in`.bizeneed.databinding.ActivityServiceDetailBinding
import android.content.Intent
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

        binding.bookServiceBtn.setOnClickListener {
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
        val list : ArrayList<Int> = ArrayList()
        for (i in 0..2)
            list.add(i)

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