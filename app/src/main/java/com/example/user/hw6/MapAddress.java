package com.example.user.hw6;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.SupportActionModeWrapper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;


import static com.example.user.hw6.R.id.mapppp;

public class MapAddress extends AppCompatActivity {

    TextView textPersonName , textStoreLocation;
    Button return_main;

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
        return_main = (Button)findViewById(R.id.return_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapppp);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (
                        ActivityCompat.checkSelfPermission(MapAddress.this
                                , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(MapAddress.this,
                                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                Bundle bundle = getIntent().getExtras().getBundle("key1");
                String getLocationData = bundle.getString("StoreLocation");
                googleMap.clear();
                Geocoder geocoder = new Geocoder(getBaseContext());
                List<Address> addresslist = null;
                int maxResult = 1;
                try{
                    addresslist = geocoder.getFromLocationName(getLocationData,maxResult);
                }catch (IOException e)
                {
                    Log.e("MapAddress" , e.toString());
                }
                if(addresslist == null || addresslist.isEmpty())
                {
                    Toast.makeText( MapAddress.this, "未找到店名", Toast.LENGTH_SHORT ).show();
                }
                else {
                    double latitude = addresslist.get(0).getLatitude();
                    double longitude = addresslist.get(0).getLongitude();

                    MarkerOptions m1 = new MarkerOptions();
                    m1.position(new LatLng(latitude, longitude));
                    m1.draggable(true);
                    googleMap.addMarker(m1);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12.0f));

                }
            }
        });


        //返回主畫面功能
        return_main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent();
                intent1.setClass(MapAddress.this, MainActivity.class);
                startActivityForResult(intent1, 0);
                Toast.makeText(MapAddress.this, "返回主畫面", Toast.LENGTH_SHORT ).show();
            }
        });







    }

}



