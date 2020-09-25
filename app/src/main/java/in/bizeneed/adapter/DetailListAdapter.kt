package `in`.bizeneed.adapter

import `in`.bizeneed.model.DescriptionModel
import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementDetailListBinding
import `in`.bizeneed.response.DescriptionData
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

class DetailListAdapter(
    var context: Context
) : BaseRecyclerViewAdapter<DescriptionData, ElementDetailListBinding>() {

    override fun getLayout(): Int = R.layout.element_detail_list

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementDetailListBinding>,
        position: Int
    ) {

        val currentItem = items[position]
        holder.binding.title.text = currentItem.heading
        holder.binding.body.text = currentItem.body

    }


}