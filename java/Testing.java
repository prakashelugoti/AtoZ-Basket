package com.user.atozbasket;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Testing extends AppCompatActivity {
//TextView textView;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference db;
    ProgressDialog progressDialog;
    String s="";
    String[] url=new String[1000];

    GridView grid;
    String[] web = {
            "Google",
            "Github",
            "Instagram",
            "Facebook",
            "Flickr",
            "Pinterest",
            "Quora",
            "Twitter",
            "Vimeo",
            "WordPress",
            "Youtube",
            "Stumbleupon",
            "SoundCloud",
            "Reddit",
            "Blogger"

    } ;
    int[] imageId = {
            R.drawable.edit,
            R.drawable.my_account_colour,
            R.drawable.my_account_colour,
            R.drawable.dry_fruits,
            R.drawable.ic_add_circle_black_24dp,
            R.drawable.my_account_colour,
            R.drawable.edit,
            R.drawable.my_account_colour,
            R.drawable.my_account_colour,
            R.drawable.dry_fruits,
            R.drawable.ic_add_circle_black_24dp,
            R.drawable.my_account_colour,
            R.drawable.about_us,
            R.drawable.my_wallet,
            R.drawable.choclate_icon

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        //textView=(TextView)findViewById(R.id.textView);

        CustomGrid adapter = new CustomGrid(Testing.this, web, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Testing.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });


    }
}
