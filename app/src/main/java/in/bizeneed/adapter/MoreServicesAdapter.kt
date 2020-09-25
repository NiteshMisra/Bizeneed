package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.activity.ServiceCategory
import `in`.bizeneed.databinding.ElementServicesBinding
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.response.ServiceData
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.google.gson.Gson

class MoreServicesAdapter(var context: Context) : BaseRecyclerViewAdapter<ServiceData, ElementServicesBinding>() {

    override fun getLayout(): Int = R.layout.element_services

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementServicesBinding>,
        position: Int
    ) {
        val currentItem = items[position]

        holder.binding.title.text = currentItem.name
        Glide.with(context).load(Constants.IMAGE_URL + currentItem.headerImage).into(holder.binding.image)

        holder.binding.layout.setOnClickListener{
            val intent = Intent(context,ServiceCategory::class.java)
            intent.putExtra(Constants.DATA, Gson().toJson(currentItem))
            context.startActivity(intent)
        }
    }


}