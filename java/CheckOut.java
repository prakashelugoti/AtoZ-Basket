package com.user.atozbasket;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.atozbasket.CustomerDetails.Notifications;
import com.user.atozbasket.Picasso.ImageLoad;
import com.user.atozbasket.WriteOrder.WriteOrder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class CheckOut extends AppCompatActivity {
TextView textView;

    SharedPreferences sharedpreferences_order_details;
    public static final String mypreference_order_details = "mypref";
    public static final String order_list = "order_list";
    public static final String order_cost="order_cost";

    TextView delivery_address;

    SharedPreferences sharedpreferences_full_address;
    public static final String mypreference_full_address = "mypreference_full_address";
    public static final String full_address = "full_address";

    SharedPreferences sharedpreferences_number;
    public static final String mypreference_number = "savedata";
    public static final String my_number = "mynumber";
    public static final String my_name = "myname";


    String complete_data="";

    String grand_total="";
Button button_check_out;

    Calendar calander;
    SimpleDateFormat simpledateformat;
    String Date;

    String data="";

    private WebView wv1;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference db,db2;

    String s;

    SharedPreferences sharedpreferences_edit_address;
    public static final String mypreference_edit_address = "mypreference_full_address";
    public static final String edit_address = "edit_address";
    public static final String area = "area";

    SharedPreferences sharedpreferences_activity;
    public static final String mypreference_activity = "myprefdata";
    public static final String activity_name = "activity_name";


    SharedPreferences sharedpreferences_write_order;
    public static final String mypreference_write_order = "mypreference_write_order";
    public static final String full_order_list = "full_order_list";

    ImageView edit_order;

    String[] data1=new String[1000];

    int start_count=0;

    int g=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);


        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        db= FirebaseDatabase.getInstance().getReference().child("Users");


        edit_order=(ImageView)findViewById(R.id.edit_order);
        textView=(TextView)findViewById(R.id.products_list);
        delivery_address=(TextView)findViewById(R.id.delivery_address);
        button_check_out=(Button)findViewById(R.id.check_out_button);

        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date = simpledateformat.format(calander.getTime());



        sharedpreferences_order_details = getSharedPreferences(mypreference_order_details,
                Context.MODE_PRIVATE);

        sharedpreferences_full_address = getSharedPreferences(mypreference_full_address,
                Context.MODE_PRIVATE);

        sharedpreferences_number = getSharedPreferences(mypreference_number,
                Context.MODE_PRIVATE);

        sharedpreferences_edit_address = getSharedPreferences(mypreference_edit_address,
                Context.MODE_PRIVATE);

        sharedpreferences_activity = getSharedPreferences(mypreference_activity,
                Context.MODE_PRIVATE);

        sharedpreferences_write_order=getSharedPreferences(mypreference_write_order,
                Context.MODE_PRIVATE);

        String u_name="Name: "+sharedpreferences_number.getString(my_name,"")+"\n";
        String u_number="Phone Number: "+sharedpreferences_number.getString(my_number,"")+"\n";

        String abcd=sharedpreferences_write_order.getString(full_order_list,"");

        //Toast.makeText(getApplicationContext(),abcd,Toast.LENGTH_SHORT).show();

        String s1,s2;

        s1=sharedpreferences_order_details.getString(order_cost,"");
        s2=sharedpreferences_order_details.getString(order_list,"");

        if(s2.equals(""))
            edit_order.setVisibility(View.INVISIBLE);

        if(abcd.equals("")&&s1.equals("")||(abcd.equals("")&&s1.equals("0")))
        {
            finish();
            //Toast.makeText(getApplicationContext(),abcd,Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),EmptyCart.class));
        }

        Random r = new Random();
        int i1 = r.nextInt(800000 - 300000) + 300000;

        String rand= "Order ID: "+String.valueOf(i1)+"\n";

        if(s1.equals(""))
        {
            SharedPreferences.Editor editor=sharedpreferences_order_details.edit();

            editor.putString(order_cost,"0");
            editor.commit();
        }

        for(int i=0;i<sharedpreferences_order_details.getString(order_list,"").length();i++)
        {
            char c=sharedpreferences_order_details.getString(order_list,"").charAt(i);

            if(c=='*')
                start_count++;
        }

        start_count=start_count/2;

        for(int i=0;i<start_count+1;i++)
            data1[i]="";

        for(int i=0;i<sharedpreferences_order_details.getString(order_list,"").length();i++)
        {
            char c=sharedpreferences_order_details.getString(order_list,"").charAt(i);

            if(c=='\n')
            {
                g++;
            }
            else
            {
                data1[g]=data1[g]+c;
            }
        }

        //Toast.makeText(getApplicationContext(),String.valueOf(g),Toast.LENGTH_LONG).show();

        String[] d1=new String[g+2];
        String[] d2=new String[g+2];
        String[] d3=new String[g+2];

        for(int i=0;i<g+1;i++)
        {
            d1[i]=d2[i]=d3[i]="";
        }

        int k=0;

        for(int i=0;i<g;i++)
        {

            for(int j=1;j<51;j++)
            {
                char c=data1[i].charAt(j);
                d1[k]=d1[k]+c;
            }

            for(int j=52;j<data1[i].length()-6;j++)
            {
                char c=data1[i].charAt(j);
                d2[k]=d2[k]+c;
            }

            for(int j=data1[i].length()-5;j<data1[i].length()-1;j++)
            {
                char c=data1[i].charAt(j);
                d3[k]=d3[k]+c;
            }

            k++;

        }

        String ins="";

        for(int i=0;i<k;i++)
        {
            int f=i+1;
            ins=ins+f+") "+d1[i]+"\n";
            ins=ins+"   Qunatity: "+d2[i]+"\n";
            ins=ins+"   Price(Per Item): Rs."+d3[i]+"/-\n\n";
        }




        if(!sharedpreferences_order_details.getString(order_list,"").equals(""))
        data="Basket List:\n"+ins+"\n";
        else
        data="";



        if(abcd.equals(""))
        data=data+sharedpreferences_write_order.getString(full_order_list,"");
        else
        data=data+"\nMy Own Order List:\n"+sharedpreferences_write_order.getString(full_order_list,"")+"\n\n";

        String xyz="";

        if(abcd.equals(""))
            xyz="";
        else
        xyz="\n(NOTE: Extra amount to be paid for the products you have written in your own list).\n";






        DatabaseReference databaseReference11= FirebaseDatabase.getInstance().
                getReferenceFromUrl("https://a-to-z-basket.firebaseio.com/DeliveryCharges");
        databaseReference11.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  x=(String) dataSnapshot.getValue();
                //Toast.makeText(MessageActivity.this,s,Toast.LENGTH_LONG).show();




                //textView.setText("My Notifications");
                //notification.setText(x);
                //notifications_icon.setImageResource(R.drawable.notifications_bell);

                //textView.setText(s);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //textView.setText("Some error occured please try again!");
            }
        });


        data=data+"-----------------------\nTotal Amount: Rs."+sharedpreferences_order_details.getString(order_cost,"")+"/-"+xyz+"\n";


        String address=u_name+u_number+"Address: "+sharedpreferences_edit_address.getString(edit_address,"")+"\nArea: "+
                sharedpreferences_edit_address.getString(area,"")+"\n";




        complete_data=data+address;

        int c=0;
        String olist=ins;


        for(int i=0;i<sharedpreferences_order_details.getString(order_list,"").length();i++)
        {
            if(olist.charAt(i)=='\n')
                c++;
        }

        String abc= String.valueOf(c);
        //Toast.makeText(getApplicationContext(),abc,Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();


        button_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isInternetOn()==0)
                {
                    Toast.makeText(getApplicationContext(),"Internet Connection Lost!",Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("Caluclating delivery charges....");
                progressDialog.show();
                progressDialog.setCancelable(false);
                DatabaseReference databaseReference0= FirebaseDatabase.getInstance().
                        getReferenceFromUrl("https://a-to-z-basket.firebaseio.com/DeliveryCharges");
                databaseReference0.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String  x=(String) dataSnapshot.getValue();
                        //Toast.makeText(MessageActivity.this,s,Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                        int amt= Integer.parseInt(sharedpreferences_order_details.getString(order_cost,""));

                        int cst= Integer.parseInt(x);


                        amt=amt+cst;

                grand_total="\nGrand Total(with delivery charges): Rs."+String.valueOf(amt)+"/-\n";


                        String disp="Actual amount: Rs."+sharedpreferences_order_details.getString(order_cost,"")+"/-\n";
                        disp=disp+"Delivery Charges: Rs."+x+"/-"+grand_total;
//super.onBackPressed();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CheckOut.this);
                        alertDialogBuilder.setTitle("Place Order ?");
                        alertDialogBuilder
                                .setMessage(disp)
                                .setCancelable(false)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                if(isInternetOn()==0)
                                                {
                                                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                final String user_number=sharedpreferences_number.getString(my_number,"");

                                                final String order_time="Order Timings: "+Date;
                                            grand_total=grand_total+order_time+"\n----------------------------------------------------------\n\n";

                                                progressDialog.setMessage("Placing order...Please wait!");
                                                progressDialog.show();

                                                DatabaseReference databaseReference1=FirebaseDatabase.getInstance().
                                                        getReferenceFromUrl("https://a-to-z-basket.firebaseio.com/Users/"+user_number+"/myorders");
                                                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        s=(String) dataSnapshot.getValue();
                                                        //Toast.makeText(MessageActivity.this,s,Toast.LENGTH_LONG).show();

                                                        if(s.equals("no orders!"))
                                                            s="";
                                                        //textView.setText(s);
                                                        DatabaseReference databaseReference1= FirebaseDatabase.getInstance().
                                                                getReferenceFromUrl("https://a-to-z-basket.firebaseio.com/OrderID/count");
                                                        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                String  x=(String) dataSnapshot.getValue();
                                                                //Toast.makeText(MessageActivity.this,s,Toast.LENGTH_LONG).show();

                                                                int id= Integer.parseInt(x);

                                                                data="Order ID: "+String.valueOf(id)+"\n"+data;
                                                                complete_data="Order ID: "+String.valueOf(id)+"\n"+complete_data+grand_total;

                                                                id=id+1;
                                                                x= String.valueOf(id);


                                                                db= FirebaseDatabase.getInstance().getReference().child("Users").child(user_number).child("myorders");

                                                                db2= FirebaseDatabase.getInstance().getReference().child("OrderID").child("count");

                                                                String my_orders_string=data+grand_total+"\n\n"+s;
                                                                db.setValue(my_orders_string);
                                                                db2.setValue(x);

                                                                progressDialog.dismiss();

                                                                Intent i = new Intent(getApplicationContext(), OrderPlaced.class);
                                                                i.putExtra("key", complete_data);
                                                                startActivity(i);

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

                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                        textView.setText("Some error occured please try again!");
                                                    }
                                                });
                                            }
                                        })

                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                        //textView.setText("My Notifications");
                        //notification.setText(x);
                        //notifications_icon.setImageResource(R.drawable.notifications_bell);

                        //textView.setText(s);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //textView.setText("Some error occured please try again!");
                    }
                });



                //textView.setText("SMS sent");
                //String url = "http://api.infoskysolution.com/SendSMS/sendmsg.php?uname=FDZAMOT&pass=thisisas&send=FDZAMO&dest=919479675817,91"+decode_num+",919425725423&msg=";


                //wv1.loadUrl(url);


            }
        });


        edit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),PlaceOrder.class));
            }
        });

            textView.setText(data);
            //delivery_address.setText(sharedpreferences_full_address.getString(full_address,"")+"\n");
            String delivery_details=u_name+u_number;
            delivery_details=delivery_details+"Address:"+sharedpreferences_edit_address.getString(edit_address,"")+"\n";
            delivery_details=delivery_details+"Area: "+sharedpreferences_edit_address.getString(area,"");


            delivery_address.setText(delivery_details);








    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.checkout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_empty_cart) {
            //Toast.makeText(getApplicationContext(),"abcd",Toast.LENGTH_SHORT).show();


            empty_cart();
            return true;
        }
        if(id==R.id.action_write_order_list)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), WriteOrder.class));
            return true;
        }
        if(id==R.id.action_use_wallet_cash)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), WriteOrder.class));
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    public void empty_cart()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Empty Cart ?");
        alertDialogBuilder
                .setMessage("All the items will be deleted !")
                .setCancelable(false)
                .setPositiveButton("Clear",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences.Editor editor4=sharedpreferences_write_order.edit();
                                editor4.remove(full_order_list);
                                editor4.commit();

                                SharedPreferences.Editor editor2=sharedpreferences_order_details.edit();
                                editor2.remove(order_list);
                                editor2.remove(order_cost);
                                editor2.commit();

                                finish();
                                startActivity(new Intent(getApplicationContext(),EmptyCart.class));

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

    @Override
    public void onBackPressed()
    {
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

}
