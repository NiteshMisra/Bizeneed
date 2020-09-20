package `in`.bizeneed.fragments

import `in`.bizeneed.R
import `in`.bizeneed.activity.MainActivity
import `in`.bizeneed.adapter.BookingAdapter
import `in`.bizeneed.adapter.BookingMonthAdapter
import `in`.bizeneed.adapter.OrderAdapter
import `in`.bizeneed.databinding.FragmentBookingsBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

class BookingsFragment : BaseFragment<FragmentBookingsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bookingRcv.layoutManager = LinearLayoutManager(activity1)
        binding.dataView.visibility = View.GONE
        binding.emptyView.visibility = View.GONE

        loadData()

        binding.refresh.setOnRefreshListener {
            loadData()
        }

        binding.bookServiceBtn.setOnClickListener {
            if (activity1 is MainActivity){
                (activity1 as MainActivity).moveToHome()
            }
        }

    }

    private fun loadData() {
        binding.refresh.isRefreshing = false
        showProgressBar(null)
        myViewModel.fetchOrder().observe(activity1, Observer {
            hideProgress()
            it?.let {
                if (it.data.isNotEmpty()){
                    binding.dataView.visibility = View.VISIBLE
                    binding.emptyView.visibility = View.GONE
                    val orderAdapter = OrderAdapter(activity1)
                    orderAdapter.addItems(it.data)
                    binding.bookingRcv.adapter = orderAdapter
                    orderAdapter.notifyDataSetChanged()
                }else{
                    binding.dataView.visibility = View.GONE
                    binding.emptyView.visibility = View.VISIBLE
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = BookingsFragment()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_bookings
}