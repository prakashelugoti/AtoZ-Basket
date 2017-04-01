package com.user.atozbasket.Check;

/**
 * Created by Satish on 3/3/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.user.atozbasket.PlaceOrder;
import com.user.atozbasket.R;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] d1;
    private final String[] d2;
    private final String[] d3;
    int k;

    SharedPreferences sharedpreferences_order_details;
    public static final String mypreference_order_details = "mypref";
    public static final String order_list = "order_list";
    public static final String order_cost="order_cost";
    public CustomList(Activity context,
                      String[] d1, String[] d2, String[] d3, int k, SharedPreferences sharedpreferences_order_details) {
        super(context, R.layout.list_single, d1);
        this.context = context;
        this.d1 = d1;
        this.d2 = d2;
        this.d3=d3;
        this.k=k;
        this.sharedpreferences_order_details=sharedpreferences_order_details;

    }
    @Override
    public View getView(final int position, final View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        TextView second_text=(TextView)rowView.findViewById(R.id.second_text);
        TextView third_text=(TextView)rowView.findViewById(R.id.third_text);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(d1[position]);
        second_text.setText("Quantity: "+d2[position]);
        third_text.setText("Price(Per Item) Rs."+d3[position]+"/-");

        imageView.setImageResource(R.drawable.delete);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Item removed!",Toast.LENGTH_SHORT).show();

                String fin="";
                int cost=0,quantity=0;
                String qt="",cst="";

                for(int j=0;j<k;j++)
                {
                    if(j==position) {
                         qt=d2[j];
                         cst=d3[j];
                        continue;
                    }
                    fin=fin+"*"+d1[j]+"#"+d2[j]+"#"+d3[j]+"*\n";
                }

                /*data=data+"*"+item_name[d]+"#"+vals+"#";
                data=data+item_cost[d]+"*\n";*/

               quantity= Integer.parseInt(qt);

                String price="";

                for(int j=0;j<4;j++)
                {
                    char c=cst.charAt(j);
                    if(c=='0'||c=='1'||c=='2'||c=='3'||c=='4'||c=='5'||c=='6'||c=='7'||c=='8'||c=='9')
                        price=price+c;

                }

                  cost= Integer.parseInt(price);

                 cost=cost*quantity;

                 int cos= Integer.parseInt(sharedpreferences_order_details.getString(order_cost,""));

                 cos=cos-cost;

                String fin_rate= String.valueOf(cos);

                //Toast.makeText(context,fin,Toast.LENGTH_LONG).show();


                SharedPreferences.Editor editor=sharedpreferences_order_details.edit();
                editor.putString(order_list,fin);
                editor.putString(order_cost,fin_rate);
                editor.commit();

                context.startActivity(new Intent(context, PlaceOrder.class));


            }
        });
        return rowView;
    }
}
