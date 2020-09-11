package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.activity.ServiceCategory
import `in`.bizeneed.databinding.ElementServicesBinding
import android.content.Context
import android.content.Intent
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import assignment.chatapp.adapter.BaseRecyclerViewAdapter
import com.bumptech.glide.Glide

class ServicesAdapter(var context: Context) : BaseRecyclerViewAdapter<Int,ElementServicesBinding>() {

    override fun getLayout(): Int = R.layout.element_services

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementServicesBinding>,
        position: Int
    ) {
        val currentItem = items[position]
        //holder.binding.title.text = ("Salon at home")

        if (position == 0){
            Glide.with(context).load(R.drawable.it_sevice).into(holder.binding.image)
            holder.binding.title.text = ("IT Services")

        }else{
            holder.binding.title.text = ("Services ${position + 1}")
        }

        holder.binding.layout.setOnClickListener{
            context.startActivity(Intent(context,ServiceCategory::class.java))
        }
    }


}