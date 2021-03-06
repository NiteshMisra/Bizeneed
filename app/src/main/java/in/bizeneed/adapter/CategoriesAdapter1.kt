package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementCategories1Binding
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.listener.CategoryListener
import `in`.bizeneed.response.CategoryData
import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide

class CategoriesAdapter1(
    var context: Context,
    private var categoryListener: CategoryListener
) : BaseRecyclerViewAdapter<CategoryData, ElementCategories1Binding>() {

    override fun getLayout(): Int = R.layout.element_categories1

    fun changeShowing(position : Int, isShowing : Boolean, scrollTo : Int){
        items[position].isShowing = isShowing
        items[position].showPosition = scrollTo
        notifyItemChanged(position)
    }

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementCategories1Binding>,
        position: Int
    ) {

        holder.binding.title.setBackgroundResource(0)
        holder.binding.image.setBackgroundResource(R.color.colorPrimary)

        val currentItem: CategoryData = items[position]

        holder.binding.categoryRcv.visibility = View.GONE

        holder.binding.title.text = currentItem.name
        Glide.with(context).load(Constants.IMAGE_URL + currentItem.image).into(holder.binding.image)

        if (currentItem.isShowing)
        {
            holder.binding.categoryRcv.visibility = View.VISIBLE
            val manager = LinearLayoutManager(context)
            holder.binding.categoryRcv.layoutManager = manager
            holder.binding.categoryRcv.scheduleLayoutAnimation()

            val categoriesAdapter = CategoriesAdapter(context)
            categoriesAdapter.addItems(currentItem.subCategory)
            holder.binding.categoryRcv.adapter = categoriesAdapter
            categoriesAdapter.notifyDataSetChanged()
            categoryListener.hideOtherCategory(position)
            manager.scrollToPositionWithOffset(currentItem.showPosition, 20)
            //holder.binding.categoryRcv.scrollToPosition(currentItem.showPosition)
        }

        holder.binding.titleLayout.setOnClickListener {
            if (holder.binding.categoryRcv.visibility == View.VISIBLE) {
                holder.binding.categoryRcv.visibility = View.GONE
            } else {
                holder.binding.categoryRcv.visibility = View.VISIBLE
                val manager = LinearLayoutManager(context)
                holder.binding.categoryRcv.layoutManager = manager
                holder.binding.categoryRcv.scheduleLayoutAnimation()

                val categoriesAdapter = CategoriesAdapter(context)
                categoriesAdapter.addItems(currentItem.subCategory)
                holder.binding.categoryRcv.adapter = categoriesAdapter
                categoriesAdapter.notifyDataSetChanged()
                categoryListener.hideOtherCategory(position)
                manager.scrollToPositionWithOffset(2, 20)
            }
        }
    }

}