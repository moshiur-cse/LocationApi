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
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    //Find accurate location
    private FusedLocationProviderClient providerClient;
    private LocationViewModel locationViewModel;
    private LocationCallback locationCallback=new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

        }
    };

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

                    createLocationRequest();
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

    private void createLocationRequest() {
        LocationRequest locationRequest=LocationRequest.create();
        locationRequest.setInterval(10000);//mili second
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setNumUpdates(1); //Not require for quick requst

        LocationSettingsRequest locationSettingsRequest=new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient settingsClient=LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> responseTask=settingsClient.checkLocationSettings(locationSettingsRequest);
        responseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                getDeviceLastLocationUpdates();
            }
        }) ;
        responseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {



            }
        });

    }

    private void getDeviceLastLocationUpdates() {
    }


}
