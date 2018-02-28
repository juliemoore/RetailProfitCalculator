package com.example.julieannmoore.retailprofitcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Julie Ann Moore on 2/5/2018.
 * The purpose of this activity is to create a 5 second view of the logo
 * spinning around on a gradient background on the initial start of the
 * application.
 */

public class SplashActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    Animation animation;
    Animation animationFadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Lets activity have background called splash
        setContentView(R.layout.activity_splash);
        imageView = (ImageView)findViewById(R.id.splashImageView);
        textView = (TextView)findViewById(R.id.welcomeTextView);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_animation);
        animationFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein_animation);
        textView.setAnimation(animationFadeIn);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }
}
