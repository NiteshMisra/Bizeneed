package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.RandomModel
import `in`.bizeneed.activity.ServiceCategory
import `in`.bizeneed.activity.ServiceDetail
import `in`.bizeneed.databinding.ElementCategoriesBinding
import android.content.Context
import android.content.Intent
import assignment.chatapp.adapter.BaseRecyclerViewAdapter

class CategoriesAdapter(var context: Context) : BaseRecyclerViewAdapter<RandomModel,ElementCategoriesBinding>() {

    override fun getLayout(): Int = R.layout.element_categories

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementCategoriesBinding>,
        position: Int
    ) {

        val currentItem = items[position]

        holder.binding.categoryTitle.text = currentItem.itemName
        holder.binding.position.text = ("${position+1}.")

        holder.binding.layout.setOnClickListener{
            context.startActivity(Intent(context, ServiceDetail::class.java))
        }
    }


}