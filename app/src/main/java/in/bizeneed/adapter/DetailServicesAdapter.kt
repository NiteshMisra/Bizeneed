package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.activity.ListingActivity
import `in`.bizeneed.activity.ServiceCategory
import `in`.bizeneed.databinding.ElementServices2Binding
import `in`.bizeneed.databinding.ElementServicesBinding
import android.content.Context
import android.content.Intent
import assignment.chatapp.adapter.BaseRecyclerViewAdapter

class DetailServicesAdapter(var context: Context) : BaseRecyclerViewAdapter<Int,ElementServices2Binding>() {

    override fun getLayout(): Int = R.layout.element_services2

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementServices2Binding>,
        position: Int
    ) {
        val currentItem = items[position]
        //holder.binding.title.text = ("Salon at home")

        /*holder.binding.layout.setOnClickListener{
            context.startActivity(Intent(context,ListingActivity::class.java))
        }*/
    }


}