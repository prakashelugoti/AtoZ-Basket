package com.user.atozbasket.CustomerDetails;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.user.atozbasket.Global;
import com.user.atozbasket.MainActivity;
import com.user.atozbasket.R;

public class MyAddress extends AppCompatActivity {

    SharedPreferences sharedpreferences_full_address;
    public static final String mypreference_full_address = "mypreference_full_address";
    public static final String full_address = "full_address";


    SharedPreferences sharedpreferences_edit_address;
    public static final String mypreference_edit_address = "mypreference_full_address";
    public static final String edit_address = "edit_address";
    public static final String area = "area";

    SharedPreferences sharedpreferences_number;
    public static final String mypreference_number = "savedata";
    public static final String my_number = "mynumber";
    public static final String my_name = "myname";

    TextView complete_address;

    ImageView edit_address_button;

    Global global;

    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        sharedpreferences_full_address = getSharedPreferences(mypreference_full_address,
                Context.MODE_PRIVATE);

        sharedpreferences_edit_address = getSharedPreferences(mypreference_edit_address,
                Context.MODE_PRIVATE);

        sharedpreferences_number = getSharedPreferences(mypreference_number,
                Context.MODE_PRIVATE);

        String u_name="Name: "+sharedpreferences_number.getString(my_name,"")+"\n";
        String u_number="Phone Number: "+sharedpreferences_number.getString(my_number,"")+"\n";

        edit_address_button=(ImageView)findViewById(R.id.edit_address);
        global=new Global();


        complete_address=(TextView)findViewById(R.id.customer_details);
        //edit_button=(Button)findViewById(R.id.edit_button);

        //complete_address.setText(sharedpreferences_full_address.getString(full_address,""));
      String add=u_name+u_number;
              add=add+"Address: "+sharedpreferences_edit_address.getString(edit_address,"");
        add=add+"\nArea: "+sharedpreferences_edit_address.getString(area,"");

      complete_address.setText(add);


        edit_address_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.alert_dialog);
                dialog.setTitle("Change Address!");
                dialog.show();

                final EditText edit_text_address=(EditText) dialog.findViewById(R.id.address);

                //Creating the instance of ArrayAdapter containing list of language names
                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (getApplicationContext(),R.layout.list_autocomplete,global.products);
                final AutoCompleteTextView autocomplete=(AutoCompleteTextView)dialog.findViewById(R.id.area);

                Resources res = getResources();
                int color = res.getColor(android.R.color.black);
                autocomplete.setText("");
                autocomplete.setThreshold(1);//will start working from first character
                autocomplete.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

                Button save_address=(Button)dialog.findViewById(R.id.save_address);


                edit_text_address.setText(sharedpreferences_edit_address.getString(edit_address,""));

                save_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(autocomplete.length()==0)
                        {
                            Toast.makeText(getApplicationContext(),"Please Enter Your Area",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String check=autocomplete.getText().toString();
                        int flag=0;
                        for(int i=0;i<global.products.length;i++)
                        {
                            if(check.equals(global.products[i]))
                            {
                                flag=1;
                                break;
                            }
                        }
                        if(flag==0)
                        {
                            Toast.makeText(getApplicationContext(),check,Toast.LENGTH_SHORT).show();
                            return;
                        }

                        SharedPreferences.Editor editor = sharedpreferences_edit_address.edit();

                        editor.putString(edit_address,edit_text_address.getText().toString());
                        editor.putString(area,autocomplete.getText().toString());
                        editor.commit();

                        Toast.makeText(getApplicationContext(),"Address changed successfully!",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        finish();
                        startActivity(new Intent(getApplicationContext(),MyAddress.class));
                    }
                });

            }
        });


    }

    @Override
    public void onBackPressed()
    {
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
