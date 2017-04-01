package com.user.atozbasket.Personalcare;

/**
 * Created by Satish on 2/20/2017.
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


public class PersonalCustomGridAdapter extends BaseAdapter {

    private Context context;
    private final String[] gridValues;

    //Constructor to initialize values
    public PersonalCustomGridAdapter(Context context, String[] gridValues) {
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

            imageView.setImageResource(R.drawable.deodarant_icon);
            //relativeLayout.setBackgroundColor(Color.parseColor("#008D1A"));
            desc.setText("\nPerfumes, Deodorants from all brands.");


        } else if (position==1) {

            imageView.setImageResource(R.drawable.feminine_icon);
            //relativeLayout.setBackgroundColor(Color.parseColor("#32318F"));

            desc.setText("\nNapkins from all brands.");

        } else if (position==2) {

            imageView.setImageResource(R.drawable.hair_icons);
            //relativeLayout.setBackgroundColor(Color.parseColor("#E44C00"));

            desc.setText("\nHair oils, gels, shampoos, conditioners etc.");


        }
        else if (position==3) {

            imageView.setImageResource(R.drawable.oralcare_icon);
            //relativeLayout.setBackgroundColor(Color.parseColor("#C7181A"));

            desc.setText("\nTooth brushes, pastes, mouthwash and many more!");


        }
        else if (position==4) {

            imageView.setImageResource(R.drawable.wellness_icon);
            //relativeLayout.setBackgroundColor(Color.parseColor("#F8B501"));

            desc.setText("\nPain relievers, gels, sprays etc");


        }
        else if (position==5) {

            imageView.setImageResource(R.drawable.shaving_icons);
            //relativeLayout.setBackgroundColor(Color.parseColor("#2095B6"));

            desc.setText("\nRazors, creams, shaving gels and many more");


        }
        else if (position==6) {

            imageView.setImageResource(R.drawable.skincare_icons);
            //relativeLayout.setBackgroundColor(Color.parseColor("#C4247E"));

            desc.setText("\nTalcs, Face creams, foot creams and many other products.");


        }
        else if (position==7) {

            imageView.setImageResource(R.drawable.soaps_icons);
            //relativeLayout.setBackgroundColor(Color.parseColor("#002359"));

            desc.setText("\nSoaps, Body wash, Shower gels from all brands");


        }

        return gridView;
    }
}
