package com.user.atozbasket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.user.atozbasket.IntroSlider.WelcomeActivity;

public class EnterAddress extends AppCompatActivity {


    String message;
    TextView textView;
    EditText complete_address;
    Button button_save_address;

    Global global;

    SharedPreferences sharedpreferences_number;
    public static final String mypreference_number = "savedata";
    public static final String my_number = "mynumber";
    public static final String my_name = "myname";

    SharedPreferences sharedpreferences_full_address;
    public static final String mypreference_full_address = "mypreference_full_address";
    public static final String full_address = "full_address";


    SharedPreferences sharedpreferences_edit_address;
    public static final String mypreference_edit_address = "mypreference_full_address";
    public static final String edit_address = "edit_address";
    public static final String area = "area";


    String customer_address="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_address);

        Bundle b = getIntent().getExtras();
        message = b.getString("key", "");

        global=new Global();

        //textView=(TextView)findViewById(R.id.area);
        complete_address=(EditText)findViewById(R.id.address);
        textView=(TextView)findViewById(R.id.other_details);
        button_save_address=(Button)findViewById(R.id.save_address);

        sharedpreferences_number = getSharedPreferences(mypreference_number,
                Context.MODE_PRIVATE);

        sharedpreferences_full_address = getSharedPreferences(mypreference_full_address,
                Context.MODE_PRIVATE);

        sharedpreferences_edit_address=getSharedPreferences(mypreference_edit_address,
                Context.MODE_PRIVATE);

        String u_name=sharedpreferences_number.getString(my_name,"");
        String u_number=sharedpreferences_number.getString(my_number,"");

        customer_address="Name: "+u_name+"\n"+"Phone Number: "+u_number+"\n";

        textView.setText(customer_address+"Area: "+message);

        button_save_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(complete_address.getText().toString().length()<=10)
                {
                    Toast.makeText(getApplicationContext(),"Enter valid address",Toast.LENGTH_SHORT).show();
                    return;
                }
                String edit_complete_address="Address: "+complete_address.getText().toString()+"\nArea: "+message;


                edit_complete_address=customer_address+edit_complete_address;

                SharedPreferences.Editor editor = sharedpreferences_full_address.edit();

                editor.putString(full_address,edit_complete_address);
                editor.commit();


                SharedPreferences.Editor editor2 = sharedpreferences_edit_address.edit();
                editor2.putString(edit_address,complete_address.getText().toString());
                editor2.putString(area,message);
                editor2.commit();


                Toast.makeText(getApplicationContext(),"Address Saved Successfully!",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(),WelcomeActivity.class));
                //SharedPreferences.Editor editor=new E



            }
        });











    }
}
