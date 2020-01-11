package com.pushpole.sample.as.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pushpole.sdk.PushPole;
import com.pushpole.sample.as.R;

public class SimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        PushPole.initialize(this, true);

        findViewById(R.id.advanced).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SimpleActivity.this, MainActivity.class));
            }
        });
    }
}
