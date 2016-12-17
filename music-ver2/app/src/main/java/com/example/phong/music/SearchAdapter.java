package com.example.phong.music;
/**
 * Created by phong on 12/5/2016.
 */

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class SearchAdapter extends ArrayAdapter<Music> {
	private static final int DELETE_TYPE = 		0x05;
	private Activity context;
	private int idLayout;
	ArrayList<Music> usedMusics;
	private Database db;

	public SearchAdapter(Activity context, int idLayout, ArrayList<Music> list) {
		super(context,idLayout,list);
		this.context = context;
		this.idLayout = idLayout;
		this.usedMusics = list;
	}
		TextView songName;
	@NonNull
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
			convertView = context.getLayoutInflater().inflate(idLayout, null);
			songName = (TextView) convertView.findViewById(R.id.txtitem_song);


		if(usedMusics.size() > 0 && position >= 0) {
			songName.setText(usedMusics.get(position).getMusic_name());
		}

		return convertView;
	}
}
