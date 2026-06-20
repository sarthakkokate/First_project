package com.example.vaibhav_graphics;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    boolean doubleTap = false;
    SharedPreferences Preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuhomemyprofile)
        {
            Intent i = new Intent(HomeActivity.this,MyProfileActivity.class);
            startActivity(i);
        }
        else if (item.getItemId() == R.id.menuhomesettings)
        {
            Intent i = new Intent(HomeActivity.this,SettingsActivity.class);
            startActivity(i);
            Toast.makeText(HomeActivity.this, "Settings Click", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.menuhomecontactus)
        {
            Intent i = new Intent(HomeActivity.this,ContactUsActivity.class);
            startActivity(i);
            Toast.makeText(HomeActivity.this, "Contact Us Click", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.menuhomeaboutus)
        {
            Intent i = new Intent(HomeActivity.this,AboutUsActivity.class);
            startActivity(i);
            Toast.makeText(HomeActivity.this, "About Us Click", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.menuhomelogout)
        {
            logout();
        }

        return true;
    }

    private void logout()
    {
        AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity.this);
        ad.setTitle("Logout");
        ad.setMessage("Are you sure,You want to Logout?");
        ad.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent i = new Intent(HomeActivity.this,LoginActivity2.class);
                editor.putBoolean("isLogin",false).commit();
                startActivity(i);
                finishAffinity();
            }
        });

        ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show().create();

    }

    @SuppressLint({"MissingSuperCall", "GestureBackNavigation"})
    @Override
    public void onBackPressed() {
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
}


