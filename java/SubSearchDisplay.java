package com.user.atozbasket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.user.atozbasket.Picasso.ImageLoad;

public class SubSearchDisplay extends AppCompatActivity {
String message;

    SharedPreferences sharedpreferences_data;
    public static final String mypreference_data = "myprefdata";
    public static final String data_url = "data_url";
    public static final String data_name = "data_name";
    public static final String data_cost= "data_cost";

    int p=0;
    int cnt=0;

    int p2=0,p3=0;

    SharedPreferences sharedpreferences_activity;
    public static final String mypreference_activity = "myprefdata";
    public static final String activity_name = "activity_name";


    ImageView imageView;
    TextView text_cost,text_name,text_count;

    ImageView plus_button,minus_button;
    Button    checkout_button;

    String final_url,final_name,final_cost;
    SharedPreferences sharedpreferences_order_details;
    public static final String mypreference_order_details = "mypref";
    public static final String order_list = "order_list";
    public static final String order_cost="order_cost";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_search_display);

        Bundle b = getIntent().getExtras();
        message = b.getString("key", "");

        sharedpreferences_data = getSharedPreferences(mypreference_data,
                Context.MODE_PRIVATE);

        sharedpreferences_order_details = getSharedPreferences(mypreference_order_details,
                Context.MODE_PRIVATE);

        sharedpreferences_activity = getSharedPreferences(mypreference_activity,
                Context.MODE_PRIVATE);
        //Toast.makeText(getApplicationContext(),sharedpreferences_order_details.getString(order_list,""),Toast.LENGTH_SHORT).show();

        //textView=(TextView)findViewById(R.id.display_product);
        imageView=(ImageView)findViewById(R.id.img_android);
        text_cost=(TextView)findViewById(R.id.price);
        text_name=(TextView)findViewById(R.id.tv_android);
        text_count=(TextView)findViewById(R.id.count);

        plus_button=(ImageView) findViewById(R.id.plus_button);
        minus_button=(ImageView) findViewById(R.id.minus_button);
        checkout_button=(Button)findViewById(R.id.check_out_button);




        String[] products=new String[1000];
        String[] url=new String[1000];
        String[] cost=new String[1000];

        String s_name=sharedpreferences_data.getString(data_name,"");
        String s_cost,s_url;

        s_cost=sharedpreferences_data.getString(data_cost,"");
        s_url=sharedpreferences_data.getString(data_url,"");

        for(int i=0;i<s_name.length()/51;i++)
        {
            products[i]="";
            url[i]="";
            cost[i]="";
        }
        for(int i=1;i<=s_name.length()-51;i=i+51)
        {

            for(int j=i;j<i+50;j++)
            {
                char c=s_name.charAt(j);
                products[p]=products[p]+c;

            }
            p++;
        }

        for(int i=1;i<=s_url.length()-22;i=i+22)
        {

            for(int j=i;j<i+21;j++)
            {
                char c=s_url.charAt(j);
                url[p2]=url[p2]+c;

            }
            p2++;
        }

        for(int i=1;i<=s_cost.length()-5;i=i+5)
        {

            for(int j=i;j<i+4;j++)
            {
                char c=s_cost.charAt(j);
                cost[p3]=cost[p3]+c;

            }
            p3++;
        }



        for(int i=0;i<p;i++)
        {
            if(products[i].equals(message)) {
                cnt = i;
                break;
            }
        }

 String mes= String.valueOf(cnt);



        final_name=products[cnt];
        final_cost=cost[cnt];
        final_url=url[cnt];



        text_cost.setText("Rs."+final_cost+"/-");
        text_name.setText(final_name);
        Picasso.with(getApplicationContext()).load(final_url).resize(128, 128).into(imageView);




        //Toast.makeText(getApplicationContext(),mes,Toast.LENGTH_SHORT).show();

        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String c=text_count.getText().toString();

                int f= Integer.parseInt(c);

                f++;
                c= String.valueOf(f);

                text_count.setText(c);
            }
        });

        minus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String c=text_count.getText().toString();

                int f= Integer.parseInt(c);

                if(f!=0)
                f--;

                c= String.valueOf(f);

                text_count.setText(c);
            }
        });

        checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!text_count.getText().toString().equals("0"))
                {

                    String list=sharedpreferences_order_details.getString(order_list,"");
                    String cost=sharedpreferences_order_details.getString(order_cost,"");

                    if(cost.equals(""))
                        cost="0";

                    String txt_cnt=text_count.getText().toString();

                    int c1= Integer.parseInt(txt_cnt);

                    //String txt_cost=final_cost;


                    //int c1= Integer.parseInt(text_count.getText().toString());

                    String edit_cst="";

                    for(int i=0;i<4;i++)
                    {
                        char c=final_cost.charAt(i);

                        if(c=='0'||c=='1'||c=='2'||c=='3'||c=='4'||c=='5'||c=='6'||c=='7'||c=='8'||c=='9')
                            edit_cst=edit_cst+c;
                    }

                    int c2=Integer.parseInt(edit_cst);

                    //String cx= String.valueOf(c2);

                    //Toast.makeText(getApplicationContext(),txt_cnt,Toast.LENGTH_SHORT).show();

                    c1=c1*c2;

                    int cst= Integer.parseInt(cost);

                    cst=cst+c1;

                    //cst=cst+c1;

                    String str=text_name.getText().toString();

                    //str=str+"Quantity: "+text_count.getText().toString()+"\n";

                   // str=str+list;

                    String fin="";

                    fin="*"+str+"#"+text_count.getText().toString()+"#"+String.valueOf(final_cost)+"*\n";



                    SharedPreferences.Editor editor=sharedpreferences_order_details.edit();
                    editor.putString(order_cost, String.valueOf(cst));
                    editor.putString(order_list,fin);
                    editor.commit();

                    text_count.setText("0");

                    finish();
                    startActivity(new Intent(getApplicationContext(),CheckOut.class));


                }
                else
                {
                    finish();
                    startActivity(new Intent(getApplicationContext(),CheckOut.class));
                }

            }
        });

    }

    @Override
    public void onBackPressed()
    {
        if(!text_count.getText().toString().equals("0"))
        {

            String list=sharedpreferences_order_details.getString(order_list,"");
            String cost=sharedpreferences_order_details.getString(order_cost,"");

            if(cost.equals(""))
                cost="0";

            String txt_cnt=text_count.getText().toString();

            int c1= Integer.parseInt(txt_cnt);

            //String txt_cost=final_cost;


            //int c1= Integer.parseInt(text_count.getText().toString());

            String edit_cst="";

            for(int i=0;i<4;i++)
            {
                char c=final_cost.charAt(i);

                if(c=='0'||c=='1'||c=='2'||c=='3'||c=='4'||c=='5'||c=='6'||c=='7'||c=='8'||c=='9')
                    edit_cst=edit_cst+c;
            }

            int c2=Integer.parseInt(edit_cst);

            //String cx= String.valueOf(c2);

            //Toast.makeText(getApplicationContext(),txt_cnt,Toast.LENGTH_SHORT).show();

            c1=c1*c2;

            int cst= Integer.parseInt(cost);

            cst=cst+c1;

            //cst=cst+c1;

            String str=text_name.getText().toString();

            str=str+"Quantity: "+text_count.getText().toString()+"\n";

            str=str+list;

            SharedPreferences.Editor editor=sharedpreferences_order_details.edit();
            editor.putString(order_cost, String.valueOf(cst));
            editor.putString(order_list,str);
            editor.commit();

            text_count.setText("0");

            String mes=sharedpreferences_activity.getString(activity_name,"");

            Intent i = new Intent(getApplicationContext(), ImageLoad.class);
            i.putExtra("key", mes);
            startActivity(i);


        }
        else
        {
            String mes=sharedpreferences_activity.getString(activity_name,"");

            Intent i = new Intent(getApplicationContext(), ImageLoad.class);
            i.putExtra("key", mes);
            startActivity(i);
        }
    }

}
