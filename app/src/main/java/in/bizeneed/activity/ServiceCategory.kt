package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.adapter.CategoriesAdapter1
import `in`.bizeneed.databinding.ActivityServiceCategoryBinding
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.extras.isConnected
import `in`.bizeneed.listener.CategoryListener
import `in`.bizeneed.response.ServiceData
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson

class ServiceCategory : BaseActivity<ActivityServiceCategoryBinding>(), CategoryListener {

    private var oldSelectedPosition : Int = -1
    private lateinit var categoriesAdapter : CategoriesAdapter1
    private lateinit var categoryLayoutManager: LinearLayoutManager
    private lateinit var serviceData: ServiceData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val value = intent.getStringExtra(Constants.DATA)
        serviceData = Gson().fromJson(value,ServiceData::class.java)

        Glide.with(this).load(Constants.IMAGE_URL + serviceData.mainImage).into(binding.image)

        binding.backBtn.setOnClickListener { onBackPressed() }
        binding.title.text = serviceData.name

        categories()

    }

    private fun categories() {
        categoryLayoutManager = LinearLayoutManager(this)
        binding.categoryRcv.layoutManager = categoryLayoutManager

        if (isConnected(this)){
            showProgressBar(null)
            myViewModel.getAllCategory(serviceData.name).observe(this, androidx.lifecycle.Observer {
                hideProgress()
                it.let {
                    categoriesAdapter = CategoriesAdapter1(this,this,it)
                    categoriesAdapter.addItems(it.category)
                    binding.categoryRcv.adapter = categoriesAdapter
                    categoriesAdapter.notifyDataSetChanged()
                }
            })
        }else{
            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show()
        }
    }

    override fun hideOtherCategory(visibleCategoryIndex: Int) {
        if (oldSelectedPosition != -1 && oldSelectedPosition != visibleCategoryIndex)
            categoriesAdapter.notifyItemChanged(oldSelectedPosition)
        oldSelectedPosition = visibleCategoryIndex
    }

    override fun getLayoutRes(): Int = R.layout.activity_service_category
}