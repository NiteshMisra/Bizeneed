package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.adapter.LoginImageAdapter
import `in`.bizeneed.databinding.ActivityGetStartedBinding
import `in`.bizeneed.model.SliderModel
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.CountDownTimer
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager

class GetStartedActivity : BaseActivity<ActivityGetStartedBinding>(){

    private var countDownTimer: CountDownTimer ?= null
    private lateinit var imageList : ArrayList<SliderModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginSlider()

        binding.getStarted.setOnClickListener {
            startActivity(Intent(this,LoginActivity2::class.java))
        }

    }

    private fun loginSlider() {
        imageList = ArrayList()
        imageList.add(SliderModel(R.drawable.first,"Get instant support for all your queries for the business",""))
        imageList.add(SliderModel(R.raw.second,"We have got you need for your business","Companify is India's cloud-based business services platform dedicated to helping Entrepreneurs easily start and grow their business, at an affordable cost."))
        imageList.add(SliderModel(R.raw.lea,"Get your company register within just 2 days with best price in market","Now you need to place order to avail rewards and benefits available for your business."))
        val loginImageAdapter = LoginImageAdapter(this,imageList)
        binding.viewPager.adapter = loginImageAdapter
        loginImageAdapter.notifyDataSetChanged()

        addBottomDots(binding.layoutDots, imageList.size, 0)

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                addBottomDots(binding.layoutDots, imageList.size, position % imageList.size)
                startBannerTimer()
            }

        })

        startBannerTimer()

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

    private fun addBottomDots(layout_dots: LinearLayout, size: Int, current: Int) {
        val dots =
            arrayOfNulls<ImageView>(size)
        layout_dots.removeAllViews()
        try {
            for (i in dots.indices) {
                dots[i] = ImageView(this)
                val width_height = 17
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams(
                        width_height,
                        width_height
                    )
                )
                params.setMargins(10, 0, 10, 0)
                dots[i]!!.layoutParams = params
                dots[i]!!.setImageResource(R.drawable.shape_circle)
                dots[i]!!.setColorFilter(
                    ContextCompat.getColor(this, R.color.grey),
                    PorterDuff.Mode.SRC_ATOP
                )
                layout_dots.addView(dots[i])
            }
            if (dots.isNotEmpty()) {
                dots[current]!!.setImageResource(R.drawable.shape_circle)
                dots[current]!!.setColorFilter(
                    ContextCompat.getColor(this, R.color.colorPrimary),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        } catch (e: Exception) {
            //to be handled later or to change dots layout
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_get_started
}