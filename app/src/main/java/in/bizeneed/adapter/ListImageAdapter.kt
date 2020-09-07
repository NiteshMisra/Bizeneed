package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementListImageBinding
import android.content.Context
import assignment.chatapp.adapter.BaseRecyclerViewAdapter

class ListImageAdapter(var context: Context) : BaseRecyclerViewAdapter<Int,ElementListImageBinding>(){

    override fun getLayout(): Int = R.layout.element_list_image

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementListImageBinding>,
        position: Int
    ) {

        val currentItem = items[position]

        holder.binding.layout.setOnClickListener{
            //context.startActivity(Intent(context,ServiceCategory::class.java))
        }
    }

}