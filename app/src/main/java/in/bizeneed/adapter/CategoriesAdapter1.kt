package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.RandomModel
import `in`.bizeneed.databinding.ElementCategories1Binding
import `in`.bizeneed.listener.CategoryListener
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import assignment.chatapp.adapter.BaseRecyclerViewAdapter
import java.util.ArrayList

class CategoriesAdapter1(var context: Context, var categoryListener: CategoryListener) : BaseRecyclerViewAdapter<RandomModel,ElementCategories1Binding>(){

    override fun getLayout(): Int = R.layout.element_categories1

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementCategories1Binding>,
        position: Int
    ) {

        val currentItem = items[position]

        holder.binding.categoryRcv.visibility = View.GONE

        holder.binding.title.text = currentItem.itemName

        holder.binding.titleLayout.setOnClickListener {
            if (holder.binding.categoryRcv.visibility == View.VISIBLE){
                holder.binding.categoryRcv.visibility = View.GONE
            }else{
                holder.binding.categoryRcv.visibility = View.VISIBLE
                val manager = LinearLayoutManager(context)
                holder.binding.categoryRcv.layoutManager = manager
                holder.binding.categoryRcv.scheduleLayoutAnimation()
                val list: ArrayList<RandomModel> = ArrayList()
                when(currentItem.position) {
                    0 -> {
                        list.add(RandomModel("Static Website",null,0))
                        list.add(RandomModel("Dynamic Website",null,0))
                        list.add(RandomModel("ECommerce Website",null,0))
                        list.add(RandomModel("Multi Vendor Website",null,0))
                        list.add(RandomModel("MLM",null,0))
                    }
                    1 -> {
                        list.add(RandomModel("Private Limited Company",null,1))
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

                val categoriesAdapter = CategoriesAdapter(context)
                categoriesAdapter.addItems(list)
                holder.binding.categoryRcv.adapter = categoriesAdapter
                categoriesAdapter.notifyDataSetChanged()
                categoryListener.hideOtherCategory(position)
                manager.scrollToPositionWithOffset(2,20)
            }
        }

        holder.binding.layout.setOnClickListener{
            //context.startActivity(Intent(context,ServiceCategory::class.java))
        }
    }

}