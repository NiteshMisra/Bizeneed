package `in`.bizeneed.dialog

import `in`.bizeneed.R
import `in`.bizeneed.activity.Summary
import `in`.bizeneed.databinding.PayLaterDialogBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity

class PayLaterDialog(
    private var totalAmount: Int,
    private var discount: Int,
    private var cashBack: Int,
    private var walletAmount: Int,
    var amount: Int,
    private var isOffline: Boolean
) : DialogFragment() {

    private lateinit var binding: PayLaterDialogBinding
    private lateinit var activity1 : FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.pay_later_dialog, container, false)
        activity1 = requireActivity()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isOffline){
            binding.message.text = ("If you choose 'Pay Later' service then you won't be able to take benefits of cashBack and discounts.")
        }else{
            binding.message.text = ("Online Payment")
        }

        binding.cashBack.text = ("₹ $cashBack")
        binding.mrp.text = ("₹$totalAmount")
        binding.discount.text = ("₹$discount")
        binding.walletDeduct.text = ("₹$walletAmount")
        binding.sellingPrice.text = ("₹$amount")

        binding.proceedBtn.setOnClickListener {
            dismiss()
            if (activity1 is Summary){
                if (isOffline){
                    (activity1 as Summary).proceedOrderApi("Offline")
                }else{
                    if (walletAmount == amount) {
                        (activity1 as Summary).proceedOrderApi("Online")
                    } else {
                        (activity1 as Summary).proceedToPay()
                    }
                }
            }else{
                Toast.makeText(activity1,"Some error occurred,Try again later",Toast.LENGTH_SHORT).show()
            }

        }

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

}