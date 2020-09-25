package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.activity.ImageSliderActivity
import `in`.bizeneed.databinding.ElementServices2Binding
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.response.DemoImages
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.google.gson.Gson

class ServiceImagesAdapter(
    var context: Context,
    var imagesList : List<DemoImages>
) : BaseRecyclerViewAdapter<DemoImages, ElementServices2Binding>() {

    override fun getLayout(): Int = R.layout.element_services2

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementServices2Binding>,
        position: Int
    ) {
        val currentItem = items[position]

        Glide.with(context).load(Constants.IMAGE_URL + currentItem.image).into(holder.binding.image)

        holder.binding.layout.setOnClickListener{
            val intent = Intent(context,ImageSliderActivity::class.java)
            intent.putExtra(Constants.DATA,Gson().toJson(imagesList))
            context.startActivity(intent)
        }
    }


}