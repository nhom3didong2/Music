package com.example.phong.music;
/**
 * Created by phong on 12/5/2016.
 */
import com.example.phong.music.MyAdapter;
import com.example.phong.music.MainActivity;
import com.example.phong.music.MusicService;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class Frag1 extends Fragment{

	ArrayList<File> listSongs;

	public Frag1(ArrayList<File> arrFile) {
		//Log.d("test","minh thich thi minh log choi thoi");
		listSongs = arrFile;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.listmusic_fragment_layout, container, false);
		ArrayList<String> arr = new ArrayList<String>();

		for (int i = 0; i < listSongs.size(); i++)
			arr.add(listSongs.get(i).getName().toString());

		MyAdapter adapter = new MyAdapter(this.getActivity(), R.layout.item_list_music, arr);
		ListView lv = (ListView) view.findViewById(R.id.lvSong);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent = new Intent(getActivity().getApplication(), MainActivity.class);
				intent.putExtra("listsongs", listSongs);
				intent.putExtra("position", i);
				startActivity(intent);

			}
		});

		return view;
	}
}
