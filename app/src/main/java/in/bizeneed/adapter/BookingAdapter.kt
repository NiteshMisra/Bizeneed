package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.activity.BookingDetail
import `in`.bizeneed.databinding.ElementBookingsBinding
import android.content.Context
import android.content.Intent
import android.view.View
import assignment.chatapp.adapter.BaseRecyclerViewAdapter

class BookingAdapter(var context: Context) : BaseRecyclerViewAdapter<Int,ElementBookingsBinding>(){

    override fun getLayout(): Int = R.layout.element_bookings

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementBookingsBinding>,
        position: Int
    ) {

        val currentItem = items[position]

        if (position < 2){
            holder.binding.message.visibility = View.VISIBLE
        }else{
            holder.binding.message.visibility = View.GONE
        }

        holder.binding.title.text = ("Super Saver Week")

        holder.binding.layout.setOnClickListener {
            context.startActivity(Intent(context,BookingDetail::class.java))
        }
    }

}