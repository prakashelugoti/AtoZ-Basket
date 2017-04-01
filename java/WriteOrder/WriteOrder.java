package com.user.atozbasket.WriteOrder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.user.atozbasket.CheckOut;
import com.user.atozbasket.MainActivity;
import com.user.atozbasket.R;

public class WriteOrder extends AppCompatActivity {

    EditText write_order;
    Button button_add_to_cart;

    SharedPreferences sharedpreferences_write_order;
    public static final String mypreference_write_order = "mypreference_write_order";
    public static final String full_order_list = "full_order_list";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_order);

        write_order=(EditText)findViewById(R.id.order_list);
        button_add_to_cart=(Button)findViewById(R.id.add_to_cart);

        sharedpreferences_write_order=getSharedPreferences(mypreference_write_order,
                Context.MODE_PRIVATE);

        String ol=sharedpreferences_write_order.getString(full_order_list,"");

        if(!ol.equals(""))
            write_order.setText(ol);

        button_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(write_order.getText().toString().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Write something before you proceed!",Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences.Editor editor=sharedpreferences_write_order.edit();
                editor.putString(full_order_list,write_order.getText().toString());
                editor.commit();

                finish();
                startActivity(new Intent(getApplicationContext(), CheckOut.class));


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
