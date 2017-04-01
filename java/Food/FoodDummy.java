package com.user.atozbasket.Food;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.atozbasket.Babycare.BabyCustomGridAdapter;
import com.user.atozbasket.CheckOut;
import com.user.atozbasket.Grocery.GroCustomGridAdapter;
import com.user.atozbasket.MainActivity;
import com.user.atozbasket.Personalcare.PersonalCustomGridAdapter;
import com.user.atozbasket.Picasso.ImageLoad;
import com.user.atozbasket.R;


public class FoodDummy extends AppCompatActivity {

    GridView gridView;

    static final String[] GRID_DATA = new String[] {"Vegetarian Food","Non-Vegetarian Food","Biscuits & Cookies",
            "Chips & Crisps", "Choclates & Candies","Snacks & Sweets"};

    Button small_button;

    ImageView imageView;
    SharedPreferences sharedpreferences_data;
    public static final String mypreference_data = "myprefdata";
    public static final String data_url = "data_url";
    public static final String data_name = "data_name";
    public static final String data_cost= "data_cost";


    SharedPreferences sharedpreferences_activity;
    public static final String mypreference_activity = "myprefdata";
    public static final String activity_name = "activity_name";
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_dummy);

        //Get gridview object from xml file
        gridView = (GridView) findViewById(R.id.gridView1);

        DatabaseReference databaseReference111= FirebaseDatabase.getInstance().
                getReferenceFromUrl("https://a-to-z-basket.firebaseio.com/DeliveryCharges");
        databaseReference111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  x=(String) dataSnapshot.getValue();
                //Toast.makeText(MessageActivity.this,s,Toast.LENGTH_LONG).show();




                //textView.setText("My Notifications");
                //notification.setText(x);
                //notifications_icon.setImageResource(R.drawable.notifications_bell);

                //textView.setText(s);
                //progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //textView.setText("Some error occured please try again!");
            }
        });

        sharedpreferences_data = getSharedPreferences(mypreference_data,
                Context.MODE_PRIVATE);

        sharedpreferences_activity = getSharedPreferences(mypreference_activity,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor2=sharedpreferences_activity.edit();
        editor2.remove(activity_name);
        editor2.commit();

        SharedPreferences.Editor editor=sharedpreferences_data.edit();
        editor.remove(data_cost);
        editor.remove(data_name);
        editor.remove(data_url);
        editor.commit();

        // Set custom adapter (GridAdapter) to gridview
        gridView.setAdapter(new FoodCustomGridAdapter(this, GRID_DATA));

        small_button=(Button)findViewById(R.id.small_button);

        imageView=(ImageView)findViewById(R.id.proceed);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CheckOut.class));
            }
        });


        small_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CheckOut.class));
            }
        });

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                String pos= String.valueOf(position);

                pos="3"+pos;

                if(checkconnection()==0)
                {
                    Toast.makeText(getApplicationContext(),"No Connection!",Toast.LENGTH_SHORT).show();
                    return;
                }
                /*Toast.makeText(
                        getApplicationContext(),
                        pos, Toast.LENGTH_SHORT).show();*/

                //startActivity(new Intent(getApplicationContext(), ImageLoad.class));
                Intent i = new Intent(getApplicationContext(), ImageLoad.class);
                i.putExtra("key", pos);
                startActivity(i);


            }
        });

    }
    @Override
    public void onBackPressed()
    {
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public int checkconnection()
    {
        ConnectivityManager cn=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf=cn.getActiveNetworkInfo();
        if(nf != null && nf.isConnected()==true )
        {
            //Toast.makeText(this, "Network Available", Toast.LENGTH_LONG).show();
            return 1;
            //tvstatus.setText("Network Available");
        }
        else
        {
            // Toast.makeText(this, "Network Not Available", Toast.LENGTH_LONG).show();
            return 0;
            //tvstatus.setText("Network Not Available");
        }
    }
}
