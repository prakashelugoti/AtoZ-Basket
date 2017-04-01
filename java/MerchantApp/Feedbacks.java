package com.user.atozbasket.MerchantApp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.atozbasket.R;

public class Feedbacks extends AppCompatActivity {
    TextView textView;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbacks);

        textView=(TextView)findViewById(R.id.order_list);
        progressDialog=new ProgressDialog(this);

        progressDialog.setMessage("Loading feedbacks...Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        DatabaseReference databaseReference1= FirebaseDatabase.getInstance().
                getReferenceFromUrl("https://a-to-z-basket.firebaseio.com/Feedback/feedback");
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  x=(String) dataSnapshot.getValue();
                //Toast.makeText(MessageActivity.this,s,Toast.LENGTH_LONG).show();


                textView.setText(x);

                //textView.setText(s);
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
        startActivity(new Intent(getApplicationContext(),MerchantActivity.class));
    }
}
