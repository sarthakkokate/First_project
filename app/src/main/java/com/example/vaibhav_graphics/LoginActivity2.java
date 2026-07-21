package com.example.vaibhav_graphics;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SharedMemory;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.vaibhav_graphics.comman_things.Urls;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.prefs.Preferences;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
public class LoginActivity2 extends AppCompatActivity {

    ImageView logo;
    TextView title, slogan;
    Button btnLogin,btnregister;
    EditText etLoginUsername,etLoginPassword;
    CheckBox cbLoginShowHidePassword;
    TextView tvForgotpassword;
    ProgressDialog progressDialog;
    Button btnRegister;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
        logo = findViewById(R.id.logo);
        title = findViewById(R.id.tvSplashTitle);
        slogan = findViewById(R.id.tvSplashSlogan);

        btnLogin = findViewById(R.id.btnlogin);
        btnregister=findViewById(R.id.btnregister);
        etLoginUsername=findViewById(R.id.etLoginUsername);
        etLoginPassword=findViewById(R.id.etloginPassword);
        cbLoginShowHidePassword=findViewById(R.id.cbShowHidePassword);
        tvForgotpassword=findViewById(R.id.tvForgotpassword);
        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity2.this);
        editor = preferences.edit();
        progressDialog = new ProgressDialog(LoginActivity2.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);


        if (preferences.getBoolean("islogin", false)) {
            Intent i = new Intent(LoginActivity2.this, HomeActivity.class);
            startActivity(i);
            finish();
        }

        cbLoginShowHidePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    etLoginPassword.
                            setTransformationMethod
                                    (HideReturnsTransformationMethod.getInstance());
                }else {
                    etLoginPassword.
                            setTransformationMethod
                                    (PasswordTransformationMethod.getInstance());
                }
            }
        });

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        
        logo.startAnimation(fadeIn);
        title.startAnimation(fadeIn);
        slogan.startAnimation(fadeIn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etLoginUsername.getText().toString().isEmpty())
                {
                    etLoginUsername.setError("Please Enter Your Username");

                }
                else if (etLoginUsername.getText().toString().length()<8)
                {
                    etLoginUsername.setError("Username Atleast 8 Characters");

                }
                else if (etLoginPassword.getText().toString().isEmpty())
                {
                    etLoginPassword.setError("Please Enter Your Password");

                }
                else if (etLoginPassword.getText().toString().length()<8)
                {
                    etLoginPassword.setError("Password Atleast 8 Characters" );

                }else if (!etLoginUsername.getText().toString().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$"))
                {
                    etLoginUsername.setError("Username must contain Uppercase, Lowercase, Number and Special Character");
                }
                else if (!etLoginPassword.getText().toString().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$"))
                {
                    etLoginPassword.setError("Password must contain Uppercase, Lowercase, Number and Special Character");
                }
                else
                {
                    //Toast.makeText(LoginActivity2.this,"Login Successfully",Toast.LENGTH_SHORT).show();

                    //editor.putBoolean("islogin", true);
                   // editor.apply();

                    //Intent i = new Intent(LoginActivity2.this, HomeActivity.class);
                   // startActivity(i);
                   // finish();

                    loginUser();
                }
            }
        });

        tvForgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(LoginActivity2.this,ForgotActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity2.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void loginUser() {

        progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username", etLoginUsername.getText().toString());
        params.put("password", etLoginPassword.getText().toString());

        client.post(Urls.LOGINUSER_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressDialog.dismiss();

                try {
                    String status = response.getString("success");
                    String message = response.getString("message");

                    if (status.equals("1")) {

                        Toast.makeText(LoginActivity2.this, message, Toast.LENGTH_SHORT).show();

                        editor.putBoolean("islogin", true);
                        editor.apply();

                        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", etLoginUsername.getText().toString());
                        editor.apply();

                        Intent intent = new Intent(LoginActivity2.this, HomeActivity.class);
                        startActivity(intent);
                        finishAffinity();

                    } else {
                        Toast.makeText(LoginActivity2.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity2.this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


