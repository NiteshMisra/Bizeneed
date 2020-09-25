package `in`.bizeneed.fragments

import `in`.bizeneed.R
import `in`.bizeneed.activity.Address
import `in`.bizeneed.activity.Profile
import `in`.bizeneed.activity.SearchActivity
import `in`.bizeneed.adapter.BannerAdapter
import `in`.bizeneed.adapter.MoreServicesAdapter
import `in`.bizeneed.adapter.ServicesAdapter
import `in`.bizeneed.databinding.FragmentHomeBinding
import `in`.bizeneed.extras.AppPrefData
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.extras.isConnected
import `in`.bizeneed.response.BannerData
import `in`.bizeneed.response.ServiceData
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private var countDownTimer: CountDownTimer ?= null
    private lateinit var bannerList : ArrayList<BannerData>
    private var servicesAdapter: ServicesAdapter ?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        loadData()

        binding.refresh.setOnRefreshListener {
            loadData()
        }

        binding.location.setOnClickListener {
            startActivity(Intent(activity1,Address::class.java))
        }

        /*binding.profile.setOnClickListener {
            startActivity(Intent(activity1,Profile::class.java))
        }*/

        binding.searchImage.setOnClickListener {
            startActivity(Intent(activity1,SearchActivity::class.java))
        }

        binding.searchEdt.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                servicesAdapter?.filter?.filter(p0)
            }

        })
    }

    private fun loadData(){
        bannerList()
        servicesList()
        singleBanner()
    }

    private fun singleBanner() {
        if (isConnected(activity1)){
            myViewModel.getBanners("2").observe(activity1, Observer {
                it.let {
                    it.let {
                        if (it.data.isNotEmpty()){
                            binding.bannerText.visibility = View.VISIBLE
                            binding.image.visibility = View.VISIBLE
                            Glide.with(activity1).load(Constants.IMAGE_URL + it.data[0].image).into(binding.image)
                        }else{
                            binding.bannerText.visibility = View.GONE
                            binding.image.visibility = View.GONE
                        }
                    }
                }
            })
        }
    }

    private fun initViews() {
        bannerList = ArrayList()
        val user = AppPrefData.user()!!
        if (user.city != null){
            binding.city.text = user.city
        }else{
            binding.city.text = ("New Delhi")
        }
    }

    private fun servicesList() {

        binding.refresh.isRefreshing = false
        if (isConnected(activity1)){
            showProgressBar(null)
            myViewModel.getServices().observe(activity1, Observer {
                hideProgress()
                it.let {

                    val list = it.data
                    val serviceList : ArrayList<ServiceData> = ArrayList()
                    val moreServiceList : ArrayList<ServiceData> = ArrayList()
                    for (i in list.indices){
                        if (i < 9){
                            serviceList.add(list[i])
                        }else {
                            moreServiceList.add(list[i])
                        }
                    }

                    servicesAdapter = ServicesAdapter(activity1,serviceList)
                    binding.servicesRcv.layoutManager = GridLayoutManager(activity1,3)
                    binding.servicesRcv.adapter = servicesAdapter
                    servicesAdapter?.notifyDataSetChanged()

                    moreServicesList(moreServiceList)
                }
            })
        }else{
            Toast.makeText(activity1,"No Internet Connection",Toast.LENGTH_SHORT).show()
        }
    }

    private fun moreServicesList(list : ArrayList<ServiceData>) {
        if (list.isNotEmpty()){
            binding.moreServicesLayout.visibility = View.GONE
            val moreServicesAdapter = MoreServicesAdapter(activity1)
            moreServicesAdapter.addItems(list)
            binding.moreServicesRcv.layoutManager = GridLayoutManager(activity1,2)
            binding.moreServicesRcv.adapter = moreServicesAdapter
            moreServicesAdapter.notifyDataSetChanged()
        }else{
            binding.moreServicesLayout.visibility = View.GONE
        }
    }

    private fun bannerList() {

        if (isConnected(activity1)){
            myViewModel.getBanners("1").observe(activity1, Observer {
                it.let {
                    bannerList.clear()
                    bannerList.addAll(it.data)
                    val bannerAdapter = BannerAdapter(activity1, bannerList)
                    binding.viewPager.adapter = bannerAdapter
                    bannerAdapter.notifyDataSetChanged()
                }
            })
        }

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

    override fun getLayoutRes(): Int = R.layout.fragment_home
}