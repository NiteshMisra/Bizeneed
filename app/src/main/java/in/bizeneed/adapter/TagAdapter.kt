package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementTagsBinding
import `in`.bizeneed.listener.ListingListener
import android.content.Context
import androidx.core.content.ContextCompat

class TagAdapter(
    var context: Context,
    var selectedPosition: Int,
    var tagListener: ListingListener.TagListener
) : BaseRecyclerViewAdapter<Int, ElementTagsBinding>() {

    override fun getLayout(): Int = R.layout.element_tags

    fun selectedPosition(position: Int) {
        selectedPosition = position
    }

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementTagsBinding>,
        position: Int
    ) {

        val currentItem = items[position]

        if (position == selectedPosition) {
            holder.binding.layout.setBackgroundResource(R.drawable.selected_tag_bg)
            holder.binding.tagTitle.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            holder.binding.layout.setBackgroundResource(R.drawable.not_selected_tag_bg)
            holder.binding.tagTitle.setTextColor(ContextCompat.getColor(context, R.color.black))
        }

        holder.binding.tagTitle.text = ("Super Saver Week")

        holder.binding.layout.setOnClickListener {
            selectedPosition = position
            notifyItemChanged(position)
            tagListener.tagSelected(position)
        }
    }


}