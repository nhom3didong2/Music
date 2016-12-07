package com.example.phong.music;
/**
 * Created by phong on 12/5/2016.
 */
import android.content.Intent;
import android.os.Handler;
import android.app.Activity;
import android.os.Bundle;

public class SplashScreenActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MainLayoutActivity.class);
                overridePendingTransition(R.anim.animfadein,R.anim.animfadeout);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
