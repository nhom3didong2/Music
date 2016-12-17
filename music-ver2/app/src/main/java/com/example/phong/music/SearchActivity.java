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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity{
    EditText editText;
    Button btnSearch;
    ListView lv;
    private ImageView back;
    ArrayList<Music> listSongs;
    private Database db;
    private ArrayList<Music> songfined;
    private SearchAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_search_layout);

        final Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animfadeout);
        editText = (EditText) findViewById(R.id.search_edit);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        songfined = new ArrayList<Music>();
        lv= (ListView) findViewById(R.id.lvSearch);

        adapter = new SearchAdapter(this, R.layout.item_list2_search_music,songfined);
        lv.setAdapter(adapter);

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
                onBackPressed();
            }
        });

        //get list music

        listSongs = new ArrayList<Music>();
        db = new Database(this);
        db.readMusics(listSongs);



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence cha, int i, int i1, int i2) {
                Log.d("testf",cha.toString());
                searchSongs(cha);
                cha.toString();
                if(cha.equals("")||cha.length() < 1){
                    songfined.clear();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = songfined.get(position).getMusic_name();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                Bundle b = new Bundle();
                b.putString("name",name);
                intent.putExtra("search",b);
                startActivity(intent);
            }
        });

    }

    private void searchSongs(CharSequence name){
        songfined.clear();
        for(int i = 0; i< listSongs.size(); i++){
            String nameSong = listSongs.get(i).getMusic_name();
            String s = name.toString();
            s.toLowerCase();
            nameSong.toLowerCase();
            if(nameSong.contains(s)){
                //Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
                songfined.add(listSongs.get(i));
            }
        }
		adapter.notifyDataSetChanged();
    }

}

