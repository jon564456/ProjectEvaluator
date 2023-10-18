package com.itsoeh.jbrigido.projectevaluator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    public static int DURACION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent brinco = new Intent(Splash.this, LoginActivity.class);
                startActivity(brinco);
                finish();
            }
        }, DURACION);
    }
}