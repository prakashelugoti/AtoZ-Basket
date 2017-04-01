package com.user.atozbasket.Grocery;

/**
 * Created by Satish on 2/18/2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.user.atozbasket.R;


public class GroCustomGridAdapter extends BaseAdapter {

    private Context context;
    private final String[] gridValues;

    //Constructor to initialize values
    public GroCustomGridAdapter(Context context, String[] gridValues) {
        this.context = context;
        this.gridValues = gridValues;
    }

    @Override
    public int getCount() {

        // Number of times getView method call depends upon gridValues.length
        return gridValues.length;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }


    // Number of times getView method call depends upon gridValues.length

    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView;
        //LayoutInflator to call external grid_item.xml file

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        if (convertView == null) {


            gridView = new View(context);
            // get layout from grid_item.xml
            gridView = inflater.inflate(R.layout.grid_item, null);



        } else {
            gridView = (View) convertView;
        }


        // set value into textview

        final TextView textView = (TextView) gridView
                .findViewById(R.id.grid_item_label);
        textView.setText(gridValues[position]);



        TextView desc=(TextView) gridView.findViewById(R.id.description);

        // set image based on selected text

        ImageView imageView = (ImageView) gridView
                .findViewById(R.id.grid_item_image);

        RelativeLayout relativeLayout=(RelativeLayout) gridView.findViewById(R.id.relative_layout);

        //static final String[] GRID_DATA = new String[] { "Dal & Pulses" ,
        //"Dry Fruits", "Edible Oils & Ghee" ,"Rice & Flour","Salt & Sugar","Spices"};
        String mobile = gridValues[position];

        if (position==0) {

            imageView.setImageResource(R.drawable.dal_pulses);
            //relativeLayout.setBackgroundColor(Color.parseColor("#008D1A"));
            desc.setText("\nHealthy pulses for you and your family.");


        } else if (position==1) {

            imageView.setImageResource(R.drawable.dry_fruits);
            //relativeLayout.setBackgroundColor(Color.parseColor("#32318F"));

            desc.setText("\nAlmonds, Cashews, Dates and many more!");

        } else if (position==2) {

            imageView.setImageResource(R.drawable.oil);
            //relativeLayout.setBackgroundColor(Color.parseColor("#E44C00"));

            desc.setText("\nOil from Freedom, Sundrop, Saffola and many other brands.");


        }
        else if (position==3) {

            imageView.setImageResource(R.drawable.spices);
            //relativeLayout.setBackgroundColor(Color.parseColor("#C7181A"));

            desc.setText("\nMasalas, Chilli powders, Turmeric etc.");


        }
        else if (position==4) {

            imageView.setImageResource(R.drawable.rice_flour);
            //relativeLayout.setBackgroundColor(Color.parseColor("#F8B501"));

            desc.setText("\nAAta and rice from your favourite brands!");


        }
        else if (position==5) {

            imageView.setImageResource(R.drawable.salt_sugar);
            //relativeLayout.setBackgroundColor(Color.parseColor("#2095B6"));

            desc.setText("\nSalt, Sugars etc.");


        }

        return gridView;
    }
}

