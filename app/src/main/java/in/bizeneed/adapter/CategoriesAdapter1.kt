package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementCategories1Binding
import `in`.bizeneed.listener.CategoryListener
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import assignment.chatapp.adapter.BaseRecyclerViewAdapter
import java.util.ArrayList

class CategoriesAdapter1(var context: Context, var categoryListener: CategoryListener) : BaseRecyclerViewAdapter<Int,ElementCategories1Binding>(){

    override fun getLayout(): Int = R.layout.element_categories1

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementCategories1Binding>,
        position: Int
    ) {

        val currentItem = items[position]

        holder.binding.categoryRcv.visibility = View.GONE

        holder.binding.titleLayout.setOnClickListener {
            if (holder.binding.categoryRcv.visibility == View.VISIBLE){
                holder.binding.categoryRcv.visibility = View.GONE
            }else{
                holder.binding.categoryRcv.visibility = View.VISIBLE
                val manager = LinearLayoutManager(context)
                holder.binding.categoryRcv.layoutManager = manager
                holder.binding.categoryRcv.scheduleLayoutAnimation()
                val list: ArrayList<Int> = ArrayList()
                for (i in 0..6)
                    list.add(i)

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