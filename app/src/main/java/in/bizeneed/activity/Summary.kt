package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivitySummaryBinding
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class Summary : AppCompatActivity() {

    private lateinit var binding : ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_summary)

        binding.crossedPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    }
}