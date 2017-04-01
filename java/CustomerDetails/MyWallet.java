package com.user.atozbasket.CustomerDetails;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.atozbasket.MainActivity;
import com.user.atozbasket.R;

public class MyWallet extends AppCompatActivity {
ImageView rupee_icon;
    TextView available_amount,textView;
    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    ListView listView;
    private DatabaseReference db;

    SharedPreferences sharedpreferences_number;
    public static final String mypreference_number = "savedata";
    public static final String my_number = "mynumber";
    public static final String my_name = "myname";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        firebaseAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);

        rupee_icon=(ImageView)findViewById(R.id.rupee_icon);

        available_amount=(TextView)findViewById(R.id.available_amout);
        textView=(TextView)findViewById(R.id.textView);

        sharedpreferences_number = getSharedPreferences(mypreference_number,
                Context.MODE_PRIVATE);

        String user_number=sharedpreferences_number.getString(my_number,"");

        progressDialog.setMessage("Loading your balance amount...Please wait!");
        progressDialog.show();
        progressDialog.setCancelable(false);

        DatabaseReference databaseReference1= FirebaseDatabase.getInstance().
                getReferenceFromUrl("https://a-to-z-basket.firebaseio.com/Users/"+user_number+"/wallet");
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               String  x=(String) dataSnapshot.getValue();
                //Toast.makeText(MessageActivity.this,s,Toast.LENGTH_LONG).show();


                textView.setText("\nAvailable Balance");
                available_amount.setText("\u20B9. "+x+"/-");
                rupee_icon.setImageResource(R.drawable.money_bag);

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
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
