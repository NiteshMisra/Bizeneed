package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.activity.ServiceCategory
import `in`.bizeneed.activity.ServiceDetail
import `in`.bizeneed.databinding.ElementCategoriesBinding
import android.content.Context
import android.content.Intent
import assignment.chatapp.adapter.BaseRecyclerViewAdapter

class CategoriesAdapter(var context: Context) : BaseRecyclerViewAdapter<Int,ElementCategoriesBinding>() {

    override fun getLayout(): Int = R.layout.element_categories

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementCategoriesBinding>,
        position: Int
    ) {

        val currentItem = items[position]

        when(position){
            0 -> holder.binding.categoryTitle.text = ("Static Website")
            1 -> holder.binding.categoryTitle.text = ("Dynamic Website")
            2 -> holder.binding.categoryTitle.text = ("ECommerce Website")
            3 -> holder.binding.categoryTitle.text = ("Multi Vendor Website")
            4 -> holder.binding.categoryTitle.text = ("MLM")
            else -> holder.binding.categoryTitle.text = ("Category ${position + 1}")
        }

        holder.binding.position.text = ("${position+1}.")

        holder.binding.layout.setOnClickListener{
            context.startActivity(Intent(context, ServiceDetail::class.java))
        }
    }


}