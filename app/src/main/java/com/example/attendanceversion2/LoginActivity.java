package com.example.attendanceversion2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText editTextEmail;
    private EditText editTextPassword;
    private AttendanceApi attendanceApi;
    public static final String EXTRA_NUMBER = "com.example.attendanceversion2.EXTRA_NUMBER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-attendance-app-staging.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        attendanceApi = retrofit.create(AttendanceApi.class);

        findViewById(R.id.signIn).setOnClickListener(this);
    }
        private void userLogin(){

           String email = editTextEmail.getText().toString().trim();
           String password = editTextPassword.getText().toString().trim();
            if (email.isEmpty()) {
                editTextEmail.setError("Email is required");
                editTextEmail.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError("Enter a valid email");
                editTextEmail.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                editTextPassword.setError("Password required");
                editTextPassword.requestFocus();
                return;
            }

            if (password.length() < 6) {
                editTextPassword.setError("Password should be atleast 6 character long");
                editTextPassword.requestFocus();
                return;
            }
            Call<LoginResponse> call = attendanceApi.userLogin(email, password);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginResponse = response.body();

                    if (!response.isSuccessful()) {
                        Log.d("Code:" , ""+ response.code());
                        editTextPassword.setError("Bad Combo Try Again");
                        editTextPassword.requestFocus();
                        return;
                    }
                    else {
                        int text = loginResponse.getUser();
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        intent.putExtra(EXTRA_NUMBER, text);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                }
            });
        }
    @Override
    public void onClick(View v) {
        userLogin();
    }
}
