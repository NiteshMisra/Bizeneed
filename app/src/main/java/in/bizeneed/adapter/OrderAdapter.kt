package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.activity.ServiceDetail
import `in`.bizeneed.databinding.ElementTransactionsBinding
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.response.OrderData
import android.content.Context
import android.content.Intent
import com.google.gson.Gson

class OrderAdapter(
    var context: Context
) : BaseRecyclerViewAdapter<OrderData, ElementTransactionsBinding>() {

    override fun getLayout(): Int = R.layout.element_transactions

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementTransactionsBinding>,
        position: Int
    ) {

        holder.binding.title.setBackgroundResource(0)
        holder.binding.date.setBackgroundResource(0)
        holder.binding.status.setBackgroundResource(0)
        holder.binding.amount.setBackgroundResource(0)

        val currentItem = items[position]
        holder.binding.title.text = currentItem.subCategoryName
        holder.binding.status.text = when {
            currentItem.completed.toInt() == 0 -> {
                ("Order Placed")
            }
            currentItem.completed.toInt() == 2 -> {
                ("Cancelled")
            }
            currentItem.completed.toInt() == 3 -> {
                ("Assigned")
            }
            currentItem.completed.toInt() == 4 -> {
                ("Documentation pending")
            }
            currentItem.completed.toInt() == 5 -> {
                ("Work In Progress")
            }
            else -> {
                ("Work Delivered")
            }
        }
        holder.binding.date.text = (currentItem.timeOfOrder + ", " + currentItem.dateOfOrder)
        holder.binding.amount.text = ("\u20B9${currentItem.totalAmount}")

        holder.binding.layout.setOnClickListener {
            if (currentItem.subCategoryDetails.isNotEmpty()){
                val intent = Intent(context,ServiceDetail::class.java)
                intent.putExtra(Constants.DATA,Gson().toJson(currentItem.subCategoryDetails[0]))
                intent.putExtra(Constants.IS_PURCHASED,true)
                intent.putExtra(Constants.ORDER_DATA,Gson().toJson(currentItem))
                context.startActivity(intent)
            }
        }

    }


}