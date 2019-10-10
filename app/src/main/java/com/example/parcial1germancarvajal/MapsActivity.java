package com.example.parcial1germancarvajal;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parcial1germancarvajal.R;
import com.example.parcial1germancarvajal.app.ArithGoApp;
import com.example.parcial1germancarvajal.model.data.CRUDPremio;
import com.example.parcial1germancarvajal.model.data.CRUDUser;
import com.example.parcial1germancarvajal.model.driver.DBDriver;
import com.example.parcial1germancarvajal.model.entity.UserData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, LocationListener {

    private GoogleMap mMap;
    private Marker actualLocation;
    private Button buttonCenter;
    private Button buttonReactive;
    private HashMap<String,PolygonOptions> polygons;
    private TextView reactiveView;
    private TextView puntaje;
    private boolean autoFollow;
    private boolean onArea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }, 11);

        buttonCenter=findViewById(R.id.buttonCenter);
        buttonCenter.setOnClickListener(this);
        buttonReactive=findViewById(R.id.buttonReactive);
        buttonReactive.setOnClickListener(this);
        buttonReactive.setVisibility(View.INVISIBLE);
        buttonReactive.setClickable(false);
        reactiveView=findViewById(R.id.viewReactive);
        reactiveView.setVisibility(View.INVISIBLE);
        puntaje=findViewById(R.id.viewPuntaje);
        puntaje.setBackgroundColor(Color.RED);
        puntaje.setTextColor(Color.WHITE);
        onArea=false;
        autoFollow=true;
        polygons=new HashMap<>();


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    @SuppressLint("MissingPermission")
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng icesi = new LatLng(3.34175, -76.53050);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(icesi, 18));
        actualLocation=mMap.addMarker(new MarkerOptions().position(icesi));
        actualLocation.setIcon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

//        Biblioteca
//        3.341939, -76.530106
//        3.341922, -76.529802
//        3.341638, -76.529813
//        3.341646, -76.530118
//
//        Saman
//        3.341894, -76.530536
//        3.341887, -76.530362
//        3.341693, -76.530362
//        3.341698, -76.530550
//
//        Edificio A
//        3.342244, -76.530366
//        3.342241, -76.530074
//        3.342111, -76.530066
//        3.342090, -76.529830
//        3.341948, -76.529822
//        3.341988, -76.530375

       PolygonOptions polygonBiblioteca=new PolygonOptions()
               .add(new LatLng(3.341939, -76.530106))
               .add(new LatLng(3.341922, -76.529802))
               .add(new LatLng(3.341638, -76.529813))
               .add(new LatLng(3.341646, -76.530118))
               .fillColor(Color.CYAN);

        PolygonOptions polygonSaman=new PolygonOptions()
                .add(new LatLng(3.341894, -76.530536))
                .add(new LatLng(3.341887, -76.530362))
                .add(new LatLng(3.341693, -76.530362))
                .add(new LatLng(3.341698, -76.530550))
                .fillColor(Color.GREEN);

        PolygonOptions polygonEdificioA=new PolygonOptions()
                .add(new LatLng(3.342244, -76.530366))
                .add(new LatLng(3.342241, -76.530074))
                .add(new LatLng(3.342111, -76.530066))
                .add(new LatLng(3.342090, -76.529830))
                .add(new LatLng(3.341948, -76.529822))
                .add(new LatLng(3.341988, -76.530375))
                .fillColor(Color.RED);

        mMap.addPolygon(polygonBiblioteca);
        mMap.addPolygon(polygonEdificioA);
        mMap.addPolygon(polygonSaman);
        //Toast.makeText(this,polygonBiblioteca.getPoints().isEmpty()+"",Toast.LENGTH_LONG).show();
        polygons.put("Biblioteca",(polygonBiblioteca));
        polygons.put("Saman",(polygonSaman));
        polygons.put("Edificio A" ,(polygonEdificioA));

        if(CRUDUser.getUser(CRUDUser.masterUser).getUsername()==null){
            CRUDUser.insertarUsuario(new UserData(CRUDUser.masterUser,0));
        }
        else{
            puntaje.setText("Puntaje: "+CRUDUser.getUser(CRUDUser.masterUser).getPoints());
        }
        if(CRUDPremio.darPremios().size()==0){
            CRUDPremio.insertarTodosLosPremios();
        }

        //Solicitud de Ubicacion
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonReactive){
            if(reactiveView.getText().toString().equals("Biblioteca")){

                Intent i=new Intent(MapsActivity.this,BibliotecaActivity.class);
                startActivityForResult(i,12);
            }
            else{
                Intent i=new Intent(MapsActivity.this,QuestionActivity.class);
                startActivityForResult(i,11);
            }
        }
        else if(v.getId()==R.id.buttonCenter){

            if(autoFollow){
                Toast.makeText(this,"Seguimiento automatico desactivado",Toast.LENGTH_SHORT).show();
                buttonCenter.setBackgroundColor(Color.RED);
            }else{
                Toast.makeText(this,"Seguimiento automatico activado",Toast.LENGTH_SHORT).show();
                buttonCenter.setBackgroundColor(0xFF359C5E);
            }
            autoFollow=!autoFollow;

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        actualLocation.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
        if(autoFollow){
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(actualLocation.getPosition(),18));
        }

        if(PolyUtil.containsLocation(actualLocation.getPosition(),polygons.get("Biblioteca").getPoints(),true)){
            reactiveView.setVisibility(View.VISIBLE);
            reactiveView.setBackgroundColor(polygons.get("Biblioteca").getFillColor());
            reactiveView.setText("Biblioteca");
            buttonReactive.setVisibility(View.VISIBLE);
            buttonReactive.setClickable(true);
        }
        else if(PolyUtil.containsLocation(actualLocation.getPosition(),polygons.get("Saman").getPoints(),true)){
            reactiveView.setVisibility(View.VISIBLE);
            reactiveView.setBackgroundColor(polygons.get("Saman").getFillColor());
            reactiveView.setText("Saman");
            buttonReactive.setVisibility(View.VISIBLE);
            buttonReactive.setClickable(true);
            if(!onArea){
                onArea=true;
                Intent i=new Intent(MapsActivity.this,QuestionActivity.class);
                startActivityForResult(i,11);
            }
        }
        else if(PolyUtil.containsLocation(actualLocation.getPosition(),polygons.get("Edificio A").getPoints(),true)){
            reactiveView.setVisibility(View.VISIBLE);
            reactiveView.setBackgroundColor(polygons.get("Edificio A").getFillColor());
            reactiveView.setText("EdificioA");
            buttonReactive.setVisibility(View.VISIBLE);
            buttonReactive.setClickable(true);
            if(!onArea){
                onArea=true;
                Intent i=new Intent(MapsActivity.this,QuestionActivity.class);
                startActivityForResult(i,11);
            }
        }
        else{
            reactiveView.setVisibility(View.INVISIBLE);
            buttonReactive.setVisibility(View.INVISIBLE);
            buttonReactive.setClickable(false);
            onArea=false;
        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0){

        }
        else if(requestCode==11){
            int score=Integer.parseInt(puntaje.getText().toString().split(" ")[1]);
            if((boolean)data.getExtras().get("result")){
                puntaje.setText("Puntaje: "+(++score));
            }
            else {
                puntaje.setText("Puntaje: "+(--score));
            }
            CRUDUser.cambiarPuntajeMU(new UserData(CRUDUser.masterUser,score));

        }
        else if(requestCode==12){
            int puntos=data.getExtras().getInt("puntos");
            puntaje.setText("Puntaje: "+puntos);
            CRUDUser.cambiarPuntajeMU(new UserData(CRUDUser.masterUser,puntos));
        }

    }
}
