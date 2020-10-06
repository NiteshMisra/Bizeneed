package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.adapter.WalletAdapter
import `in`.bizeneed.databinding.ActivityWalletHistoryBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager

class WalletHistory : BaseActivity<ActivityWalletHistoryBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.backBtnIv.setOnClickListener { onBackPressed() }

        binding.walletRcv.layoutManager = GridLayoutManager(this,2)
        binding.walletRcv.setHasFixedSize(true)
        showProgressBar(null)
        myViewModel.fetchWalletHistory().observe(this, Observer {
            hideProgress()
            it?.let {
                val walletAdapter = WalletAdapter(this)
                walletAdapter.addItems(it.data)
                binding.walletRcv.adapter = walletAdapter
                walletAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun getLayoutRes(): Int = R.layout.activity_wallet_history
}