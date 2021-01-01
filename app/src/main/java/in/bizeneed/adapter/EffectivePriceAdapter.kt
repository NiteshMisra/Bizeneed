package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ElementCouponBinding
import `in`.bizeneed.databinding.ElementEffectivePriceBinding
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.response.CouponData
import `in`.bizeneed.response.EffectivePrice
import android.content.Context
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_service_detail.view.*

class EffectivePriceAdapter(var context: Context) : BaseRecyclerViewAdapter<EffectivePrice, ElementEffectivePriceBinding>()  {
    override fun getLayout(): Int = R.layout.element_effective_price

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementEffectivePriceBinding>,
        position: Int
    ) {
        var effectivePrice = items[position];
        holder.binding.title.text = effectivePrice.title;
        Glide.with(context).load(Constants.IMAGE_URL + effectivePrice.image).into(holder.binding.icon)

        // holder.binding.icon.image = effectivePrice.image;
    }
}