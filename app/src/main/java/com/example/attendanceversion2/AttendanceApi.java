package com.example.attendanceversion2;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AttendanceApi {


    @PATCH("mobile/present")
    Call<JsonElement>patchStudent(@Query("input") int input,
                                  @Query("lesson") int lesson);

    @FormUrlEncoded
    @PUT("mobile/login")
    Call<LoginResponse> userLogin(
            @Field("emailAddress") String email,
            @Field("password") String password
    );

    @GET("mobile/lesson")
    Call<List<LessonResponse>> getLesson(
            @Query("user") int user);

}
