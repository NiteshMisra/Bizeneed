package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.activity.ServiceCategory
import `in`.bizeneed.databinding.ElementPreferenceBinding
import android.content.Context
import android.content.Intent

class PreferenceAdapter(var context: Context) : BaseRecyclerViewAdapter<Int, ElementPreferenceBinding>() {

    override fun getLayout(): Int = R.layout.element_preference

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementPreferenceBinding>,
        position: Int
    ) {

        val currentItem = items[position]

        holder.binding.layout.setOnClickListener{
            context.startActivity(Intent(context,ServiceCategory::class.java))
        }
    }


}