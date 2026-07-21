package com.example.vaibhav_graphics;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.vaibhav_graphics.comman_things.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
public class ForgotActivity extends AppCompatActivity {
    EditText etUsername,etPassword;
    Button btnResetpass;
    String username,newpassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etnewpass);
        btnResetpass = findViewById(R.id.btnResetPassword);
        btnResetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etUsername.getText().toString().isEmpty()){
                    etUsername.setError("Enter Username");
                } else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Enter the password");

                }else{
                    username = etUsername.getText().toString().trim();
                    newpassword = etPassword.getText().toString().trim();
                    ProgressDialog progressDialog = new ProgressDialog(ForgotActivity.this);
                    progressDialog.setMessage("Loading....");
                    progressDialog.show();
                    UpdatePass(username,newpassword);
                }
            }
        });
    }
    private void UpdatePass(String username, String newpassword) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("newpass", newpassword);
        client.post(Urls.Forget_pass,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status=response.getString("success");
                    String mess=response.getString("mess");
                    if(status.equals("1")){
                        Toast.makeText(ForgotActivity.this, mess, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ForgotActivity.this,LoginActivity2.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(ForgotActivity.this, mess, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(ForgotActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
