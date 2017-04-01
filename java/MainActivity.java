package com.user.atozbasket;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.user.atozbasket.Babycare.BabyDummy;
import com.user.atozbasket.Beverages.BevaragesDummy;
import com.user.atozbasket.CustomerDetails.AboutUs;
import com.user.atozbasket.CustomerDetails.FeedBack;
import com.user.atozbasket.CustomerDetails.MyAddress;
import com.user.atozbasket.CustomerDetails.MyOrders;
import com.user.atozbasket.CustomerDetails.MyWallet;
import com.user.atozbasket.CustomerDetails.Notifications;
import com.user.atozbasket.Eggs.EggsDummy;
import com.user.atozbasket.Food.FoodDummy;
import com.user.atozbasket.Grocery.GroceryDummy;
import com.user.atozbasket.HouseHolds.HouseDummy;
import com.user.atozbasket.Personalcare.PersonalDummy;
import com.user.atozbasket.Vegetables.Dummy;
import com.user.atozbasket.WriteOrder.WriteOrder;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView img1,img2,img3,img4,img5;
    ProgressDialog progressDialog;
    private GridLayoutManager lLayout;


    SharedPreferences sharedpreferences_number;
    public static final String mypreference_number = "savedata";
    public static final String my_number = "mynumber";
    public static final String my_name = "myname";

    Button small_button;
    ImageView imageView;




    String s="";
    String[] url=new String[1000];

    public GridView grid;
    String[] web = {
            "Grocery",
            "Vegetables/Fruits",
            "House Holds",
            "Food & Bakery",
            "Eggs, Meat, Milk",
            "Beverage Items",
            "Personal Care",
            "Baby Care"

    } ;
    int[] imageId = {
            R.drawable.grocery_main,
            R.drawable.vegetables_main,
            R.drawable.house_final,
            R.drawable.bakery_main,
            R.drawable.meat_main,
            R.drawable.beverage_main,
            R.drawable.personalcare_main,
            R.drawable.babycare_main

    };


    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedpreferences_number = getSharedPreferences(mypreference_number,
                Context.MODE_PRIVATE);

        small_button=(Button)findViewById(R.id.small_button);
        imageView=(ImageView)findViewById(R.id.proceed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Necessary statement for setting desired colour in navigation view
        navigationView.setItemIconTintList(null);
        View hView =  navigationView.getHeaderView(0);

        TextView text_user_name = (TextView)hView.findViewById(R.id.user_name);
        TextView text_user_number = (TextView)hView.findViewById(R.id.user_number);

        String u_name=sharedpreferences_number.getString(my_name,"");
        String u_number=sharedpreferences_number.getString(my_number,"");

        text_user_name.setText("Welcome "+u_name+"!");
        text_user_number.setText("Your phone number: "+u_number);

        DatabaseReference databaseReference1= FirebaseDatabase.getInstance().
                getReferenceFromUrl("https://a-to-z-basket.firebaseio.com/DynamicNumbers");
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
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

        small_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),CheckOut.class));
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), CheckOut.class));
            }
        });

        /*ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper1);

        img1=(ImageView)findViewById(R.id.img1);
        img2=(ImageView)findViewById(R.id.img2);
        img3=(ImageView)findViewById(R.id.img3);
        img4=(ImageView)findViewById(R.id.img4);
        img5=(ImageView)findViewById(R.id.img5);

        progressDialog=new ProgressDialog(this);


        img1.setImageResource(R.drawable.grocery);
        img2.setImageResource(R.drawable.veg_fruits);
        img3.setImageResource(R.drawable.baby_care);
        img4.setImageResource(R.drawable.personal_care);
        img5.setImageResource(R.drawable.beverages);
        progressDialog.setMessage("Loading...");
        progressDialog.show();




       Picasso.with(getApplicationContext())
                .load("https://firebasestorage.googleapis.com/v0/b/a-to-z-basket.appspot.com/o/Banners%2Fbanner1.jpg?alt=media&token=d0a636a9-77a0-4103-9a21-0c58f8c649eb")
                .resize(1024,500).into(img1);

        Picasso.with(getApplicationContext())
                .load("https://firebasestorage.googleapis.com/v0/b/a-to-z-basket.appspot.com/o/Banners%2Fbanner2.png?alt=media&token=82fc68cc-9f37-4d5c-9c49-d7447287c74c")
                .resize(1024,500).into(img2);

        Picasso.with(getApplicationContext())
                .load("https://firebasestorage.googleapis.com/v0/b/a-to-z-basket.appspot.com/o/Banners%2Fbanner3.png?alt=media&token=73f7baa6-aeb3-4a56-869a-315dc66121a4")
                .resize(1024,500).into(img3);

        Picasso.with(getApplicationContext())
                .load("https://firebasestorage.googleapis.com/v0/b/a-to-z-basket.appspot.com/o/Banners%2Fbanner4.png?alt=media&token=52cbcbd9-c5cb-40ec-b55b-7f91ed34ba4a")
                .resize(1024,500).into(img4);

        Picasso.with(getApplicationContext())
                .load("https://firebasestorage.googleapis.com/v0/b/a-to-z-basket.appspot.com/o/Banners%2Fbanner5.jpg?alt=media&token=f8db05f3-7a83-406e-9cde-c2717110d264")
                .resize(1024,500).into(img5);

        progressDialog.dismiss();*/


        //flipper.startFlipping();


        CustomGrid adapter = new CustomGrid(MainActivity.this, web, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

                if(position==0)
                {
                    finish();
                    startActivity(new Intent(getApplicationContext(), GroceryDummy.class));
                }
                if(position==1)
                {
                    finish();
                    startActivity(new Intent(getApplicationContext(), Dummy.class));
                }
                if(position==2)
                {
                    finish();
                    startActivity(new Intent(getApplicationContext(), HouseDummy.class));
                }
                if(position==3)
                {
                    finish();
                    startActivity(new Intent(getApplicationContext(), FoodDummy.class));
                }
                if(position==4)
                {
                    finish();
                    startActivity(new Intent(getApplicationContext(), EggsDummy.class));
                }
                if(position==5)
                {
                    finish();
                    startActivity(new Intent(getApplicationContext(), BevaragesDummy.class));
                }
                if(position==6)
                {
                    finish();
                    startActivity(new Intent(getApplicationContext(), PersonalDummy.class));
                }
                if(position==7)
                {
                    finish();
                    startActivity(new Intent(getApplicationContext(), BabyDummy.class));
                }


            }
        });

        //List<ItemObject> rowListItem = getAllItemList();
        //lLayout = new GridLayoutManager(MainActivity.this, 2);

        //RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        //rView.setHasFixedSize(true);
        //rView.setLayoutManager(lLayout);

        //RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, rowListItem);
        //rView.setAdapter(rcAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
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
        if (id == R.id.action_notify) {
            //Toast.makeText(getApplicationContext(),"abcd",Toast.LENGTH_SHORT).show();
            //menu.getItem(id).setIcon(getResources().getDrawable(R.drawable.notifications));
            if(checkconnection()==0)
            {
                Toast.makeText(getApplicationContext(),"No Connection!",Toast.LENGTH_SHORT).show();
                return true;

            }
            finish();
            startActivity(new Intent(getApplicationContext(), Notifications.class));
            return true;
        }
        if(id==R.id.action_write_order_list)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), WriteOrder.class));
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_address) {

            finish();
            startActivity(new Intent(getApplicationContext(), MyAddress.class));

        } else if (id == R.id.my_orders) {

            if(checkconnection()==0)
            {
                Toast.makeText(getApplicationContext(),"No Connection!",Toast.LENGTH_SHORT).show();

            }
            else {
                finish();
                startActivity(new Intent(getApplicationContext(), MyOrders.class));
            }

        }  else if (id == R.id.feedback) {
            finish();
            startActivity(new Intent(getApplicationContext(), FeedBack.class));

        }
        else if (id == R.id.share_app) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.user.atozbasket&hl=en\nHey download this cool app dude!");
            startActivity(shareIntent);
        }
        else if (id == R.id.about_us) {
               finish();
            startActivity(new Intent(getApplicationContext(), AboutUs.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
