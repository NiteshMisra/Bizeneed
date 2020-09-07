package `in`.bizeneed.fragments

import `in`.bizeneed.R
import `in`.bizeneed.adapter.BookingAdapter
import `in`.bizeneed.adapter.BookingMonthAdapter
import `in`.bizeneed.databinding.FragmentBookingsBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager

class BookingsFragment : Fragment() {

    private lateinit var binding : FragmentBookingsBinding
    private lateinit var activity1 : FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_bookings, container, false)
        activity1 = requireActivity()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bookingRcv.layoutManager = LinearLayoutManager(activity1)
        val list : ArrayList<Int> = ArrayList()
        for (i in 0..10){
            list.add(i)
        }

        val bookingAdapter = BookingMonthAdapter(activity1)
        bookingAdapter.addItems(list)
        binding.bookingRcv.adapter = bookingAdapter
        bookingAdapter.notifyDataSetChanged()

    }

    companion object {
        @JvmStatic
        fun newInstance() = BookingsFragment()
    }
}