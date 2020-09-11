package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementDetailList2Binding
import android.content.Context
import assignment.chatapp.adapter.BaseRecyclerViewAdapter

class DetailListAdapter2(
    var context: Context
) : BaseRecyclerViewAdapter<Int, ElementDetailList2Binding>() {

    override fun getLayout(): Int = R.layout.element_detail_list_2

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementDetailList2Binding>,
        position: Int
    ) {

        val currentItem = items[position]
        holder.binding.title.text = ("8 Page Website, Inquiry Form, Live Chat, etc.")

    }


}