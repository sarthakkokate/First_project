package com.example.vaibhav_graphics;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;


public class MainActivity extends AppCompatActivity {
    //create
    ImageView ivSplashLogo;
    TextView tvSplashTitle,tvSplashSlogan;

    LottieAnimationView lottieloading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//initialization
        ivSplashLogo=findViewById(R.id.ivSplashLogo);
        tvSplashTitle=findViewById(R.id.tvSplashTitle);
        tvSplashSlogan=findViewById(R.id.tvSplashSlogan);

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        ivSplashLogo.startAnimation(fadeIn);
        tvSplashTitle.startAnimation(fadeIn);
        tvSplashSlogan.startAnimation(fadeIn);

        lottieloading=findViewById(R.id.lottieloding);

        lottieloading.setRepeatCount(LottieDrawable.INFINITE);
        lottieloading.playAnimation();





//classname objectname= new cunstructorname
      Handler handler = new Handler();
      handler.postDelayed(new Runnable() {
          @Override
          public void run() {
            Intent i = new Intent( MainActivity.this,LoginActivity2.class);
            startActivity(i);
            finish();

          }
      },2500);

    }
}