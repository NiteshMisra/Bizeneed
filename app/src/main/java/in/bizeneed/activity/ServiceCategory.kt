package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.adapter.CategoriesAdapter
import `in`.bizeneed.adapter.CategoriesAdapter1
import `in`.bizeneed.adapter.PreferenceAdapter
import `in`.bizeneed.databinding.ActivityServiceCategoryBinding
import `in`.bizeneed.listener.CategoryListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.*

class ServiceCategory : AppCompatActivity(), CategoryListener {

    private lateinit var binding : ActivityServiceCategoryBinding
    private var oldSelectedPosition : Int = -1
    private lateinit var categoriesAdapter : CategoriesAdapter1
    private lateinit var categoryLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_service_category)

        binding.backBtn.setOnClickListener { onBackPressed() }

        categories()

    }

    private fun categories() {
        binding.categoryTitle.text = ("Select a Category")
        categoryLayoutManager = LinearLayoutManager(this)
        binding.categoryRcv.layoutManager = categoryLayoutManager
        val list: ArrayList<Int> = ArrayList()
        for (i in 0..5)
            list.add(i)

        categoriesAdapter = CategoriesAdapter1(this,this)
        categoriesAdapter.addItems(list)
        binding.categoryRcv.adapter = categoriesAdapter
        categoriesAdapter.notifyDataSetChanged()
    }

    override fun hideOtherCategory(visibleCategoryIndex: Int) {
        if (oldSelectedPosition != -1 && oldSelectedPosition != visibleCategoryIndex)
            categoriesAdapter.notifyItemChanged(oldSelectedPosition)
        oldSelectedPosition = visibleCategoryIndex
    }
}