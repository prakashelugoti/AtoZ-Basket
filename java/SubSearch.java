package com.user.atozbasket;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SubSearch extends Activity {

    // List view
    private ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;

    SharedPreferences sharedpreferences_data;
    public static final String mypreference_data = "myprefdata";
    public static final String data_url = "data_url";
    public static final String data_name = "data_name";
    public static final String data_cost= "data_cost";
    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;
    int p=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_search);

        // Listview Data

        sharedpreferences_data = getSharedPreferences(mypreference_data,
                Context.MODE_PRIVATE);

        String[] products=new String[1000];

        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        String s_name=sharedpreferences_data.getString(data_name,"");

        for(int i=0;i<s_name.length()/51;i++)
        {
            products[i]="";
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

        String[] products_updated=new String[p];

        for(int i=0;i<p;i++)
        {
            products_updated[i]=products[i];
        }

        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.sub_search_list, R.id.product_name, products_updated);
        lv.setAdapter(adapter);

        /**
         * Enabling Search Filter
         * */

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = (String) parent.getAdapter().getItem(position);

                finish();
                Intent i = new Intent(SubSearch.this, SubSearchDisplay.class);
                i.putExtra("key", item);
                startActivity(i);


            }
        });
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                SubSearch.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

}
