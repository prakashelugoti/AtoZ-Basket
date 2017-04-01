package com.user.atozbasket;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderPlaced extends AppCompatActivity {
TextView textView;

    private FirebaseAuth firebaseAuth;
    ListView listView;
    private DatabaseReference db;
    ProgressDialog progressDialog;

    String s;

String message;

    private WebView wv1,wv2;

    ImageView imageView;

    SharedPreferences sharedpreferences_number;
    public static final String mypreference_number = "savedata";
    public static final String my_number = "mynumber";
    public static final String my_name = "myname";


    SharedPreferences sharedpreferences_order_details;
    public static final String mypreference_order_details = "mypref";
    public static final String order_list = "order_list";
    public static final String order_cost="order_cost";

    SharedPreferences sharedpreferences_write_order;
    public static final String mypreference_write_order = "mypreference_write_order";
    public static final String full_order_list = "full_order_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);

        textView=(TextView)findViewById(R.id.order_paced_message);
        imageView=(ImageView)findViewById(R.id.order_placed_successfully);

        sharedpreferences_number = getSharedPreferences(mypreference_number,
                Context.MODE_PRIVATE);

        sharedpreferences_order_details = getSharedPreferences(mypreference_order_details,
                Context.MODE_PRIVATE);

        sharedpreferences_write_order=getSharedPreferences(mypreference_write_order,
                Context.MODE_PRIVATE);

        db= FirebaseDatabase.getInstance().getReference().child("Orders").child("orders_list");

        //web view
        wv1=(WebView)findViewById(R.id.webView);
        wv1.setVisibility(View.INVISIBLE);
        wv1.setWebViewClient(new MyBrowser());

        wv2=(WebView)findViewById(R.id.webView2);
        wv2.setVisibility(View.INVISIBLE);
        wv2.setWebViewClient(new MyBrowser());

        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();

        Bundle b = getIntent().getExtras();
        message = b.getString("key", "");

        progressDialog.setMessage("Placing your order...Please wait!");
        progressDialog.show();
        progressDialog.setCancelable(false);

        DatabaseReference databaseReference1= FirebaseDatabase.getInstance().
                getReferenceFromUrl("https://a-to-z-basket.firebaseio.com/Orders/orders_list");
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                s=(String) dataSnapshot.getValue();
                //Toast.makeText(MessageActivity.this,s,Toast.LENGTH_LONG).show();
                String data_add=message+s;
                db.setValue(data_add);

                DatabaseReference databaseReference2=FirebaseDatabase.getInstance().
                        getReferenceFromUrl("https://a-to-z-basket.firebaseio.com/OrderMessage/message");
                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       String x=(String) dataSnapshot.getValue();
                        //Toast.makeText(MessageActivity.this,s,Toast.LENGTH_LONG).show();
                        textView.setText(x);
                        imageView.setImageResource(R.drawable.order_placed_successfully);

                        DatabaseReference databaseReference3=FirebaseDatabase.getInstance().
                                getReferenceFromUrl("https://a-to-z-basket.firebaseio.com/DynamicNumbers");
                        databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                               String  z=(String) dataSnapshot.getValue();
                                //Toast.makeText(MessageActivity.this,s,Toast.LENGTH_LONG).show();


                                //textView.setText("SMS sent");
                                String url = "http://api.infoskysolution.com/SendSMS/sendmsg.php?uname=AZBASKET&pass=thisisas&send=AZBSKT&dest="+z+"&msg=Order has been arrived. Regards AZ BASKET";

                                //wv1.getSettings().setLoadsImagesAutomatically(true);
                                // wv1.getSettings().setJavaScriptEnabled(true);
                                //wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                                wv1.loadUrl(url);

                                String num=sharedpreferences_number.getString(my_number,"");
                                //textView.setText("SMS sent");
                                String url2 = "http://api.infoskysolution.com/SendSMS/sendmsg.php?uname=AZBASKET&pass=thisisas&send=AZBSKT&dest="+num+"&msg=Dear customer, your order has been confirmed. Regards A to Z Basket!";

                                //wv1.getSettings().setLoadsImagesAutomatically(true);
                                // wv1.getSettings().setJavaScriptEnabled(true);
                                //wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                                wv2.loadUrl(url2);

                                SharedPreferences.Editor editor2=sharedpreferences_order_details.edit();
                                editor2.remove(order_list);
                                editor2.remove(order_cost);
                                editor2.commit();

                                SharedPreferences.Editor editor=sharedpreferences_write_order.edit();
                                editor.remove(full_order_list);
                                editor.commit();


                                db= FirebaseDatabase.getInstance().getReference().child("Users").child(num).child("1Notifications");
                                db.setValue("Dear customer, your order has been confirmed!");

                                progressDialog.dismiss();


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                textView.setText("Some error occured please try again!");
                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        textView.setText("Some error occured please try again!");
                    }
                });


                //progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //textView.setText("Some error occured please try again!");
            }
        });




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
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}
