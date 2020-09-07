package `in`.bizeneed.fragments

import `in`.bizeneed.R
import `in`.bizeneed.adapter.RecentSearchAdapter
import `in`.bizeneed.databinding.FragmentRecentSearchesBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager

class RecentSearches : Fragment() {

    private lateinit var binding : FragmentRecentSearchesBinding
    private lateinit var activity1 : FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_recent_searches, container, false)
        activity1 = requireActivity()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recentSearchRcv.layoutManager = LinearLayoutManager(activity1)

        val list : ArrayList<Int> = ArrayList()
        for (i in 0..4)
            list.add(i)

        val recentSearchAdapter = RecentSearchAdapter(activity1)
        recentSearchAdapter.addItems(list)
        binding.recentSearchRcv.adapter = recentSearchAdapter
        recentSearchAdapter.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance() = RecentSearches()
    }
}