package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.activity.Summary
import `in`.bizeneed.databinding.ElementCouponBinding
import `in`.bizeneed.response.CouponData
import android.content.Context

class CouponAdapter(var context: Context) : BaseRecyclerViewAdapter<CouponData, ElementCouponBinding>(){

    override fun getLayout(): Int = R.layout.element_coupon

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementCouponBinding>,
        position: Int
    ) {

        val currentItem = items[position]

        if (currentItem.isSelected){
            holder.binding.applyBtn.text = ("Applied")
        }else{
            holder.binding.applyBtn.text = ("Apply")
        }

        holder.binding.couponCode.text = currentItem.couponCode
        holder.binding.desc.text = currentItem.description

        holder.binding.applyBtn.setOnClickListener {
            if (context is Summary){
                (context as Summary).applyCoupon(currentItem,position)
            }
        }

    }

    fun setSelected(isSelected : Boolean,position: Int){
        items[position].isSelected = isSelected
        notifyItemChanged(position)
    }

}