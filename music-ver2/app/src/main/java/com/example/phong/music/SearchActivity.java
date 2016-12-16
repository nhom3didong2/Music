package com.example.phong.music;
/**
 * Created by phong on 12/5/2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity{
    EditText editText;
    Button btnSearch;
    ListView lv;
    private ImageView back;
    static ArrayList<String> arr;
    ArrayList<Music> usedMusics;
    private Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_search_layout);

        final Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animfadeout);
        editText = (EditText) findViewById(R.id.search_edit);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        lv= (ListView) findViewById(R.id.lvSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = new AlphaAnimation(1.0f,0.0f);
                animation.setDuration(50);
                btnSearch.startAnimation(animation);
            }
        });
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, MainLayoutActivity.class);
                startActivity(intent);
                Animation animation = new AlphaAnimation(1.0f,0.0f);
                animation.setDuration(50);
                back.startAnimation(animation);
                back.startAnimation(animation2);
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence cha, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}

