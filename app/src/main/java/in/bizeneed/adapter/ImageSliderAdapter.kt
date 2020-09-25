package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.response.DemoImages
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ImageSliderAdapter(
    private val act: Context,
    private var imagelist: List<DemoImages>
) : PagerAdapter()  {

    override fun getCount(): Int {
        return imagelist.size
    }

    fun getItem(pos: Int): Int {
        return imagelist.size
    }

    fun setItems(items1: List<DemoImages>) {
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
        val currentImage: DemoImages = imagelist[position]
        val inflater = act.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = inflater.inflate(R.layout.detail_images, container, false)
        val image = v.findViewById<ImageView>(R.id.image)
        Glide.with(act).load(Constants.IMAGE_URL + currentImage.image).diskCacheStrategy(DiskCacheStrategy.ALL).into(image)
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