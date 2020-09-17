package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.response.BannerData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rishabhharit.roundedimageview.RoundedImageView

class BannerAdapter(
    private val act: Context,
    private var imagelist: List<BannerData>
) : PagerAdapter() {

    override fun getCount(): Int {
        return imagelist.size
    }

    fun getItem(pos: Int): Int {
        return imagelist.size
    }

    fun setItems(items1: List<BannerData>) {
        imagelist = items1
        notifyDataSetChanged()
    }

    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val currentImage: BannerData = imagelist[position]
        val inflater = act.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = inflater.inflate(R.layout.item_slider_image, container, false)
        val image = v.findViewById<RoundedImageView>(R.id.image)
        //val lyt_parent: CardView = v.findViewById(R.id.layout_cv)
        Glide.with(act).load(Constants.IMAGE_URL + currentImage.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL).into(image)
        //lyt_parent.setOnClickListener { v1: View? -> }
        container.addView(v)
        return v
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        container.removeView(`object` as RelativeLayout)
    }

    // constructor
    init {
        this.imagelist = imagelist
    }
}