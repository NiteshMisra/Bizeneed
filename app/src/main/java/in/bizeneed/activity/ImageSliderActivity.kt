package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.adapter.ImageSliderAdapter
import `in`.bizeneed.databinding.ActivityImageSliderBinding
import `in`.bizeneed.response.DemoImages
import android.os.Bundle
import android.provider.SyncStateContract
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ImageSliderActivity : BaseActivity<ActivityImageSliderBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val value: String = intent.getStringExtra(SyncStateContract.Constants.DATA)!!
        val type: Type = object : TypeToken<List<DemoImages>>(){}.type
        val imagesList : List<DemoImages> = Gson().fromJson(value,type)

        val imageSliderAdapter = ImageSliderAdapter(this,imagesList)
        binding.viewPager.adapter = imageSliderAdapter
        imageSliderAdapter.notifyDataSetChanged()
    }

    override fun getLayoutRes(): Int = R.layout.activity_image_slider
}