package `in`.bizeneed.fragments

import `in`.bizeneed.R
import `in`.bizeneed.activity.Address
import `in`.bizeneed.activity.SearchActivity
import `in`.bizeneed.adapter.BannerAdapter
import `in`.bizeneed.adapter.MoreServicesAdapter
import `in`.bizeneed.adapter.ServicesAdapter
import `in`.bizeneed.databinding.FragmentHomeBinding
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var activity1: FragmentActivity
    private var countDownTimer: CountDownTimer ?= null
    private lateinit var bannerList : ArrayList<Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        activity1 = requireActivity()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bannerList()
        servicesList()
        moreServicesList()

        binding.location.setOnClickListener {
            startActivity(Intent(activity1,Address::class.java))
        }

        binding.searchImage.setOnClickListener {
            startActivity(Intent(activity1,SearchActivity::class.java))
        }

        binding.search.setOnClickListener {
            startActivity(Intent(activity1,SearchActivity::class.java))
        }
    }

    private fun initViews() {
        bannerList = ArrayList()
    }

    private fun moreServicesList() {
        val list: ArrayList<Int> = ArrayList()
        for (i in 0..3)
            list.add(i)

        val moreServicesAdapter = MoreServicesAdapter(activity1)
        moreServicesAdapter.addItems(list)
        binding.moreServicesRcv.layoutManager = GridLayoutManager(activity1,2)
        binding.moreServicesRcv.adapter = moreServicesAdapter
        moreServicesAdapter.notifyDataSetChanged()
    }

    private fun servicesList() {
        val list: ArrayList<Int> = ArrayList()
        for (i in 0..8)
            list.add(i)

        val servicesAdapter = ServicesAdapter(activity1)
        servicesAdapter.addItems(list)
        binding.servicesRcv.layoutManager = GridLayoutManager(activity1,3)
        binding.servicesRcv.adapter = servicesAdapter
        servicesAdapter.notifyDataSetChanged()
    }

    private fun bannerList() {

        bannerList.clear()
        bannerList.add(R.raw.safety)
        bannerList.add(R.raw.cleaning)
        bannerList.add(R.raw.salon)
        bannerList.add(R.raw.reward_point)

        val bannerAdapter = BannerAdapter(activity1, bannerList)
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
                val nextPosition = if (currentPosition == bannerList.size - 1){
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

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}