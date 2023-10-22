package edu.ewubd.quizzler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private TextView crazy8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        crazy8=findViewById(R.id.crazy8);

        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.myanim);
        crazy8.setAnimation(animation);

        int secondsDelayed =1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        },secondsDelayed*3000);
    }
}
