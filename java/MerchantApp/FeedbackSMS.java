package com.user.atozbasket.MerchantApp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.user.atozbasket.R;

public class FeedbackSMS extends AppCompatActivity {
    EditText editText;
    Button button;

    private WebView wv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_sms);



        editText=(EditText)findViewById(R.id.edit_after_delivery);
        button=(Button)findViewById(R.id.send_sms_after_delivery);

        //web view
        wv1=(WebView)findViewById(R.id.webView);
        wv1.setVisibility(View.INVISIBLE);
        wv1.setWebViewClient(new MyBrowser());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=editText.getText().toString();

                if(s.length()<10)
                {
                    Toast.makeText(getApplicationContext(),"Enter valid number",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(isInternetOn()==0)
                {
                    Toast.makeText(getApplicationContext(),"No Internet!",Toast.LENGTH_SHORT).show();
                    return;
                }

                //textView.setText("SMS sent");
                String url = "http://api.infoskysolution.com/SendSMS/sendmsg.php?uname=AZBASKET&pass=thisisas&send=AZBSKT&dest="+s+"&msg=Dear customer, we have received your complaint. Please check your mail for further details.";

                //wv1.getSettings().setLoadsImagesAutomatically(true);
                // wv1.getSettings().setJavaScriptEnabled(true);
                //wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv1.loadUrl(url);

                Toast.makeText(getApplicationContext(),"SMS sent",Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(),MerchantActivity.class));


            }
        });
    }


    public final int isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet

            //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return 1;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            //Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return 0;
        }
        return 0;
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    @Override
    public void onBackPressed()
    {
        finish();
        startActivity(new Intent(getApplicationContext(),MerchantActivity.class));
    }
}
