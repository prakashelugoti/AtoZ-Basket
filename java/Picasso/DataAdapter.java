package com.user.atozbasket.Picasso;

/**
 * Created by Satish on 2/11/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.user.atozbasket.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android_versions;
    private Context context;
    int[] item_quantity=new int[400];
    String[] item_name=new String[400];
    String[] item_cost=new String[400];

String old_list="",old_cost="0";
    SharedPreferences sharedpreferences_order_details;
    public static final String mypreference_order_details = "mypref";
    public static final String order_list = "order_list";
    public static final String order_cost="order_cost";
    String data="";

    int total_cost=0;
    int l=0;

    public DataAdapter(Context context,ArrayList<AndroidVersion> android_versions,SharedPreferences sharedpreferences_order_details, String[] item_name, int[] item_quantity,
                       String[] item_cost, String old_list, String old_cost) {
        this.context = context;
        this.android_versions = android_versions;
        this.sharedpreferences_order_details=sharedpreferences_order_details;
        this.item_quantity=item_quantity;
        this.item_name=item_name;
        this.item_cost=item_cost;
        this.old_list=old_list;
        this.old_cost=old_cost;


    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {



        viewHolder.tv_android.setText(android_versions.get(i).getAndroid_version_name());
        viewHolder.product_price.setText("Rs."+android_versions.get(i).getAndroid_product_name()+"/-");
        Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).resize(128, 128).into(viewHolder.img_android);



        //final TextView relativeLayout=viewHolder.qunatity;
        final TextView tv=viewHolder.count;

        String val= String.valueOf(item_quantity[i]);
        tv.setText(val);

        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Adding product
                //Toast.makeText(context,"Product added",Toast.LENGTH_SHORT).show();
                item_quantity[i]++;

                String val= String.valueOf(item_quantity[i]);
                tv.setText(val);

                item_name[i]=android_versions.get(i).getAndroid_version_name();
                item_cost[i]=android_versions.get(i).getAndroid_product_name();


                String s="";
                data="";
                int cs=0;
                total_cost=0;

                //String ab= String.valueOf(i);

                //Toast.makeText(context,item_cost[0],Toast.LENGTH_SHORT).show();

                int x= item_cost[0].length();
                String z= String.valueOf(x);

                //Toast.makeText(context,z,Toast.LENGTH_SHORT).show();


                if(sharedpreferences_order_details.getString(order_list,"").equals(""))
                {
                   //Toast.makeText(context,"Empty",Toast.LENGTH_SHORT).show();


                    for(int d=0;d<400;d++)
                    {
                        if(item_quantity[d]!=0)
                        {
                            //String cost_string= tv.getText().toString();
                            String vals= String.valueOf(item_quantity[d]);
                            data=data+"*"+item_name[d]+"#"+vals+"#";
                            data=data+item_cost[d]+"*\n";


                             String price="";
                            for(int k=0;k<item_cost[d].length();k++)
                            {
                                char c=item_cost[d].charAt(k);

                                if(c=='0'||c=='1'||c=='2'||c=='3'||c=='4'||c=='5'||c=='6'||c=='7'||c=='8'||c=='9')
                                price=price+c;
                            }
                             total_cost= total_cost+Integer.parseInt(price)*item_quantity[d];





                            //cs= cs+x;




                            //String vals= item_name[i];
                            //Toast.makeText(context,vals,Toast.LENGTH_SHORT).show();
                        }
                    }

                    data=data+old_list;

                    int f= Integer.parseInt(old_cost);
                    total_cost=total_cost+f;



                    //data=data+"\n--------------------------\nTotal Amount:        Rs."+String.valueOf(total_cost)+"/-";


                    //viewHolder.small_button.setVisibility(View.VISIBLE);
                       //viewHolder.small_button.setText(cnt);

                    SharedPreferences.Editor editor=sharedpreferences_order_details.edit();
                    editor.remove(order_cost);
                    editor.remove(order_list);
                    editor.commit();

                    SharedPreferences.Editor editor2=sharedpreferences_order_details.edit();
                    editor2.putString(order_list,data);
                    editor2.putString(order_cost,String.valueOf(total_cost));
                    editor2.commit();
                }
                else
                {

                    for(int d=0;d<400;d++)
                    {
                        if(item_quantity[d]!=0)
                        {
                            String vals= String.valueOf(item_quantity[d]);
                            data=data+"*"+item_name[d]+"#"+vals+"#";
                            data=data+item_cost[d]+"*\n";

                            String price="";
                            for(int k=0;k<item_cost[d].length();k++)
                            {
                                char c=item_cost[d].charAt(k);

                                if(c=='0'||c=='1'||c=='2'||c=='3'||c=='4'||c=='5'||c=='6'||c=='7'||c=='8'||c=='9')
                                    price=price+c;
                            }
                             total_cost= total_cost+Integer.parseInt(price)*item_quantity[d];

                        }
                    }
                //SharedPreferences.Editor editor=sharedpreferences_order_details.edit();
                    String previous_cost=sharedpreferences_order_details.getString(order_cost,"");

                    //previous_data=previous_data+sharedpreferences_order_details.getString(order_list,"");


                    data=data+old_list;

                    int f= Integer.parseInt(old_cost);
                    total_cost=total_cost+f;



                    SharedPreferences.Editor editor2=sharedpreferences_order_details.edit();
                    editor2.remove(order_cost);
                    editor2.remove(order_list);
                    editor2.commit();

                    SharedPreferences.Editor editor=sharedpreferences_order_details.edit();
                    editor.putString(order_list,data);
                    editor.putString(order_cost,String.valueOf(total_cost));
                    editor.commit();

                    //Toast.makeText(context,sharedpreferences_order_details.getString(order_list,"").toString(),Toast.LENGTH_SHORT).show();
                }


            }
        });

        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //removing product
                //Toast.makeText(context,"Minus button",Toast.LENGTH_SHORT).show();
                //Toast.makeText(context,"Product removed",Toast.LENGTH_SHORT).show();
                if(item_quantity[i]!=0)
                item_quantity[i]--;

                String val= String.valueOf(item_quantity[i]);
                tv.setText(val);
                item_name[i]=android_versions.get(i).getAndroid_version_name();


                String s="";
                data="";
                int cs=0;
                total_cost=0;

                //String ab= String.valueOf(i);

                //Toast.makeText(context,ab,Toast.LENGTH_SHORT).show();

                if(sharedpreferences_order_details.getString(order_list,"").equals(""))
                {
                    //Toast.makeText(context,"Empty",Toast.LENGTH_SHORT).show();


                    for(int d=0;d<400;d++)
                    {
                        if(item_quantity[d]!=0)
                        {
                            //String cost_string= tv.getText().toString();
                            String vals= String.valueOf(item_quantity[d]);

                            data=data+"*"+item_name[d]+"#"+vals+"#";
                            data=data+item_cost[d]+"*\n";

                            String price="";
                            for(int k=0;k<item_cost[d].length();k++)
                            {
                                char c=item_cost[d].charAt(k);

                                if(c=='0'||c=='1'||c=='2'||c=='3'||c=='4'||c=='5'||c=='6'||c=='7'||c=='8'||c=='9')
                                    price=price+c;
                            }
                            total_cost= total_cost+Integer.parseInt(price)*item_quantity[d];

                            //int x= Integer.parseInt(item_cost[d]);
                            //cs= cs+x;
                            //String vals= item_name[i];
                            //Toast.makeText(context,vals,Toast.LENGTH_SHORT).show();
                        }
                    }

                    SharedPreferences.Editor editor=sharedpreferences_order_details.edit();
                    editor.remove(order_cost);
                    editor.remove(order_list);
                    editor.commit();

                    data=data+old_list;

                    int f= Integer.parseInt(old_cost);
                    total_cost=total_cost+f;

                    data=data;

                    SharedPreferences.Editor editor2=sharedpreferences_order_details.edit();
                    editor2.putString(order_list,data);
                    editor2.putString(order_cost,String.valueOf(total_cost));
                    editor2.commit();
                }
                else
                {

                    for(int d=0;d<400;d++)
                    {
                        if(item_quantity[d]!=0)
                        {
                            String vals= String.valueOf(item_quantity[d]);

                            data=data+"*"+item_name[d]+"#"+vals+"#";
                            data=data+item_cost[d]+"*\n";

                            String price="";
                            for(int k=0;k<item_cost[d].length();k++)
                            {
                                char c=item_cost[d].charAt(k);

                                if(c=='0'||c=='1'||c=='2'||c=='3'||c=='4'||c=='5'||c=='6'||c=='7'||c=='8'||c=='9')
                                    price=price+c;
                            }
                            total_cost= total_cost+Integer.parseInt(price)*item_quantity[d];

                            

                            //int x= Integer.parseInt(item_cost[d]);
                            //cs= cs+x;
                        }
                    }
                    //SharedPreferences.Editor editor=sharedpreferences_order_details.edit();
                    String previous_data=sharedpreferences_order_details.getString(order_list,"");

                    //previous_data=previous_data+sharedpreferences_order_details.getString(order_list,"");
                    data=data+old_list;

                    int f= Integer.parseInt(old_cost);
                    total_cost=total_cost+f;

                    SharedPreferences.Editor editor2=sharedpreferences_order_details.edit();

                    editor2.remove(order_cost);
                    editor2.remove(order_list);
                    editor2.commit();

                    data=data;

                    SharedPreferences.Editor editor=sharedpreferences_order_details.edit();
                    editor.putString(order_list,data);
                    editor.putString(order_cost,String.valueOf(total_cost));
                    editor.commit();

                    //Toast.makeText(context,sharedpreferences_order_details.getString(order_list,"").toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return android_versions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_android,product_price,count;
        ImageView img_android;
        ImageView plus,minus;
        Button small_button;
        public ViewHolder(View view) {
            super(view);

            tv_android = (TextView)view.findViewById(R.id.tv_android);
            img_android = (ImageView)view.findViewById(R.id.img_android);
            product_price=(TextView)view.findViewById(R.id.price);
            plus=(ImageView) view.findViewById(R.id.plus_button);
            minus=(ImageView) view.findViewById(R.id.minus_button);
            count=(TextView)view.findViewById(R.id.count);
            small_button=(Button)view.findViewById(R.id.small_button);




        }
    }
}
