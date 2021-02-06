package com.fei.aspectjdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fei.aspectjdemo.ui.login.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @CheckNet
    public void jump(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}