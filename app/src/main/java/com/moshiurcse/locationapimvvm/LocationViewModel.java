package com.moshiurcse.locationapimvvm;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {
    private MutableLiveData<Location> locationMutableLiveData=new MutableLiveData<>();
}
