package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementDetailListBinding
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import assignment.chatapp.adapter.BaseRecyclerViewAdapter

class DetailListAdapter(
    var context: Context
) : BaseRecyclerViewAdapter<Int, ElementDetailListBinding>() {

    override fun getLayout(): Int = R.layout.element_detail_list

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementDetailListBinding>,
        position: Int
    ) {

        val currentItem = items[position]
        holder.binding.title.text = ("Website Description Title")

        holder.binding.detailListRcv.layoutManager = LinearLayoutManager(context)
        val list : ArrayList<Int> = ArrayList()
        for (i in 0..4)
            list.add(i)

        val listingAdapter2 = DetailListAdapter2(context)
        listingAdapter2.addItems(list)
        holder.binding.detailListRcv.adapter = listingAdapter2
        listingAdapter2.notifyDataSetChanged()

    }


}