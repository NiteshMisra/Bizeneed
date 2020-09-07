package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementCommentBinding
import android.content.Context
import assignment.chatapp.adapter.BaseRecyclerViewAdapter

class CommentAdapter(var context: Context) : BaseRecyclerViewAdapter<Int,ElementCommentBinding>(){

    override fun getLayout(): Int = R.layout.element_comment

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementCommentBinding>,
        position: Int
    ) {

        val currentItem = items[position]

        holder.binding.senderNameTv.text = ("Hello This is a comment...")
    }

}