package com.user.atozbasket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.user.atozbasket.Check.CustomList;

public class PlaceOrder extends AppCompatActivity {


    ListView list;
    String[] web = {
            "Google Plus\n.com\ncbnghh",
            "Twitter",
            "Windows",
            "Bing",
            "Itunes",
            "Wordpress",
            "Drupal"
    } ;
    Integer[] imageId = {
            R.drawable.delete,
            R.drawable.delete,
            R.drawable.delete,
            R.drawable.delete,
            R.drawable.delete,
            R.drawable.delete,
            R.drawable.delete

    };

    SharedPreferences sharedpreferences_order_details;
    public static final String mypreference_order_details = "mypref";
    public static final String order_list = "order_list";
    public static final String order_cost="order_cost";

    String[] data1=new String[1000];

    int start_count=0;

    Button button_check_out;

    int g=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        sharedpreferences_order_details = getSharedPreferences(mypreference_order_details,
                Context.MODE_PRIVATE);

        button_check_out=(Button)findViewById(R.id.check_out_button);

        if(sharedpreferences_order_details.getString(order_list,"").equals(""))
        {
            finish();
            startActivity(new Intent(getApplicationContext(),EmptyCart.class));
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

        String[] d1=new String[g];
        String[] d2=new String[g];
        String[] d3=new String[g];

        for(int i=0;i<g;i++)
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
            /*int f=i+1;
            ins=ins+f+") "+d1[i]+"\n";
            ins=ins+"   Qunatity: "+d2[i]+"\n";
            ins=ins+"   Price(Per Item): Rs."+d3[i]+"/-\n\n";*/

            //d2[i]="Quantity: "+d2[i];
            //d3[i]="Price(Per Item): Rs."+d3[i]+"/-";
        }



        CustomList adapter = new
                CustomList(PlaceOrder.this, d1, d2, d3,k, sharedpreferences_order_details);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               // Toast.makeText(PlaceOrder.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });


        button_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),CheckOut.class));
            }
        });


    }
    @Override
    public void onBackPressed()
    {
        finish();
        startActivity(new Intent(getApplicationContext(),CheckOut.class));
    }
}
