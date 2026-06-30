package com.example.vaibhav_graphics.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vaibhav_graphics.HomeActivity;
import com.example.vaibhav_graphics.R;
import com.example.vaibhav_graphics.googlemap.MyLocationActivity;


public class MylocationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent i = new Intent(requireActivity(), MyLocationActivity.class);
        startActivity(i);

     View view  = inflater.inflate(R.layout.fragment_content, container, false);
        Toast.makeText(getActivity(), "my location fragment is open", Toast.LENGTH_SHORT).show();

     return view;
    }
}