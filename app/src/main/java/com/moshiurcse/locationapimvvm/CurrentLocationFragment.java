package com.moshiurcse.locationapimvvm;


import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentLocationFragment extends Fragment {

    private TextView latLngTV;
    private LocationViewModel locationViewModel;
    public CurrentLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        latLngTV=view.findViewById(R.id.locationLatLngTV);
        locationViewModel=new ViewModelProvider(requireActivity()).get(LocationViewModel.class);
        locationViewModel.getLocationMutableLiveData().observe(getActivity(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                double lat=location.getLatitude();
                double lng=location.getLongitude();
                latLngTV.setText(lat+","+lng);



            }
        });
    }
}
