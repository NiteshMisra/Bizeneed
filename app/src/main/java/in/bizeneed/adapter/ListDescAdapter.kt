package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementListDescBinding
import android.content.Context

class ListDescAdapter(var context: Context) : BaseRecyclerViewAdapter<Int, ElementListDescBinding>(){

    override fun getLayout(): Int = R.layout.element_list_desc

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementListDescBinding>,
        position: Int
    ) {

        val currentItem = items[position]

        holder.binding.title.text = ("FACIAL - Eyebrows Threading, Upper Lip Threading")
    }

}