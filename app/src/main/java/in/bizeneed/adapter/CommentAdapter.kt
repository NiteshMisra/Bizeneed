package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementCommentBinding
import `in`.bizeneed.response.CommentData
import android.content.Context

class CommentAdapter(var context: Context) : BaseRecyclerViewAdapter<CommentData, ElementCommentBinding>(){

    override fun getLayout(): Int = R.layout.element_comment

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementCommentBinding>,
        position: Int
    ) {

        val currentItem = items[position]

        holder.binding.senderNameTv.text = currentItem.userName
        holder.binding.commentTv.text = currentItem.feedback
        holder.binding.points.text = currentItem.rating
    }

}