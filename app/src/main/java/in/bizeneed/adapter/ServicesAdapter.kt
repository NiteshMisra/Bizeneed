package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.activity.ServiceCategory
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.response.ServiceData
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.rishabhharit.roundedimageview.RoundedImageView
import java.util.*
import kotlin.collections.ArrayList

class ServicesAdapter(
    var context: Context,
    var list: List<ServiceData>
) : RecyclerView.Adapter<ServicesAdapter.ServiceVH>(), Filterable {

    private val fullList: List<ServiceData> = list

    override fun onBindViewHolder(holder: ServiceVH, position: Int) {
        val currentItem: ServiceData = list[position]

        Glide.with(context).load(Constants.IMAGE_URL + currentItem.headerImage).into(holder.image)
        holder.title.text = currentItem.name

        holder.layout.setOnClickListener {
            val intent = Intent(context, ServiceCategory::class.java)
            intent.putExtra(Constants.DATA, Gson().toJson(currentItem))
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.element_services, parent, false)
        return ServiceVH(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ServiceVH(val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val image: RoundedImageView = view.findViewById(R.id.image)
        val layout: LinearLayout = view.findViewById(R.id.layout)
    }

    @Suppress("UNCHECKED_CAST")
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val results = FilterResults()

                val search = p0.toString().toLowerCase(Locale.getDefault())
                if (search.isEmpty()) {
                    results.values = fullList
                } else {
                    val newList : ArrayList<ServiceData> = ArrayList()
                    for (item in fullList) {
                        if (item.name.toLowerCase(Locale.getDefault()).contains(search)) {
                            newList.add(item)
                        }
                    }
                    results.values = newList
                }
                return results
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                list = p1?.values as ArrayList<ServiceData>
                notifyDataSetChanged()
            }
        }
    }


}