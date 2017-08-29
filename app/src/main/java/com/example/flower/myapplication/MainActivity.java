package com.example.flower.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.img)
    ImageView img;

    private Context mContext;

    /**
     * terst*******************
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        int a = 10;
        int b = 12;
        Log.d(TAG, "onCreate: " + a);
        AnimationSet anim = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setStartOffset(500);
        alphaAnimation.setDuration(1000);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 90, 180, 180);
        rotateAnimation.setStartOffset(500);
        rotateAnimation.setDuration(1000);

        anim.addAnimation(alphaAnimation);
        anim.addAnimation(rotateAnimation);
        img.startAnimation(anim);
    }


}