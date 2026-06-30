package com.example.vaibhav_graphics;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.vaibhav_graphics.Fragment.ContentFragment;
import com.example.vaibhav_graphics.Fragment.HomeFragment;
import com.example.vaibhav_graphics.Fragment.My_OrderFragment;
import com.example.vaibhav_graphics.Fragment.MylocationFragment;
import com.example.vaibhav_graphics.Fragment.EntertenmentFragment;
import com.example.vaibhav_graphics.googlemap.MyLocationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    boolean doubleTap = false;
    SharedPreferences Preferences;
    SharedPreferences.Editor editor;
    FrameLayout homeFrameLayout;
    BottomNavigationView homeBottomNavigationView;
    DrawerLayout drawerlayout;
    ImageView ivmenubtn;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ivmenubtn = findViewById(R.id.ivmenubtn);
        drawerlayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.vaibhav_navigation_view);

        if (ivmenubtn != null && drawerlayout != null) {
            ivmenubtn.setOnClickListener(v -> {
                drawerlayout.openDrawer(GravityCompat.END);
            });
        }

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(item -> {
                if (item.getItemId() == R.id.menuhomemyprofile) {
                    Intent i = new Intent(HomeActivity.this, MyProfileActivity.class);
                    startActivity(i);
                } else if (item.getItemId() == R.id.bottumHomemylocation) {
                    Intent i = new Intent(HomeActivity.this, MyLocationActivity.class);
                    startActivity(i);
                } else if (item.getItemId() == R.id.menuhomesettings) {
                    Intent i = new Intent(HomeActivity.this, SettingsActivity.class);
                    startActivity(i);
                    Toast.makeText(HomeActivity.this, "Settings Click", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.menuhomecontactus) {
                    Intent i = new Intent(HomeActivity.this, ContactUsActivity.class);
                    startActivity(i);
                    Toast.makeText(HomeActivity.this, "Contact Us Click", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.menuhomeaboutus) {
                    Intent i = new Intent(HomeActivity.this, AboutUsActivity.class);
                    startActivity(i);
                    Toast.makeText(HomeActivity.this, "About Us Click", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.menuhomelogout) {
                    logout();
                }
                if (drawerlayout != null) {
                    drawerlayout.closeDrawer(GravityCompat.END);
                }
                return true;

            });
        }
        homeFrameLayout=findViewById(R.id.homeFramelayout);
        homeBottomNavigationView=findViewById(R.id.homeBottonNav);
        if (homeBottomNavigationView != null) {
            homeBottomNavigationView.setOnNavigationItemSelectedListener(this);
            homeBottomNavigationView.setSelectedItemId(R.id.bottomMenuHomeHome);
        }
        Preferences = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        editor = Preferences.edit();
        boolean isFirsTime = Preferences.getBoolean("isFirstTime", true);
        if (isFirsTime) {
            Wllcome();
        }
    }
        private void Wllcome() {
            AlertDialog.Builder ad= new AlertDialog.Builder(HomeActivity.this);
            ad.setTitle("Vaibhav Graphics App");
            ad.setMessage("Welcome To Vaibhav Graphics");
            ad.setPositiveButton("Thank You", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            }).show().create();
            editor.putBoolean("isFirstTime", false).commit();
        }
    private void logout()
    {
        AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity.this);
        ad.setTitle("Logout");
        ad.setMessage("Are you  sure,You want to Logout?");
        ad.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent i = new Intent(HomeActivity.this,LoginActivity2.class);
                editor.putBoolean("isLogin",false).commit();
                startActivity(i);
                finishAffinity();
            }
        }).show().create();

        ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show().create();

    }

    @SuppressLint({"MissingSuperCall", "GestureBackNavigation"})
    @Override public void onBackPressed() {
        if(doubleTap)
        {
            finishAffinity();

        }
        else {
            Toast.makeText(HomeActivity.this, "Double Tab to Exit", Toast.LENGTH_SHORT).show();
            doubleTap = true;
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleTap = false;
                }
            },2000);
        }
    }

    HomeFragment homeFragment= new HomeFragment();
    ContentFragment contentFragment= new ContentFragment();
    My_OrderFragment myOrderFragment = new My_OrderFragment();
    EntertenmentFragment entertenmentFragment = new EntertenmentFragment();
    MylocationFragment mylocationFragment = new MylocationFragment();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.bottomMenuHomeHome)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFramelayout,homeFragment).commit();
        } else if (menuItem.getItemId() == R.id.bottomMenuHomeContent)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFramelayout,contentFragment).commit();
        }else if (menuItem.getItemId() == R.id.bottomMenuHomeMyOrder)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFramelayout,myOrderFragment).commit();
        } else if (menuItem.getItemId() == R.id.bottomMenuHomemusic) {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFramelayout,entertenmentFragment).commit();

        } else if (menuItem.getItemId()== R.id.bottumHomemylocation) {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFramelayout,mylocationFragment).commit();
            Intent i = new Intent(HomeActivity.this,MyLocationActivity.class);
            startActivity(i);
        }
        return true;
    }
}

