package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.adapter.CategoriesAdapter1
import `in`.bizeneed.databinding.ActivityServiceCategoryBinding
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.extras.hideKeyBoard
import `in`.bizeneed.listener.CategoryListener
import `in`.bizeneed.response.CategoryResponse
import `in`.bizeneed.response.ServiceData
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ethanhua.skeleton.Skeleton
import com.google.gson.Gson
import java.util.*

class ServiceCategory : BaseActivity<ActivityServiceCategoryBinding>(), CategoryListener {

    private var oldSelectedPosition : Int = -1
    private lateinit var categoriesAdapter : CategoriesAdapter1
    private lateinit var categoryLayoutManager: LinearLayoutManager
    private lateinit var serviceData: ServiceData
    private var categoryResponse: CategoryResponse ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val value = intent.getStringExtra(Constants.DATA)
        serviceData = Gson().fromJson(value,ServiceData::class.java)

        binding.backBtn.setOnClickListener { onBackPressed() }
        binding.title.text = serviceData.name

        categories()

        binding.searchEdt.setOnEditorActionListener { textView, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH){
                hideKeyBoard(this)
                textView?.let {
                    if (it.text.isNotEmpty() && categoryResponse != null){
                        var found = false
                        for ((pos, item) in categoryResponse!!.category.withIndex() ){
                            for ((pos2,subItem) in item.subCategory.withIndex()){
                                if (subItem.name.toLowerCase(Locale.getDefault()).contains(it.text.toString())){
                                    categoriesAdapter.changeShowing(pos,true,pos2)
                                    hideOtherCategory(pos)
                                    found = true
                                    categoryLayoutManager.scrollToPositionWithOffset(pos,20)
                                    break
                                }
                            }
                        }
                        if (!found){
                            Toast.makeText(this,"Service Not Found",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

    }

    private fun categories() {
        categoryLayoutManager = LinearLayoutManager(this)
        binding.categoryRcv.layoutManager = categoryLayoutManager
        categoriesAdapter = CategoriesAdapter1(this,this)
        val skeletonScreen = Skeleton.bind(binding.categoryRcv)
            .adapter(categoriesAdapter)
            .load(R.layout.element_categories1)
            .count(5)
            .show()
        myViewModel.getAllCategory(serviceData.name).observe(this, androidx.lifecycle.Observer {
            skeletonScreen.hide()
            it?.let {

                categoryResponse = it
                categoriesAdapter = CategoriesAdapter1(this,this)
                categoriesAdapter.addItems(it.category)
                binding.categoryRcv.adapter = categoriesAdapter
                categoriesAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun hideOtherCategory(visibleCategoryIndex: Int)
      {
        if (oldSelectedPosition != -1 && oldSelectedPosition != visibleCategoryIndex){
            categoriesAdapter.changeShowing(oldSelectedPosition,false,0)
            categoriesAdapter.notifyItemChanged(oldSelectedPosition)
        }
        oldSelectedPosition = visibleCategoryIndex
    }

    override fun getLayoutRes(): Int = R.layout.activity_service_category
}