package com.user.atozbasket.CustomerDetails;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.atozbasket.MainActivity;
import com.user.atozbasket.R;

public class FeedBack extends AppCompatActivity {
EditText editText;
    Button button;
    ProgressDialog progressDialog;
    String data="";

    private DatabaseReference db,db2;

    SharedPreferences sharedpreferences_number;
    public static final String mypreference_number = "savedata";
    public static final String my_number = "mynumber";
    public static final String my_name = "myname";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        editText=(EditText)findViewById(R.id.write_complaint);
        button=(Button)findViewById(R.id.submit_complaint);
        progressDialog=new ProgressDialog(this);

        db= FirebaseDatabase.getInstance().getReference().child("Feedback");

        sharedpreferences_number = getSharedPreferences(mypreference_number,
                Context.MODE_PRIVATE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             if(checkconnection()==0)
               {
    Toast.makeText(getApplicationContext(),"No Connection!",Toast.LENGTH_SHORT).show();
    return;
              }
                data=editText.getText().toString()+"\n------------------------------------\n\n";

                progressDialog.setMessage("Submitting your request...Please wait!");
                progressDialog.show();
                progressDialog.setCancelable(false);
                //progressDialog.setCancelable(false);

                DatabaseReference databaseReference1= FirebaseDatabase.getInstance().
                        getReferenceFromUrl("https://a-to-z-basket.firebaseio.com/Feedback/feedback");
                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String s=(String) dataSnapshot.getValue();
                        //Toast.makeText(MessageActivity.this,s,Toast.LENGTH_LONG).show();

                        String u_name="\nName: "+sharedpreferences_number.getString(my_name,"")+"\n";
                        String u_number="Phone Number: "+sharedpreferences_number.getString(my_number,"")+"\n";

                        String d=u_name+u_number+"Complaint:\n"+data+s;

                        db.child("feedback").setValue(d);

                 db2= FirebaseDatabase.getInstance().getReference().child("Users").child(sharedpreferences_number.getString(my_number,"")).child("1Notifications");
                        db2.setValue("Dear customer, thanks for your feedback. We will get back to you soon!");


                        //textView.setText(s);
                        progressDialog.dismiss();

                        get_alert();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //textView.setText("Some error occured please try again!");
                    }
                });






            }
        });

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
    public void get_alert()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(
                FeedBack.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Thank You!");
        alertDialog.setCancelable(false);
        // Setting Dialog Message
        alertDialog.setMessage("We have received your feedback/complaint. We will get back to you soon.");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.tick);

        // Setting OK Button
        alertDialog. setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    @Override
    public void onBackPressed()
    {
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
