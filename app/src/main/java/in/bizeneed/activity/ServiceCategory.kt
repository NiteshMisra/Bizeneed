package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.RandomModel
import `in`.bizeneed.adapter.CategoriesAdapter1
import `in`.bizeneed.databinding.ActivityServiceCategoryBinding
import `in`.bizeneed.listener.CategoryListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.element_categories.*
import java.util.*

class ServiceCategory : AppCompatActivity(), CategoryListener {

    private lateinit var binding : ActivityServiceCategoryBinding
    private var oldSelectedPosition : Int = -1
    private lateinit var categoriesAdapter : CategoriesAdapter1
    private lateinit var categoryLayoutManager: LinearLayoutManager
    private lateinit var randomModel: RandomModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_service_category)
        val value = intent.getStringExtra("data")
        randomModel = Gson().fromJson(value,RandomModel::class.java)

        if (randomModel.itemImage != null){
            Glide.with(this).load(randomModel.itemImage).into(binding.image)
        }
        binding.backBtn.setOnClickListener { onBackPressed() }
        binding.title.text = randomModel.itemName

        categories()

    }

    private fun categories() {
        binding.categoryTitle.text = ("Select a Category")
        categoryLayoutManager = LinearLayoutManager(this)
        binding.categoryRcv.layoutManager = categoryLayoutManager
        val list: ArrayList<RandomModel> = ArrayList()
        when (randomModel.position) {
            0 -> {
                list.add(RandomModel("Website Development",null,0))
                list.add(RandomModel("Mobile Application",null,0))
                list.add(RandomModel("IOS Application",null,0))
                list.add(RandomModel("Window Application",null,0))
                list.add(RandomModel("Game Application",null,0))
                list.add(RandomModel("ERP",null,0))
            }
            1 -> {
                list.add(RandomModel("Business Registration",null,1))
            }
            else -> {
                list.add(RandomModel("Category",null,2))
                list.add(RandomModel("Category",null,2))
                list.add(RandomModel("Category",null,2))
                list.add(RandomModel("Category",null,2))
                list.add(RandomModel("Category",null,2))
                list.add(RandomModel("Category",null,2))
                list.add(RandomModel("Category",null,2))
                list.add(RandomModel("Category",null,2))
                list.add(RandomModel("Category",null,2))
            }
        }

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