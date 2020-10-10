package `in`.bizeneed.fragments

import `in`.bizeneed.R
import `in`.bizeneed.activity.Profile
import `in`.bizeneed.activity.ServiceDetail
import `in`.bizeneed.adapter.BannerAdapter
import `in`.bizeneed.adapter.MoreServicesAdapter
import `in`.bizeneed.adapter.ServicesAdapter
import `in`.bizeneed.databinding.FragmentHomeBinding
import `in`.bizeneed.extras.AppPrefData
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.response.BannerData
import `in`.bizeneed.response.ServiceData
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.ethanhua.skeleton.Skeleton
import com.google.gson.Gson

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

        /*binding.location.setOnClickListener {
            startActivity(Intent(activity1,Address::class.java))
        }*/

        binding.profile.setOnClickListener {
            startActivity(Intent(activity1, Profile::class.java))
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
        myViewModel.getBanners("2").observe(activity1, Observer {
            if (it == null){
                binding.image.visibility = View.GONE
                return@Observer
            }

            if (it.data.isEmpty()){
                binding.image.visibility = View.GONE
                return@Observer
            }

            val currentImage = it.data[0]
            binding.image.visibility = View.VISIBLE

            Glide.with(activity1)
                .asBitmap()
                .load(Constants.IMAGE_URL + currentImage.image)
                .override(1600,1600)
                .into(BitmapImageViewTarget(binding.image))

            binding.image.setOnClickListener {
                if (currentImage.subCategory != null && currentImage.subCategory.isNotEmpty()){
                    val intent = Intent(activity1, ServiceDetail::class.java)
                    intent.putExtra(Constants.DATA, Gson().toJson(currentImage.subCategory[0]))
                    intent.putExtra(Constants.IS_PURCHASED,false)
                    activity1.startActivity(intent)
                }
            }
        })
    }

    private fun initViews() {
        bannerList = ArrayList()
    }

    override fun onStart() {
        super.onStart()
        val user = AppPrefData.user()!!
        binding.userName.text = user.name
        if (user.profile != null){
            Glide.with(activity1)
                .asBitmap()
                .load(Constants.PROFILE_URL + user.profile)
                .placeholder(R.drawable.boy)
                .override(1600,1600)
                .into(BitmapImageViewTarget(binding.userProfile))
        }
    }

    private fun servicesList() {

        binding.servicesRcv.layoutManager = GridLayoutManager(activity1,3)
        val skeletonScreen = Skeleton.bind(binding.servicesRcv)
            .adapter(servicesAdapter)
            .load(R.layout.element_services)
            .count(6)
            .show()
        binding.refresh.isRefreshing = false
        myViewModel.getServices().observe(activity1, Observer {
            skeletonScreen.hide()
            it?.let {

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
                binding.servicesRcv.adapter = servicesAdapter
                servicesAdapter?.notifyDataSetChanged()

                moreServicesList(moreServiceList)
            }
        })
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
        binding.bannerLayout.visibility = View.VISIBLE
        binding.viewPager.visibility = View.GONE
        myViewModel.getBanners("1").observe(activity1, Observer {
            it?.let {
                binding.bannerLayout.visibility = View.GONE
                binding.viewPager.visibility = View.VISIBLE
                bannerList.clear()
                bannerList.addAll(it.data)
                val bannerAdapter = BannerAdapter(activity1, bannerList)
                binding.viewPager.adapter = bannerAdapter
                bannerAdapter.notifyDataSetChanged()
            }
        })

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