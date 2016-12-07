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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity{
    EditText editText;
    Button btnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_search_layout);

        editText = (EditText) findViewById(R.id.search_edit);
        btnSearch = (Button) findViewById(R.id.btnSearch);

    }
}

