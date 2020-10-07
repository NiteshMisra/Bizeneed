package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.activity.ServiceDetail
import `in`.bizeneed.databinding.ElementTransactionsBinding
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.response.OrderData
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.google.gson.Gson

class OrderAdapter(
    var context: Context
) : BaseRecyclerViewAdapter<OrderData, ElementTransactionsBinding>() {

    override fun getLayout(): Int = R.layout.element_transactions

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementTransactionsBinding>,
        position: Int
    ) {

        val currentItem = items[position]
        holder.binding.title.text = currentItem.subCategoryName
        if (currentItem.subCategoryDetails.isNotEmpty()){

            Glide.with(context)
                .asBitmap()
                .load(Constants.IMAGE_URL + currentItem.subCategoryDetails[0].headerImage)
                .override(1600,1600)
                .into(BitmapImageViewTarget(holder.binding.image))

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