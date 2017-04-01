package com.user.atozbasket.Picasso;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.atozbasket.Babycare.BabyDummy;
import com.user.atozbasket.Beverages.BevaragesDummy;
import com.user.atozbasket.CheckOut;
import com.user.atozbasket.CustomerDetails.Notifications;
import com.user.atozbasket.Eggs.EggsDummy;
import com.user.atozbasket.Food.FoodDummy;
import com.user.atozbasket.Grocery.GroceryDummy;
import com.user.atozbasket.HouseHolds.HouseDummy;
import com.user.atozbasket.Personalcare.PersonalDummy;
import com.user.atozbasket.PlaceOrder;
import com.user.atozbasket.R;
import com.user.atozbasket.SubSearch;
import com.user.atozbasket.Vegetables.Dummy;

import java.util.ArrayList;

import static com.user.atozbasket.R.id.button;
import static com.user.atozbasket.R.id.imageView;
import static java.security.AccessController.getContext;

public class ImageLoad extends AppCompatActivity {



    private final String android_version_names[] = {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow"
    };

    private final String android_image_urls[] = {
            "https://goo.gl/wDwDXf",
            "https://goo.gl/wDwDXf",
            "https://goo.gl/wDwDXf",
            "https://goo.gl/wDwDXf",
            "https://goo.gl/wDwDXf",
            "https://goo.gl/wDwDXf",
            "https://goo.gl/wDwDXf",
            "https://goo.gl/wDwDXf",
            "https://goo.gl/wDwDXf",
            "https://goo.gl/wDwDXf"
    };
    private FirebaseAuth firebaseAuth;
    private DatabaseReference db;
    ProgressDialog progressDialog;
    String s_url="",s_name="",s_price="";
    int length=0;

    String[] url=new String[1000];
    String[] name=new String[1000];
    String[] cost=new String[1000];

    int[] item_quantity=new int[400];
    String[] item_name=new String[400];
    String[] item_cost=new String[400];

    SharedPreferences sharedpreferences_order_details;
    public static final String mypreference_order_details = "mypref";
    public static final String order_list = "order_list";
    public static final String order_cost="order_cost";

    SharedPreferences sharedpreferences_data;
    public static final String mypreference_data = "myprefdata";
    public static final String data_url = "data_url";
    public static final String data_name = "data_name";
    public static final String data_cost= "data_cost";

    SharedPreferences sharedpreferences_activity;
    public static final String mypreference_activity = "myprefdata";
    public static final String activity_name = "activity_name";



    int flag1=0,flag2=0;

    ImageView imageView;

    String link_name,link_cost,link_image;

    String message;

    String old_list="",old_cost="0";

    Button small_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);



        Bundle b = getIntent().getExtras();
        message = b.getString("key", "");

        //Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

        sharedpreferences_order_details = getSharedPreferences(mypreference_order_details,
                Context.MODE_PRIVATE);

        sharedpreferences_data = getSharedPreferences(mypreference_data,
                Context.MODE_PRIVATE);

        sharedpreferences_activity = getSharedPreferences(mypreference_activity,
                Context.MODE_PRIVATE);

        old_list=sharedpreferences_order_details.getString(order_list,"");
        old_cost=sharedpreferences_order_details.getString(order_cost,"");

        if(old_cost.equals(""))
            old_cost="0";

        //Toast.makeText(getApplicationContext(),sharedpreferences_order_details.getString(order_cost,""),Toast.LENGTH_SHORT).show();


        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        imageView=(ImageView)findViewById(R.id.proceed);
        small_button=(Button)findViewById(R.id.small_button);




        //Grocery

        SharedPreferences.Editor editor=sharedpreferences_activity.edit();
        editor.putString(activity_name,message);
        editor.commit();

        if(message.equals("00"))
        {
            link_image="https://a-to-z-basket.firebaseio.com/Products/Grocery/DalPulses/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/Grocery/DalPulses/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/Grocery/DalPulses/price";
        }
        else if(message.equals("01"))
        {
            link_image="https://a-to-z-basket.firebaseio.com/Products/Grocery/DryFruits/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/Grocery/DryFruits/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/Grocery/DryFruits/price";
        }
        else if(message.equals("02"))
        {
            link_image="https://a-to-z-basket.firebaseio.com/Products/Grocery/EdibleOilsGhee/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/Grocery/EdibleOilsGhee/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/Grocery/EdibleOilsGhee/price";
        }
        else if(message.equals("03"))
        {
            link_image="https://a-to-z-basket.firebaseio.com/Products/Grocery/Spices/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/Grocery/Spices/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/Grocery/Spices/price";
        }
        else if(message.equals("04"))
        {
            link_image="https://a-to-z-basket.firebaseio.com/Products/Grocery/RiceFlour/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/Grocery/RiceFlour/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/Grocery/RiceFlour/price";
        }
        else if(message.equals("05"))
        {
            link_image="https://a-to-z-basket.firebaseio.com/Products/Grocery/SaltSugar/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/Grocery/SaltSugar/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/Grocery/SaltSugar/price";
        }
        //Vegetables, Fruits
        else if(message.equals("10"))
        {
            link_image="https://a-to-z-basket.firebaseio.com/Products/VegetablesFruits/Vegetables/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/VegetablesFruits/Vegetables/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/VegetablesFruits/Vegetables/price";
        }
        else if(message.equals("11")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/VegetablesFruits/Fruits/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/VegetablesFruits/Fruits/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/VegetablesFruits/Fruits/price";
        }
        else if(message.equals("20")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/HouseHolds/DishWashers/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/HouseHolds/DishWashers/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/HouseHolds/DishWashers/price";
        }
        else if(message.equals("21")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/HouseHolds/HomePoojaNeeds/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/HouseHolds/HomePoojaNeeds/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/HouseHolds/HomePoojaNeeds/price";
        }
        else if(message.equals("22")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/HouseHolds/LaundryDetergents/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/HouseHolds/LaundryDetergents/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/HouseHolds/LaundryDetergents/price";
        }
        else if(message.equals("23")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/HouseHolds/RepellentsFreshners/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/HouseHolds/RepellentsFreshners/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/HouseHolds/RepellentsFreshners/price";
        }


        //Bakery Items


       if(message.equals("30")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/Veg/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/Veg/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/Veg/price";
        }
        else if(message.equals("31")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/NonVeg/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/NonVeg/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/NonVeg/price";
        }
        else if(message.equals("32")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/BiscuitsCookies/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/BiscuitsCookies/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/BiscuitsCookies/price";
        }
        else if(message.equals("33")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/ChipsCrisps/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/ChipsCrisps/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/ChipsCrisps/price";
        }
        else if(message.equals("34")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/ChoclatesCandies/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/ChoclatesCandies/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/ChoclatesCandies/price";
        }
        else if(message.equals("35")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/SnacksSweets/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/SnacksSweets/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/FoodBakery/SnacksSweets/price";
        }


        //Eggs Meat
        else if(message.equals("40")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/EggMeat/MilkProducts/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/EggMeat/MilkProducts/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/EggMeat/MilkProducts/price";
        }
        else if(message.equals("41")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/EggMeat/Meat/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/EggMeat/Meat/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/EggMeat/Meat/price";
        }
        else if(message.equals("42")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/EggMeat/Eggs/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/EggMeat/Eggs/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/EggMeat/Eggs/price";
        }

        //Bevarages

        else if(message.equals("50")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/Beverages/FruitsDrinksJuices/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/Beverages/FruitsDrinksJuices/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/Beverages/FruitsDrinksJuices/price";
        }
        else if(message.equals("51")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/Beverages/HealthyEnergyDrinks/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/Beverages/HealthyEnergyDrinks/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/Beverages/HealthyEnergyDrinks/price";
        }
        else if(message.equals("52")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/Beverages/SoftDrinks/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/Beverages/SoftDrinks/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/Beverages/SoftDrinks/price";
        }
        else if(message.equals("53")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/Beverages/TeaCoffee/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/Beverages/TeaCoffee/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/Beverages/TeaCoffee/price";
        }

        //Personal Care

        else if(message.equals("60")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/DeodorantsCosmetics/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/DeodorantsCosmetics/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/DeodorantsCosmetics/price";
        }
        else if(message.equals("61")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/FeminineHygiene/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/FeminineHygiene/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/FeminineHygiene/price";
        }
        else if(message.equals("62")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/HairCare/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/HairCare/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/HairCare/price";
        }
        else if(message.equals("63")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/OralCare/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/OralCare/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/OralCare/price";
        }
        else if(message.equals("64")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/OTCWellness/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/OTCWellness/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/OTCWellness/price";
        }
        else if(message.equals("65")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/ShavingHairRemoval/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/ShavingHairRemoval/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/ShavingHairRemoval/price";
        }
        else if(message.equals("66")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/SkinCare/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/SkinCare/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/SkinCare/price";
        }
        else if(message.equals("67")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/SoapsFaceHand Wash/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/SoapsFaceHand Wash/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/PersonalCare/SoapsFaceHand Wash/price";
        }

        //Baby Care

        else if(message.equals("70")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/BabyCare/BabyAccesories/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/BabyCare/BabyAccesories/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/BabyCare/BabyAccesories/price";
        }
        else if(message.equals("71")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/BabyCare/BabyFood/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/BabyCare/BabyFood/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/BabyCare/BabyFood/price";
        }
        else if(message.equals("72")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/BabyCare/BabySkinHair/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/BabyCare/BabySkinHair/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/BabyCare/BabySkinHair/price";
        }
        else if(message.equals("73")){
            link_image="https://a-to-z-basket.firebaseio.com/Products/BabyCare/DiapersWipes/url";
            link_name="https://a-to-z-basket.firebaseio.com/Products/BabyCare/DiapersWipes/name";
            link_cost="https://a-to-z-basket.firebaseio.com/Products/BabyCare/DiapersWipes/price";
        }


        for (int i=0;i<400;i++)
        {
            item_quantity[i]=0;
            item_name[i]="";
            item_cost[i]="";
        }


        progressDialog.setMessage("Products are loading...Please wait!");
        progressDialog.setCancelable(false);
        progressDialog.show();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CheckOut.class));
            }
        });

        small_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CheckOut.class));
            }
        });


        DatabaseReference databaseReference1= FirebaseDatabase.getInstance().
                getReferenceFromUrl(link_image);
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                s_url=(String) dataSnapshot.getValue();
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                //textView.setText(s);

                //progressDialog.dismiss();

                SharedPreferences.Editor editor=sharedpreferences_data.edit();
                editor.putString(data_url,s_url);
                editor.commit();

                flag1=1;

                int p=0;

                for(int i=0;i<1000;i++)
                {
                    url[i]="";
                }
                for(int i=1;i<=s_url.length()-22;i=i+22)
                {

                    for(int j=i;j<i+21;j++)
                    {
                      char c=s_url.charAt(j);
                       url[p]=url[p]+c;

                    }
                    p++;
                }

                length=p;

               /* for(int i=0;i<4;i++)
                {
                    Toast.makeText(getApplicationContext(),url[i],Toast.LENGTH_LONG).show();
                }*/



                //initViews();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //textView.setText("Some error occured please try again!");
            }
        });

        progressDialog.setMessage("Products are loading...Please wait!");
        progressDialog.setCancelable(false);
        progressDialog.show();

        DatabaseReference databaseReference2= FirebaseDatabase.getInstance().
                getReferenceFromUrl(link_name);
        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                s_name=(String) dataSnapshot.getValue();
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                //textView.setText(s);

                //progressDialog.dismiss();

                SharedPreferences.Editor editor=sharedpreferences_data.edit();
                editor.putString(data_name,s_name);
                editor.commit();
                flag2=0;
                int p=0;




                for(int i=0;i<1000;i++)
                {
                    name[i]="";
                }
                for(int i=1;i<=s_name.length()-51;i=i+51)
                {

                    for(int j=i;j<i+50;j++)
                    {
                        char c=s_name.charAt(j);
                        name[p]=name[p]+c;

                    }
                    p++;
                }

               length=p;
               /* for(int i=0;i<4;i++)
                {
                    Toast.makeText(getApplicationContext(),url[i],Toast.LENGTH_LONG).show();
                }*/




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //textView.setText("Some error occured please try again!");
            }
        });

        progressDialog.setMessage("Products are loading...Please wait!");
        progressDialog.setCancelable(false);
        progressDialog.show();

        DatabaseReference databaseReference3= FirebaseDatabase.getInstance().
                getReferenceFromUrl(link_cost);
        databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                s_price=(String) dataSnapshot.getValue();
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                //textView.setText(s);

                SharedPreferences.Editor editor=sharedpreferences_data.edit();
                editor.putString(data_cost,s_price);
                editor.commit();

                progressDialog.dismiss();
                int p=0;




                for(int i=0;i<1000;i++)
                {
                    cost[i]="";
                }
                for(int i=1;i<=s_price.length()-5;i=i+5)
                {

                    for(int j=i;j<i+4;j++)
                    {
                        char c=s_price.charAt(j);
                        cost[p]=cost[p]+c;

                    }
                    p++;
                }

                length=p;
                //Toast.makeText(getApplicationContext(),cost[0],Toast.LENGTH_SHORT).show();

                if(flag1==1&&flag2==0)
                initViews();
                else
                {
                    finish();
                    //startActivity(new Intent(getApplicationContext(),ImageLoad.class));
                    Intent i = new Intent(getApplicationContext(), ImageLoad.class);
                    i.putExtra("key", message);
                    startActivity(i);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                //textView.setText("Some error occured please try again!");
            }
        });

        //initViews();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_search) {
            //Toast.makeText(getApplicationContext(),"abcd",Toast.LENGTH_SHORT).show();

            finish();
            startActivity(new Intent(getApplicationContext(), SubSearch.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void initViews(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<AndroidVersion> androidVersions = prepareData();
        DataAdapter adapter = new DataAdapter(getApplicationContext(),androidVersions,sharedpreferences_order_details,item_name,item_quantity,item_cost,old_list,
                old_cost);
        recyclerView.setAdapter(adapter);

    }
    private ArrayList<AndroidVersion> prepareData(){

        ArrayList<AndroidVersion> android_version = new ArrayList<>();
        for(int i=0;i<length;i++){
            AndroidVersion androidVersion = new AndroidVersion();
            androidVersion.setAndroid_version_name(name[i]);
            androidVersion.setAndroid_image_url(url[i]);
            androidVersion.setAndroid_product_name(cost[i]);
            android_version.add(androidVersion);
        }
        return android_version;
    }

    @Override
    public void onBackPressed()
    {
        finish();

        char c=message.charAt(0);
        if(c=='0')
        startActivity(new Intent(getApplicationContext(), GroceryDummy.class));
        if(c=='1')
            startActivity(new Intent(getApplicationContext(), Dummy.class));
        if(c=='2')
            startActivity(new Intent(getApplicationContext(), HouseDummy.class));
        if(c=='3')
            startActivity(new Intent(getApplicationContext(),FoodDummy.class));
        if(c=='4')
            startActivity(new Intent(getApplicationContext(), EggsDummy.class));
        if(c=='5')
            startActivity(new Intent(getApplicationContext(), BevaragesDummy.class));
        if(c=='6')
            startActivity(new Intent(getApplicationContext(), PersonalDummy.class));
        if(c=='7')
            startActivity(new Intent(getApplicationContext(), BabyDummy.class));

    }
}

