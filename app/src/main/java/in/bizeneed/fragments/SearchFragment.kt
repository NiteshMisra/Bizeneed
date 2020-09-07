package `in`.bizeneed.fragments

import `in`.bizeneed.R
import `in`.bizeneed.adapter.ListingAdapter
import `in`.bizeneed.adapter.TagAdapter
import `in`.bizeneed.databinding.FragmentSearchBinding
import `in`.bizeneed.extras.Constants
import `in`.bizeneed.listener.ListingListener
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchFragment : Fragment(), ListingListener.TagListener {

    private var searchService: String? = null
    private lateinit var binding : FragmentSearchBinding
    private lateinit var activity1 : FragmentActivity
    private var tagSelectedIndex = 0
    private lateinit var tagAdapter: TagAdapter
    private lateinit var listingAdapter : ListingAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var tagLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            searchService = it.getString(Constants.SEARCH_SERVICE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search, container, false)
        activity1 = requireActivity()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tagList()
        listing()

        binding.listRcv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val position = layoutManager.findFirstVisibleItemPosition()
                listScrolledToPosition(position)
            }
        })
    }

    private fun listing() {
        layoutManager = LinearLayoutManager(activity1)
        binding.listRcv.layoutManager = layoutManager
        val list: ArrayList<Int> = ArrayList()
        for (i in 0..8)
            list.add(i)

        listingAdapter = ListingAdapter(activity1)
        listingAdapter.addItems(list)
        binding.listRcv.adapter = listingAdapter
        listingAdapter.notifyDataSetChanged()
    }

    private fun tagList() {
        tagLayoutManager = LinearLayoutManager(activity1,RecyclerView.HORIZONTAL,false)
        binding.tagRcv.layoutManager = tagLayoutManager
        val list: ArrayList<Int> = ArrayList()
        for (i in 0..8)
            list.add(i)

        tagAdapter = TagAdapter(activity1,tagSelectedIndex,this)
        tagAdapter.addItems(list)
        binding.tagRcv.adapter = tagAdapter
        tagAdapter.notifyDataSetChanged()
    }

    override fun tagSelected(position: Int) {
        tagAdapter.notifyItemChanged(tagSelectedIndex)
        tagSelectedIndex = position
        layoutManager.scrollToPositionWithOffset(position,20)
    }

    private fun listScrolledToPosition(position: Int){
        if (tagSelectedIndex != position){
            tagAdapter.selectedPosition(position)
            tagAdapter.notifyItemChanged(tagSelectedIndex)
            tagSelectedIndex = position
            tagAdapter.notifyItemChanged(tagSelectedIndex)
            tagLayoutManager.scrollToPositionWithOffset(tagSelectedIndex,20)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(searchService: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.SEARCH_SERVICE, searchService)
                }
            }
    }
}