package com.user.atozbasket;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.user.atozbasket.Babycare.BabyDummy;
import com.user.atozbasket.Beverages.BevaragesDummy;
import com.user.atozbasket.Eggs.EggsDummy;
import com.user.atozbasket.Food.FoodDummy;
import com.user.atozbasket.Grocery.GroceryDummy;
import com.user.atozbasket.HouseHolds.HouseDummy;
import com.user.atozbasket.Personalcare.PersonalDummy;
import com.user.atozbasket.Vegetables.Dummy;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView countryName;
    public ImageView countryPhoto;
    Context context;

    public RecyclerViewHolders(View itemView, Context context) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.context=context;
        countryName = (TextView)itemView.findViewById(R.id.country_name);
        countryPhoto = (ImageView)itemView.findViewById(R.id.country_photo);
    }

    @Override
    public void onClick(View view) {
        int x=getPosition();

        if(x==0)
            view.getContext().startActivity(new Intent(view.getContext(), GroceryDummy.class));
        if(x==1)
            view.getContext().startActivity(new Intent(view.getContext(), Dummy.class));
        if(x==2)
            view.getContext().startActivity(new Intent(view.getContext(), HouseDummy.class));
        if(x==3)
            view.getContext().startActivity(new Intent(view.getContext(), FoodDummy.class));
        if(x==4)
            view.getContext().startActivity(new Intent(view.getContext(), EggsDummy.class));
        if(x==5)
            view.getContext().startActivity(new Intent(view.getContext(), BevaragesDummy.class));
        if(x==6)
            view.getContext().startActivity(new Intent(view.getContext(), PersonalDummy.class));
        if(x==7)
            view.getContext().startActivity(new Intent(view.getContext(), BabyDummy.class));


    }
}