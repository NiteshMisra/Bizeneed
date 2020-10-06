package `in`.bizeneed.adapter

import `in`.bizeneed.R
import `in`.bizeneed.model.SliderModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class LoginImageAdapter(val act: Context, var imagelist: List<SliderModel>) : PagerAdapter()  {

    override fun getCount(): Int {
        return imagelist.size
    }

    fun getItem(pos: Int): Int {
        return imagelist.size
    }

    fun setItems(items1: List<SliderModel>) {
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
        val currentImage: SliderModel = imagelist[position]
        val inflater = act.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = inflater.inflate(R.layout.login_slider_image, container, false)
        val image = v.findViewById<ImageView>(R.id.image)
        val title : TextView = v.findViewById(R.id.title)
        title.text = currentImage.desc
        Glide.with(act).load(currentImage.image).diskCacheStrategy(DiskCacheStrategy.ALL).into(image)
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