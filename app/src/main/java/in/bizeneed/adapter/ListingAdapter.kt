package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementListBinding
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class ListingAdapter(
    var context: Context
) : BaseRecyclerViewAdapter<Int, ElementListBinding>() {

    override fun getLayout(): Int = R.layout.element_list

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementListBinding>,
        position: Int
    ) {

        val currentItem = items[position]
        holder.binding.tagTitle.text = ("Super Saver Week")

        holder.binding.listRcv.layoutManager = LinearLayoutManager(context)
        val list : ArrayList<Int> = ArrayList()
        for (i in 0..6)
            list.add(i)

        val listingAdapter2 = ListingAdapter2(context)
        listingAdapter2.addItems(list)
        holder.binding.listRcv.adapter = listingAdapter2
        listingAdapter2.notifyDataSetChanged()

    }


}