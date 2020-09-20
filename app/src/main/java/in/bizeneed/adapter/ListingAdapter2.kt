package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.activity.ServiceDetail
import `in`.bizeneed.databinding.ElementList2Binding
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListingAdapter2(
    var context: Context
) : BaseRecyclerViewAdapter<Int, ElementList2Binding>() {

    override fun getLayout(): Int = R.layout.element_list2

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementList2Binding>,
        position: Int
    ) {

        val currentItem = items[position]
        holder.binding.title.text = ("Super Saver Week")

        holder.binding.listImageRcv.layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        val list : ArrayList<Int> = ArrayList()
        for (i in 0..1)
            list.add(i)

        val listImageAdapter = ListImageAdapter(context)
        listImageAdapter.addItems(list)
        holder.binding.listImageRcv.adapter = listImageAdapter
        listImageAdapter.notifyDataSetChanged()

        holder.binding.listDescRcv.layoutManager = LinearLayoutManager(context)
        val listDescAdapter = ListDescAdapter(context)
        listDescAdapter.addItems(list)
        holder.binding.listDescRcv.adapter = listDescAdapter
        listDescAdapter.notifyDataSetChanged()

        holder.binding.viewDetails.setOnClickListener {
            context.startActivity(Intent(context,ServiceDetail::class.java))
        }

    }


}