package com.user.atozbasket.MerchantApp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.user.atozbasket.CustomerDetails.FeedBack;
import com.user.atozbasket.MainActivity;
import com.user.atozbasket.R;

public class MerchantActivity extends AppCompatActivity {

    Button button_main_app,button_orders,button_feedback,button_change_availability,button_sms_delivery,button_sms_feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);


        button_main_app=(Button)findViewById(R.id.main_app);
        button_orders=(Button)findViewById(R.id.orders);
        button_change_availability=(Button)findViewById(R.id.change_delivery_availability);
        button_feedback=(Button)findViewById(R.id.feedback);

        button_sms_delivery=(Button)findViewById(R.id.sms_after_delivery);
        button_sms_feedback=(Button)findViewById(R.id.sms_feedback);

        button_main_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        button_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(isInternetOn()==0)
                {
                    Toast.makeText(getApplicationContext(),"No Internet Connection!",Toast.LENGTH_SHORT).show();
                    return;
                }
             finish();
                startActivity(new Intent(getApplicationContext(),Orders.class));
            }
        });

        button_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isInternetOn()==0)
                {
                    Toast.makeText(getApplicationContext(),"No Internet Connection!",Toast.LENGTH_SHORT).show();
                    return;
                }
                finish();
                startActivity(new Intent(getApplicationContext(),Feedbacks.class));
            }
        });

        button_change_availability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
                startActivity(new Intent(getApplicationContext(),ChangeAvailability.class));

            }
        });

        button_sms_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),SMSDelivery.class));
            }
        });

        button_sms_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),FeedbackSMS.class));
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
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

            //super.onBackPressed();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Exit Application?");
            alertDialogBuilder
                    .setMessage("Are you sure ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                                    homeIntent.addCategory( Intent.CATEGORY_HOME );
                                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(homeIntent);
                                }
                            })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

    }
}
