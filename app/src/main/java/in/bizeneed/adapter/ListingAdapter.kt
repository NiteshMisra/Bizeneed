package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.activity.ServiceCategory
import `in`.bizeneed.activity.ServiceDetail
import `in`.bizeneed.databinding.ElementCategoriesBinding
import `in`.bizeneed.databinding.ElementListBinding
import `in`.bizeneed.databinding.ElementTagsBinding
import `in`.bizeneed.listener.ListingListener
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import assignment.chatapp.adapter.BaseRecyclerViewAdapter

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