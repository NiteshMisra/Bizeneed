package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.activity.ServiceCategory
import `in`.bizeneed.databinding.ElementServicesBinding
import android.content.Context
import android.content.Intent
import assignment.chatapp.adapter.BaseRecyclerViewAdapter

class MoreServicesAdapter(var context: Context) : BaseRecyclerViewAdapter<Int,ElementServicesBinding>() {

    override fun getLayout(): Int = R.layout.element_services

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementServicesBinding>,
        position: Int
    ) {
        val currentItem = items[position]
        //holder.binding.title.text = ("Salon at home")

        holder.binding.layout.setOnClickListener{
            context.startActivity(Intent(context,ServiceCategory::class.java))
        }
    }


}