package com.example.phong.music;
/**
 * Created by phong on 12/5/2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class Frag2 extends Fragment {

	ArrayList<Music> listSongs;

	public Frag2(ArrayList<Music> arrFile) {
		//Log.d("test","minh thich thi minh log choi thoi");
		listSongs = arrFile;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.mylist_fragment_layout, container, false);

		final ImageView iv = (ImageView) view.findViewById(R.id.imageView3);
		final Animation animation = AnimationUtils.loadAnimation(this.getContext(),R.anim.rotate3);
		iv.startAnimation(animation);

		ArrayList<String> arr = new ArrayList<String>();

		for (int i = 0; i < listSongs.size(); i++)
			arr.add(listSongs.get(i).getMusic_name().toString());

		AdapterList adapter = new AdapterList(this.getActivity(), R.layout.item_list2_music, arr);
		ListView lv = (ListView) view.findViewById(R.id.lvMylist);
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
