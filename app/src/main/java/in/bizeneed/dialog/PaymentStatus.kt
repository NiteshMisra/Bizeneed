package `in`.bizeneed.dialog

import `in`.bizeneed.R
import `in`.bizeneed.activity.MainActivity
import `in`.bizeneed.databinding.PaymentDialogBinding
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment

class PaymentStatus(var isSuccessful : Boolean) : DialogFragment() {

    private lateinit var binding : PaymentDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.payment_dialog,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isSuccessful){
            binding.successful.visibility = View.VISIBLE
            binding.failed.visibility = View.GONE
            binding.message.text = ("Order Successful")
            binding.message.setTextColor(Color.parseColor("#538E01"))
        }else{
            binding.successful.visibility = View.GONE
            binding.failed.visibility = View.VISIBLE
            binding.message.text = ("Order Failed")
            binding.message.setTextColor(Color.parseColor("#FF0000"))
        }

        binding.okBtn.setOnClickListener {
            dismiss()
            val intent = Intent(requireContext(),MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

}