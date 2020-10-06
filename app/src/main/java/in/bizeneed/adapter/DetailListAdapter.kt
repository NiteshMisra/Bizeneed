@file:Suppress("DEPRECATION")

package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementDetailListBinding
import `in`.bizeneed.response.DescriptionData
import android.content.Context
import android.os.Build
import android.text.Html

class DetailListAdapter(
    var context: Context
) : BaseRecyclerViewAdapter<DescriptionData, ElementDetailListBinding>() {

    override fun getLayout(): Int = R.layout.element_detail_list

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementDetailListBinding>,
        position: Int
    ) {

        val currentItem = items[position]
        holder.binding.title.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            Html.fromHtml(currentItem.heading,Html.FROM_HTML_MODE_LEGACY)
        }else{
            Html.fromHtml(currentItem.heading)
        }
        holder.binding.body.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            Html.fromHtml(currentItem.body,Html.FROM_HTML_MODE_LEGACY)
        }else{
            Html.fromHtml(currentItem.body)
        }

    }


}