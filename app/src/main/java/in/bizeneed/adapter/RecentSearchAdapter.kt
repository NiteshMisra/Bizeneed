package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.activity.ListingActivity
import `in`.bizeneed.databinding.ElementRecentSearchBinding
import android.content.Context
import android.content.Intent

class RecentSearchAdapter(var context: Context) : BaseRecyclerViewAdapter<Int, ElementRecentSearchBinding>() {

    override fun getLayout(): Int = R.layout.element_recent_search

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementRecentSearchBinding>,
        position: Int
    ) {
        val currentItem = items[position]
        holder.binding.title.text = ("Haircut for Kids")

        holder.binding.layout.setOnClickListener{
            context.startActivity(Intent(context,ListingActivity::class.java))
        }
    }


}