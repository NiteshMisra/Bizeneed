package `in`.bizeneed.fragments

import `in`.bizeneed.R
import `in`.bizeneed.databinding.FragmentRewardsBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide

class RewardsFragment : Fragment() {

    private lateinit var binding : FragmentRewardsBinding
    private lateinit var activity1 : FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_rewards, container, false)
        activity1 = requireActivity()
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = RewardsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(activity1).load(R.raw.reward_point).into(binding.rewardImage)
    }
}