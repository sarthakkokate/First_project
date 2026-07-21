package com.example.vaibhav_graphics.Fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.vaibhav_graphics.Banner_FlexActivity;
import com.example.vaibhav_graphics.ContactUsActivity;
import com.example.vaibhav_graphics.PamplhletdesignActivity;
import com.example.vaibhav_graphics.R;
import com.example.vaibhav_graphics.SocialmedieapostActivity;
import com.example.vaibhav_graphics.VisitingcardActivity;
import com.example.vaibhav_graphics.WeddingcardActivity;
import com.example.vaibhav_graphics.logodesignActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    CardView cardViewlogo;
    CardView cardViewwedding;
    CardView cardviewvisitingcard;
    CardView cardviewbannerflex;
    CardView cardsocialmediea;
    CardView cardpamphlet;

    TextView txtGreeting;
    private ImageSlider imageSlider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageSlider = view.findViewById(R.id.isImagesslider);
        ArrayList<SlideModel> slideModelArrayList =new ArrayList<>();
        slideModelArrayList.add(new SlideModel(R.drawable.image_slider_image1,  ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.image_slider_image2,  ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.image_slider_image3,  ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.image_slider_image4,  ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.image_slider_image5,  ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModelArrayList);
        imageSlider.setSlideAnimation(AnimationTypes.FOREGROUND_TO_BACKGROUND);
        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                Intent intent = new Intent(requireContext(), ContactUsActivity.class);
                startActivity(intent);
                Toast.makeText(requireContext(), "ContactUsActivity Open",Toast.LENGTH_SHORT);
            }
            @Override
            public void doubleClick(int i) {
            }
        });

        Toast.makeText(getActivity(), "Home Fragment Open", Toast.LENGTH_SHORT).show();


        cardViewlogo = view.findViewById(R.id.logo_design);
        cardViewwedding = view.findViewById(R.id.wedding_card);
        cardviewvisitingcard = view.findViewById(R.id.id_cards);
        cardviewbannerflex = view.findViewById(R.id.flex_banner);
        cardsocialmediea = view.findViewById(R.id.socialmedia_design);
        cardpamphlet = view.findViewById(R.id.pamplet);
        txtGreeting = view.findViewById(R.id.txtGreeting);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        String greeting;

                if (hour >= 5 && hour < 12)
                  {
                    greeting = "Good Morning";
                    } else if (hour >= 12 && hour < 17)
                   {
                     greeting = "Good Afternoon";
                   } else if (hour >= 17 && hour < 21)
                   {
                     greeting = "Good Evening";
                   } else
                   {
                     greeting = "Good Night";
                   }
                txtGreeting.setText(greeting);


        txtGreeting.setAlpha(0f);
        txtGreeting.setTranslationY(-150f);
        txtGreeting.setScaleX(0.8f);
        txtGreeting.setScaleY(0.8f);

        txtGreeting.animate()
                .alpha(1f)
                .translationY(0)
                .setDuration(1000)
                .setStartDelay(300)
                .start();

        ObjectAnimator scaleX =
                ObjectAnimator.ofFloat(txtGreeting, "scaleX", 0.8f, 1f);

        ObjectAnimator scaleY =
                ObjectAnimator.ofFloat(txtGreeting, "scaleY", 0.8f, 1f);

        scaleX.setDuration(1000);
        scaleY.setDuration(1000);

        scaleX.setInterpolator(new BounceInterpolator());
        scaleY.setInterpolator(new BounceInterpolator());

        scaleX.start();
        scaleY.start();


        cardViewlogo.setOnClickListener(v ->
        {
            Intent intent = new Intent(getActivity(), logodesignActivity.class);
            startActivity(intent);
        });

        cardViewwedding.setOnClickListener(v ->
        {
            Intent intent = new Intent(getActivity(), WeddingcardActivity.class);
            startActivity(intent);
        });

        cardviewvisitingcard.setOnClickListener(v ->
        {
            Intent intent = new Intent(getActivity(), VisitingcardActivity.class);
            startActivity(intent);
        });

        cardviewbannerflex.setOnClickListener(v ->
        {
            Intent intent = new Intent(getActivity(), Banner_FlexActivity.class);
            startActivity(intent);
        });
        cardsocialmediea.setOnClickListener(v ->
        {
            Intent intent = new Intent(getActivity(), SocialmedieapostActivity.class);
            startActivity(intent);
        });
        cardpamphlet.setOnClickListener(v ->
        {
            Intent intent = new Intent(getActivity(), PamplhletdesignActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
