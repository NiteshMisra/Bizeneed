package `in`.bizeneed.fragments

import `in`.bizeneed.R
import `in`.bizeneed.activity.MainActivity
import `in`.bizeneed.adapter.OrderAdapter
import `in`.bizeneed.databinding.FragmentBookingsBinding
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ethanhua.skeleton.Skeleton

class BookingsFragment : BaseFragment<FragmentBookingsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bookingRcv.layoutManager = LinearLayoutManager(activity1)
        binding.dataView.visibility = View.VISIBLE
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
        var orderAdapter = OrderAdapter(activity1)
        val skeletonScreen = Skeleton.bind(binding.bookingRcv)
            .adapter(orderAdapter)
            .load(R.layout.element_transactions)
            .count(10)
            .show()
        myViewModel.fetchOrder().observe(activity1, Observer {
            skeletonScreen.hide()
            it?.let {
                if (it.data.isNotEmpty()){
                    binding.dataView.visibility = View.VISIBLE
                    binding.emptyView.visibility = View.GONE
                    orderAdapter = OrderAdapter(activity1)
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