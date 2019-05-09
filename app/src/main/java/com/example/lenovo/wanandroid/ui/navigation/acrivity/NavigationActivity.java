package com.example.lenovo.wanandroid.ui.navigation.acrivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lenovo.wanandroid.R;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String name = intent.getStringExtra("link");


    }
}
