package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.activity.ServiceCategory
import `in`.bizeneed.databinding.ElementServicesBinding
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.response.ServiceData
import android.content.Context
import android.content.Intent
import android.widget.Filter
import android.widget.Filterable
import com.bumptech.glide.Glide
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

class ServicesAdapter(var context: Context) : BaseRecyclerViewAdapter<ServiceData, ElementServicesBinding>(), Filterable {

    override fun getLayout(): Int = R.layout.element_services

    private var totalItems : MutableList<ServiceData> = items

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementServicesBinding>,
        position: Int
    ) {
        val currentItem : ServiceData = items[position]

        Glide.with(context).load(Constants.IMAGE_URL + currentItem.image).into(holder.binding.image)
        holder.binding.title.text = currentItem.name

        holder.binding.layout.setOnClickListener{
            val intent = Intent(context,ServiceCategory::class.java)
            intent.putExtra(Constants.DATA,Gson().toJson(currentItem))
            context.startActivity(intent)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val results = FilterResults()

                val search = p0.toString().toLowerCase(Locale.getDefault())
                if (search.isEmpty()){
                    //results.count = items.size
                    results.values = totalItems
                }else{
                    val list = ArrayList<ServiceData>()
                    for (item in totalItems){
                        if (item.name.toLowerCase(Locale.getDefault()).startsWith(search)){
                            list.add(item)
                        }
                    }

                    //results.count = list.size
                    results.values = list
                }
                return results
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                items = p1?.values as MutableList<ServiceData>
                notifyDataSetChanged()
            }
        }
    }


}