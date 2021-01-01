package in.bizeneed.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.bizeneed.BuildConfig;
import in.bizeneed.R;
import in.bizeneed.databinding.ActivityPaynowBinding;
import in.bizeneed.extras.AppPrefData;
import in.bizeneed.fragments.BaseFragment;

import static in.bizeneed.extras.MethodsKt.isConnected;


public class PayNowActivity extends AppCompatActivity implements PaymentResultListener
{
    ActivityPaynowBinding activityPaynowBinding;
    private ProgressDialog progressDialog;
    static String url = BuildConfig.BASE_URL+"payNow.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPaynowBinding = DataBindingUtil.setContentView(this,R.layout.activity_paynow);
        activityPaynowBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        activityPaynowBinding.payMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activityPaynowBinding.amount.getText().toString().trim().equals(""))
                    Toast.makeText(PayNowActivity.this, "Please Enter Amount To Proceed", Toast.LENGTH_SHORT).show();
                else if(activityPaynowBinding.amount.getText().toString().trim().equals("0"))
                    Toast.makeText(PayNowActivity.this, "Amount should be greater than zero", Toast.LENGTH_SHORT).show();
                else
                {
                    float amount=Float.parseFloat(activityPaynowBinding.amount.getText().toString());
                    amount = amount * 100;
                    startPayment(amount);
                    //onPaymentSuccess("success");
                }
            }
        });
    }
    private void startPayment(float amount)
    {
        Checkout checkout=new Checkout();
        checkout.setKeyID(BuildConfig.RZP_KEY);
        checkout.setImage(R.drawable.company_logo);
        //set Logo
        //  checkout.setImage(R.drawable.ic_launcher_background);
        ////reference current object
        final Activity activity=this;
        try{
            JSONObject options=new JSONObject();
            //options.put("order_id", getIntent().getStringExtra("orderId"));
            options.put("name", getString(R.string.app_name));
            options.put("description", "Service Pay Later Amount");
            options.put("currency", "INR");
            options.put("amount", amount);
            options.put("prefill.email", getIntent().getStringExtra("email"));
            options.put("prefill.contact", getIntent().getStringExtra("mobile"));
            options.put("payment_capture", 1);
            options.put("theme.color", "#538E01");
            checkout.open(activity,options);
        }
        catch(Exception e){
            Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onPaymentSuccess(String s) {

        showProgressDialog();
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response)
            {
                progressDialog.dismiss();
                if(response.equals("Done"))
                {
                    Toast.makeText(PayNowActivity.this, "Payment Done", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PayNowActivity.this,MainActivity.class));
                    setResult(0);
                    finish();
                }
                else {
                    Toast.makeText(PayNowActivity.this, "Not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(PayNowActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("orderId",getIntent().getStringExtra("orderId"));
                return map;
            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(PayNowActivity.this, new HurlStack());
        requestQueue1.add(stringRequest1);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }


    private void showProgressDialog()
    {
        progressDialog = new ProgressDialog(PayNowActivity.this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.progress_bar_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
