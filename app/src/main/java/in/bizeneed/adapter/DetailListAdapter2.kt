package `in`.bizeneed.adapter

import `in`.bizeneed.model.DescriptionModel
import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementDetailList2Binding
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

class DetailListAdapter2(
    var context: Context
) : BaseRecyclerViewAdapter<DescriptionModel, ElementDetailList2Binding>() {

    override fun getLayout(): Int = R.layout.element_detail_list_2

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementDetailList2Binding>,
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