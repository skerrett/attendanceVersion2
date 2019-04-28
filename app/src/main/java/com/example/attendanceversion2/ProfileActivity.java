package com.example.attendanceversion2;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity implements LessonAdapter.onItemClickListener{

    private TextView textViewResult;
    private RecyclerView RecyclerView;
    private AttendanceApi attendanceApi;
    private LessonAdapter lessonAdapter;
    private ArrayList<LessonResponse> LessonList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        RecyclerView = findViewById(R.id.recycler_view);
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));

        LessonList = new ArrayList<>();
        Intent intent = getIntent();
        int text = intent.getIntExtra(LoginActivity.EXTRA_NUMBER,0);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-attendance-app-staging.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        attendanceApi = retrofit.create(AttendanceApi.class);

        getLessons(text);
    }

    private void getLessons(int text) {

        Call<List<LessonResponse>> call = attendanceApi.getLesson(text);


        call.enqueue(new Callback<List<LessonResponse>>() {
            @Override
            public void onResponse(Call<List<LessonResponse>> call, Response<List<LessonResponse>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Code:", "" + response.code());
                    return;
                }

                List<LessonResponse> lessons = response.body();

                for (LessonResponse lesson : lessons) {


                    String name = lesson.getName();
                    int id = lesson.getId();
                    String date = lesson.getDate();

                    LessonList.add(new LessonResponse(name, date, id));
                }
                lessonAdapter = new LessonAdapter(getApplicationContext(), LessonList);
                RecyclerView.setAdapter(lessonAdapter);
                lessonAdapter.setOnItemClickListnerer(ProfileActivity.this);

            }

            @Override
            public void onFailure(Call<List<LessonResponse>> call, Throwable t) {
                String message = t.getMessage();
                Log.d("failure", message);
            }
        });

    }

        @Override
        public void onItemClick(int postion) {

            LessonResponse clickeditem = LessonList.get(postion);
            int number = clickeditem.getId();
            Log.d("ID = ", number+"");
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.putExtra("ID", number);
            ProfileActivity.this.startActivity(intent);

        }

    }
