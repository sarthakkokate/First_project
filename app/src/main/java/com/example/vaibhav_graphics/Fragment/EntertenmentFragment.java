package com.example.vaibhav_graphics.Fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vaibhav_graphics.R;

import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EntertenmentFragment extends Fragment {

    TextView tvSongName, tvstartTime, tvEndTime;
    ImageView ivSongCover, ivPrevious, ivbackwad, ivPlay, ivForward, ivNext;
    SeekBar sbSongProgress;
    ListView lvSongs;

    MediaPlayer medieaplayer;

    private static int songprogressIndex =0;
    private  static  int sTime =0,tTime = 0, oTime =0,fTime =5000, bTime = 5000;
    Handler handler = new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toast.makeText(getActivity(), "Owener_Fragment_Open", Toast.LENGTH_SHORT).show();
        View view =   inflater.inflate(R.layout.fregment_entertenment, container, false);


        tvSongName = view.findViewById(R.id.tvEntertainmentSongName);
        tvstartTime = view.findViewById(R.id.tvEntertenmentsongstarttime);
        tvEndTime = view.findViewById(R.id.tvEntertenmentsongendtime);
        ivSongCover = view.findViewById(R.id.ivEntertenmentsongcoverpage);
        ivPrevious = view.findViewById(R.id.ibEntertenmentPreviousSong);
        ivbackwad = view.findViewById(R.id.ibEntertenmentBackwardSong);
        ivPlay = view.findViewById(R.id.ibEntertainmentPlay);
        ivForward = view.findViewById(R.id.ibEnertenmentForward);
        ivNext = view.findViewById(R.id.ibEntertenmentNext);
        sbSongProgress = view.findViewById(R.id.sbEntertenmentSongProgress);


        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (medieaplayer == null) {
                    medieaplayer = MediaPlayer.create(requireContext(), R.raw.hamaresath);
                }

                if (medieaplayer != null) {
                    medieaplayer.start();
                    Toast.makeText(requireContext(), "Playing...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "MediaPlayer create failed", Toast.LENGTH_SHORT).show();
                }
            }
        });





        return view;


    }
}