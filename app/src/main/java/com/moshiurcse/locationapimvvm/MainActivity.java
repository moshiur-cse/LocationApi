package com.moshiurcse.locationapimvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {
    //Find accurate location
    private FusedLocationProviderClient providerClient;
    private LocationViewModel locationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        providerClient= LocationServices.getFusedLocationProviderClient(this);
        locationViewModel=new ViewModelProvider(this).get(LocationViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!LocationPermissionHelper.hasLocationPermission(this)){
            LocationPermissionHelper.requestLocationPermission(this);
            return;
        }
        getDeviceLastLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(!LocationPermissionHelper.hasLocationPermission(this)){
            Toast.makeText(this, "You Denied Location Permission", Toast.LENGTH_SHORT).show();
        return;
        }
        getDeviceLastLocation();

    }

    private void getDeviceLastLocation() {
        providerClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location==null){
                    return;
                }
                locationViewModel.setLocationMutableLiveData(location);
                /*double lat=location.getLatitude();
                double lng=location.getLongitude();

                Log.e("Currenct Location:","Lat:"+lat);
                Log.e("Currenct Location:","long:"+lng);*/


            }
        });

    }


}
