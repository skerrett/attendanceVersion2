package com.example.attendanceversion2;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    TextView label;
    IntentFilter[] filters;
    String[][] techs;
    PendingIntent pendingIntent;
    NfcAdapter adapter;
    private AttendanceApi attendanceApi;
    public int text;
    View view;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        label = findViewById(R.id.label);

        this.getWindow().getDecorView();



        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter mifare = new IntentFilter((NfcAdapter.ACTION_TECH_DISCOVERED));
        filters = new IntentFilter[] { mifare };
        techs = new String[][] { new String[] {  NfcA.class.getName() } };
        adapter = NfcAdapter.getDefaultAdapter(this);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-attendance-app-staging.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        attendanceApi = retrofit.create(AttendanceApi.class);

        text = getIntent().getExtras().getInt("ID");

        Log.e("Starting text:", "" + text);



    }

    public void green(){
     view = this.getWindow().getDecorView();
     view.setBackgroundResource(R.color.green);

    }

    public void red(){
        view = this.getWindow().getDecorView();
        view.setBackgroundResource(R.color.red);

    }

    public void starting(){
        view = this.getWindow().getDecorView();
        view.setBackgroundResource(R.color.colorAccent);

    }
    public void onPause() {
        super.onPause();
        adapter.disableForegroundDispatch(this);
    }

    public void onResume() {
        super.onResume();
        adapter.enableForegroundDispatch(this, pendingIntent, filters, techs);
    }

    public int getText(){
        return text;
    }

    public void onNewIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] id = tag.getId();
        ByteBuffer wrapped = ByteBuffer.wrap(id);
        wrapped.order(ByteOrder.LITTLE_ENDIAN);
        int signedInt = wrapped.getInt();
        long number = signedInt & 0xffffffffl;
        label.setText(R.string.wait);
        String nfc = number+"";
        Log.d("text = ", 0+""+getText());
        Log.d("nfc = ", nfc);
        updateStudent(getText(),nfc);
    }

    private void updateStudent(int text,String number){


        Call<ResponseBody> call  = attendanceApi.patchStudent(number,text);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    label.setText(R.string.Error);
                    red();
                }
                else {
                    label.setText(R.string.success);
                    green();



                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                label.setText("Error Student is in the wrong class or not enrolled");
                    red();

            }
        });

            }
    }