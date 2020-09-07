package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.adapter.DetailImageAdapter
import `in`.bizeneed.adapter.DetailListAdapter
import `in`.bizeneed.databinding.ActivityDetailBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private lateinit var imageList: ArrayList<Int>
    private var countDownTimer : CountDownTimer ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail)

        binding.backBtn.setOnClickListener {onBackPressed() }

        imageList = ArrayList()
        imageList()

    }

    private fun imageList() {
        imageList.clear()
        for (i in 0..4)
            imageList.add(i)

        val bannerAdapter = DetailImageAdapter(this, imageList)
        binding.viewPager.adapter = bannerAdapter
        bannerAdapter.notifyDataSetChanged()

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
                startBannerTimer()
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                startBannerTimer()
            }

            override fun onPageSelected(position: Int) {

            }

        })
    }

    private fun startBannerTimer(){
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(5000,1000) {
            override fun onFinish() {
                val currentPosition = binding.viewPager.currentItem
                val nextPosition = if (currentPosition == imageList.size - 1){
                    0
                }else{
                    currentPosition + 1
                }

                binding.viewPager.setCurrentItem(nextPosition,true)
            }

            override fun onTick(p0: Long) {

            }

        }
        countDownTimer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}