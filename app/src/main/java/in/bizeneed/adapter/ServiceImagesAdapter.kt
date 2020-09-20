package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementServices2Binding
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.response.DemoImages
import android.content.Context
import com.bumptech.glide.Glide

class ServiceImagesAdapter(var context: Context) : BaseRecyclerViewAdapter<DemoImages, ElementServices2Binding>() {

    override fun getLayout(): Int = R.layout.element_services2

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementServices2Binding>,
        position: Int
    ) {
        val currentItem = items[position]

        Glide.with(context).load(Constants.IMAGE_URL + currentItem.image).into(holder.binding.image)

        /*holder.binding.layout.setOnClickListener{
            context.startActivity(Intent(context,ListingActivity::class.java))
        }*/
    }


}