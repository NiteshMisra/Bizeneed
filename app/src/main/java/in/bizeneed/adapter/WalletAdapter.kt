package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementWalletHistoryBinding
import `in`.bizeneed.response.WalletData
import android.content.Context
import android.graphics.Color
import com.bumptech.glide.Glide
import java.util.*

class WalletAdapter(var context: Context) : BaseRecyclerViewAdapter<WalletData, ElementWalletHistoryBinding>() {

    override fun getLayout(): Int = R.layout.element_wallet_history

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementWalletHistoryBinding>,
        position: Int
    ) {

        val currentItem = items[position]

        if (currentItem.operation.toLowerCase(Locale.getDefault()) == "sub"){
            Glide.with(context).load(R.drawable.ic_subtract).into(holder.binding.action)
            holder.binding.amount.setTextColor(Color.parseColor("#FF0000"))
        }else{
            Glide.with(context).load(R.drawable.ic_wallet_add).into(holder.binding.action)
            holder.binding.amount.setTextColor(Color.parseColor("#538E01"))
        }

        holder.binding.amount.text = ("\u20B9${currentItem.amount}")
        holder.binding.date.text = ("On ${currentItem.timeOfTransaction}")
    }


}