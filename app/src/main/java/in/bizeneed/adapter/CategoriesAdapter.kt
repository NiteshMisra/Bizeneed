package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.activity.ServiceDetail
import `in`.bizeneed.databinding.ElementCategoriesBinding
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.response.SubCategoryData
import android.content.Context
import android.content.Intent
import com.google.gson.Gson

class CategoriesAdapter(var context: Context) : BaseRecyclerViewAdapter<SubCategoryData, ElementCategoriesBinding>() {

    override fun getLayout(): Int = R.layout.element_categories

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementCategoriesBinding>,
        position: Int
    ) {

        val currentItem = items[position]

        holder.binding.categoryTitle.text = currentItem.name
        holder.binding.position.text = ("${position+1}.")

        holder.binding.layout.setOnClickListener{
            val intent = Intent(context, ServiceDetail::class.java)
            intent.putExtra(Constants.DATA,Gson().toJson(currentItem))
            intent.putExtra(Constants.IS_PURCHASED,false)
            context.startActivity(intent)
        }
    }


}