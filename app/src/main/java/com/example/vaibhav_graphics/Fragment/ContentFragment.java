package com.example.vaibhav_graphics.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vaibhav_graphics.R;
public class ContentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toast.makeText(getActivity(), "Content_Fragment_Open", Toast.LENGTH_SHORT).show();
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        return view;
    }
}