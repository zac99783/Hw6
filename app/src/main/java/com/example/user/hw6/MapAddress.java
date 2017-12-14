package com.example.user.hw6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MapAddress extends AppCompatActivity {

    TextView textPersonName , textStoreLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_map_address );

        Bundle bundle = getIntent().getExtras().getBundle("key1");
        String getStoreData = bundle.getString("StoreName");
        String getLocationData = bundle.getString("StoreLocation");

        textPersonName = (TextView) findViewById(R.id.textPersonName);
        textStoreLocation = (TextView) findViewById(R.id.textStoreLocation);

        textPersonName.setText(getStoreData +"\n");
        textStoreLocation.setText(getLocationData+ "\n");
    }
}
