package com.user.atozbasket.MerchantApp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.atozbasket.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChangeAvailability extends AppCompatActivity {
Button button_today,button_tommorrow;

    private DatabaseReference db1,db2;

    Calendar calander;
    SimpleDateFormat simpledateformat;
    String Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_availability);

        button_today=(Button)findViewById(R.id.deliver_today);
        button_tommorrow=(Button)findViewById(R.id.deliver_tommorrow);

        db1= FirebaseDatabase.getInstance().getReference().child("OrderMessage").child("message");

        //final java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        //mDateDisplay.setText(dateFormat.format(date))

        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("dd-MM-yyyy");
        Date = simpledateformat.format(calander.getTime());

        button_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isInternetOn()==0)
                {
                    Toast.makeText(getApplicationContext(),"No Internet!",Toast.LENGTH_SHORT).show();
                    return;
                }

                //String date=Date;
                String val="Dear customer, your order will be delivered by 6PM to 8PM TODAY.\n(Payment mode Cash On Delivery)";

                db1.setValue(val);

                Toast.makeText(getApplicationContext(),"Delivery by Today",Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(),MerchantActivity.class));
            }
        });

        button_tommorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isInternetOn()==0)
                {
                    Toast.makeText(getApplicationContext(),"No Internet!",Toast.LENGTH_SHORT).show();
                    return;
                }

                String val="Dear customer, your order will be delivered by 6PM to 8PM TOMMORROW.\n(Payment mode Cash On Delivery)";

                db1.setValue(val);


                Toast.makeText(getApplicationContext(),"Delivery by Tommorrow",Toast.LENGTH_SHORT).show();
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
    @Override
    public void onBackPressed()
    {
        finish();
        startActivity(new Intent(getApplicationContext(),MerchantActivity.class));
    }
}
