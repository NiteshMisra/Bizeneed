package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementMonthBinding
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import assignment.chatapp.adapter.BaseRecyclerViewAdapter

class BookingMonthAdapter(var context: Context) : BaseRecyclerViewAdapter<Int, ElementMonthBinding>() {

    override fun getLayout(): Int = R.layout.element_month

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementMonthBinding>,
        position: Int
    ) {

        val currentItem = items[position]
        holder.binding.month.text = ("Sept")

        holder.binding.bookingRcv.layoutManager = LinearLayoutManager(context)
        val list : ArrayList<Int> = ArrayList()
        for (i in 0..4)
            list.add(i)

        val listingAdapter = BookingAdapter(context)
        listingAdapter.addItems(list)
        holder.binding.bookingRcv.adapter = listingAdapter
        listingAdapter.notifyDataSetChanged()

    }


}