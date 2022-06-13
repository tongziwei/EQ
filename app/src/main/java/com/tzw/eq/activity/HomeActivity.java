package com.tzw.eq.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tzw.eq.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void openEQ1FrequencyResponse(View view){
        Intent intent = new Intent(HomeActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void openEQ2FrequencyResponse(View view){
        Intent intent = new Intent(HomeActivity.this,EQActivity.class);
        startActivity(intent);
    }

    public void openCompressor(View view){
        Intent intent = new Intent(HomeActivity.this,CompressorActivity.class);
        startActivity(intent);
    }

}