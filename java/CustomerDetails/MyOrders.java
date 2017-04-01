package com.user.atozbasket.CustomerDetails;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.atozbasket.MainActivity;
import com.user.atozbasket.OrderPlaced;
import com.user.atozbasket.R;
import com.user.atozbasket.WriteOrder.WriteOrder;

public class MyOrders extends AppCompatActivity {
TextView my_orders;
    ImageView imageView;

    ProgressDialog progressDialog;

    SharedPreferences sharedpreferences_number;
    public static final String mypreference_number = "savedata";
    public static final String my_number = "mynumber";
    public static final String my_name = "myname";

    private DatabaseReference db;

    String user_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        progressDialog=new ProgressDialog(this);

        sharedpreferences_number = getSharedPreferences(mypreference_number,
                Context.MODE_PRIVATE);

        my_orders=(TextView)findViewById(R.id.my_orders);
        imageView=(ImageView)findViewById(R.id.start_shopping);

        progressDialog.setMessage("Loading your orders...Please wait!");
        progressDialog.show();
        progressDialog.setCancelable(false);

       user_number=sharedpreferences_number.getString(my_number,"");

        DatabaseReference databaseReference1= FirebaseDatabase.getInstance().
                getReferenceFromUrl("https://a-to-z-basket.firebaseio.com/Users/"+user_number+"/myorders");
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s=(String) dataSnapshot.getValue();


                if(s.equals("no orders!"))
                {
                    my_orders.setText("You don't have any orders. Start shopping!");
                    imageView.setImageResource(R.drawable.start_shopping);
                }
                else {
                    my_orders.setText(s);

                }

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //textView.setText("Some error occured please try again!");


            }
        });


    }

    @Override
    public void onBackPressed()
    {
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.clear_data, menu);

        //menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.notifications));
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear_data) {
            //Toast.makeText(getApplicationContext(),"abcd",Toast.LENGTH_SHORT).show();
            //menu.getItem(id).setIcon(getResources().getDrawable(R.drawable.notifications));
            if(checkconnection()==0)
            {
                Toast.makeText(getApplicationContext(),"No Connection!",Toast.LENGTH_SHORT).show();
                return true;

            }

            progressDialog.setMessage("Please wait! Clearing the data...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            db= FirebaseDatabase.getInstance().getReference().child("Users").child(user_number).child("myorders");
            db.setValue("no orders!");

            progressDialog.dismiss();
            finish();
            startActivity(new Intent(getApplicationContext(),MyOrders.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
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
