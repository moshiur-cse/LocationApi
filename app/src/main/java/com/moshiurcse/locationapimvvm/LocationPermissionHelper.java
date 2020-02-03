package com.moshiurcse.locationapimvvm;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.drm.DrmStore;

public final class LocationPermissionHelper {

    private static final int LOCATION_CODE=123;
    private static final String LOCaTION_PERMISSION= Manifest.permission.ACCESS_FINE_LOCATION;
    public static boolean hasLocationPermission(Activity activity){
        return activity.checkSelfPermission(LOCaTION_PERMISSION)== PackageManager.PERMISSION_GRANTED;
    }

    public static void requestLocationPermission(Activity activity){
        activity.requestPermissions(new String[]{LOCaTION_PERMISSION},LOCATION_CODE);
    }
}
