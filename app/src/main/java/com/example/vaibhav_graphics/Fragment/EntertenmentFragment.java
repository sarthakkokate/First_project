package com.example.vaibhav_graphics.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.vaibhav_graphics.R;

public class EntertenmentFragment extends Fragment {

    TextView tvSongName, tvstartTime, tvEndTime;
    ImageView ivSongCover;

    ImageButton btnPrevious, btnbackward, btnPlay, btnForward, btnNext ,btnShuffle;

    SeekBar seekBar;
    ListView listSongs;

    MediaPlayer mediaPlayer;

    Handler handler = new Handler();

    int currentSong = 0;
    boolean isShuffle=false;


    private final int[] songs = {
            R.raw.hamaresath,
            R.raw.arbhi_ghode,
            R.raw.mitwa_song,
            R.raw.hedukhvandan,
            R.raw.ladhar_tu_baliraja
    };

    private final String[] songNames = {
            "Hamare Sath Shri Raghunath",
            "Arbhi Ghode",
            "Mitwa Song",
            "He Dukhvandan",
            "Ladhar Tu Baliraja"
    };

    private final int[] images = {
            R.drawable.hamresath,
            R.drawable.arbi_ghode_poster,
            R.drawable.mitwa_song_poster,
            R.drawable.sankat_mochan_hanuman,
            R.drawable.baliraja_song_poster
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fregment_entertenment,
                container,
                false);

        Toast.makeText(getActivity(),
                "Entertainment Fragment Open",
                Toast.LENGTH_SHORT).show();

        tvSongName = view.findViewById(R.id.tvEntertainmentSongName);
        tvstartTime = view.findViewById(R.id.tvEntertenmentsongstarttime);
        tvEndTime = view.findViewById(R.id.tvEntertenmentsongendtime);

        ivSongCover = view.findViewById(R.id.ivEntertenmentsongcoverpage);

        btnPrevious = view.findViewById(R.id.ibEntertenmentPreviousSong);
        btnbackward = view.findViewById(R.id.ibEntertenmentBackwardSong);
        btnPlay = view.findViewById(R.id.ibEntertainmentPlay);
        btnForward = view.findViewById(R.id.ibEnertenmentForward);
        btnNext = view.findViewById(R.id.ibEntertenmentNext);

        seekBar = view.findViewById(R.id.sbEntertenmentSongProgress);

        listSongs = view.findViewById(R.id.listSongs);
        btnShuffle = view.findViewById(R.id.ibShuffle);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        songNames);

        listSongs.setAdapter(adapter);

        listSongs.setOnItemClickListener((parent, view1, position, id) -> {

            currentSong = position;

            loadSong(currentSong);

        });

        loadSong(currentSong);

        btnPlay.setOnClickListener(v -> {

            if (mediaPlayer == null)
                return;

            if (mediaPlayer.isPlaying()) {

                mediaPlayer.pause();

                btnPlay.setImageResource(R.drawable.play_icon);

            } else {

                mediaPlayer.start();

                btnPlay.setImageResource(R.drawable.pause_icon);

            }

        });
        btnShuffle.setOnClickListener(v -> {

            isShuffle = !isShuffle;

            if (isShuffle) {
                btnShuffle.setAlpha(1.0f);
            } else {
                btnShuffle.setAlpha(0.5f);
            }

        });

        btnNext.setOnClickListener(v -> {

            if (isShuffle) {

                currentSong = (int) (Math.random() * songs.length);

            } else {

                currentSong++;

                if (currentSong >= songs.length)
                    currentSong = 0;
            }

            loadSong(currentSong);

        });

        btnPrevious.setOnClickListener(v -> {

            currentSong--;

            if (currentSong < 0) {

                currentSong = songs.length - 1;

            }

            loadSong(currentSong);

        });

        btnForward.setOnClickListener(v -> {

            if (mediaPlayer != null) {

                mediaPlayer.seekTo(
                        mediaPlayer.getCurrentPosition() + 5000);

            }

        });

        btnbackward.setOnClickListener(v -> {

            if (mediaPlayer != null) {

                mediaPlayer.seekTo(
                        Math.max(
                                mediaPlayer.getCurrentPosition() - 5000,
                                0));

            }

        });

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress,
                                                  boolean fromUser) {

                        if (fromUser && mediaPlayer != null) {

                            mediaPlayer.seekTo(progress);

                        }

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                });

        return view;

    }
    private void loadSong(int index) {

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = MediaPlayer.create(requireContext(), songs[index]);

        tvSongName.setText("Song Name :- " + songNames[index]);
        ivSongCover.setImageResource(images[index]);

        seekBar.setProgress(0);
        seekBar.setMax(mediaPlayer.getDuration());

        tvstartTime.setText("00:00");
        tvEndTime.setText(formatTime(mediaPlayer.getDuration()));

        mediaPlayer.start();

        btnPlay.setImageResource(R.drawable.pause_icon);

        handler.removeCallbacks(updateSeekBar);
        handler.post(updateSeekBar);

        mediaPlayer.setOnCompletionListener(mp -> {

            if (isShuffle) {

                currentSong = (int)(Math.random() * songs.length);

            } else {

                currentSong++;

                if (currentSong >= songs.length)
                    currentSong = 0;
            }

            loadSong(currentSong);

        });


    }

    private final Runnable updateSeekBar = new Runnable() {

        @Override
        public void run() {

            if (mediaPlayer != null) {

                try {

                    if (mediaPlayer.isPlaying()) {

                        seekBar.setProgress(mediaPlayer.getCurrentPosition());

                        tvstartTime.setText(
                                formatTime(mediaPlayer.getCurrentPosition()));

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }

                handler.postDelayed(this, 1000);

            }

        }

    };

    private String formatTime(int milliseconds) {

        int minutes = (milliseconds / 1000) / 60;

        int seconds = (milliseconds / 1000) % 60;

        return String.format("%02d:%02d", minutes, seconds);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        handler.removeCallbacks(updateSeekBar);

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}