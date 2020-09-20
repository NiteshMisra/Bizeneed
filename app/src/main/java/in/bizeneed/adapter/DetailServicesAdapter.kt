package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementServices2Binding
import android.content.Context

class DetailServicesAdapter(var context: Context) : BaseRecyclerViewAdapter<Int, ElementServices2Binding>() {

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