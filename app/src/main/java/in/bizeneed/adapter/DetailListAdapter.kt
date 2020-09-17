package `in`.bizeneed.adapter

import `in`.bizeneed.DescriptionModel
import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementDetailListBinding
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import assignment.chatapp.adapter.BaseRecyclerViewAdapter

class DetailListAdapter(
    var context: Context
) : BaseRecyclerViewAdapter<DescriptionModel, ElementDetailListBinding>() {

    override fun getLayout(): Int = R.layout.element_detail_list

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementDetailListBinding>,
        position: Int
    ) {

        val currentItem = items[position]
        holder.binding.title.text = currentItem.title

        if (currentItem.list != null){
            holder.binding.detailListRcv.layoutManager = LinearLayoutManager(context)
            val listingAdapter2 = DetailListAdapter2(context)
            listingAdapter2.addItems(currentItem.list!!)
            holder.binding.detailListRcv.adapter = listingAdapter2
            listingAdapter2.notifyDataSetChanged()
            holder.binding.detailListRcv.visibility = View.VISIBLE
        }else{
            holder.binding.detailListRcv.visibility = View.GONE
        }

    }


}