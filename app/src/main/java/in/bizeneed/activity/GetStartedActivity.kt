package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.adapter.LoginImageAdapter
import `in`.bizeneed.databinding.ActivityGetStartedBinding
import `in`.bizeneed.model.SliderModel
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
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
        imageList.add(SliderModel(R.raw.first,""))
        imageList.add(SliderModel(R.raw.second,""))
        imageList.add(SliderModel(R.raw.lea,""))
        val loginImageAdapter = LoginImageAdapter(this,imageList)
        binding.viewPager.adapter = loginImageAdapter
        loginImageAdapter.notifyDataSetChanged()

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

    override fun getLayoutRes(): Int = R.layout.activity_get_started
}