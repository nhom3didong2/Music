package com.example.phong.music;
/**
 * Created by phong on 12/5/2016.
 */
import android.app.Dialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<String> {

	private Activity context;
	private int idLayout;
	private ArrayList<String> arr;

	public MyAdapter(Activity context, int idLayout, ArrayList<String> arr) {
		super(context, idLayout, arr);
		this.context = context;
		this.idLayout = idLayout;
		this.arr = arr;
	}
	private class ViewHolder{
		TextView songName;
		ImageView menu;
	}
	ViewHolder holder = null;
	@NonNull
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = context.getLayoutInflater().inflate(idLayout, null);
			holder = new ViewHolder();
			holder.menu = (ImageView) convertView.findViewById(R.id.menu);
			holder.songName = (TextView) convertView.findViewById(R.id.txtitem_song);
			convertView.setTag(holder);
		}else
		holder = (ViewHolder) convertView.getTag();
		String name = arr.get(position);
		if(name !=null)
		{
			holder.songName.setText(name);
		}
		try {
			holder.menu.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					switch (view.getId()) {
						case R.id.menu:

							PopupMenu popup = new PopupMenu(context, view);
							popup.getMenuInflater().inflate(R.menu.select_layout_drawer,
									popup.getMenu());
							popup.show();
							popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
								@Override
								public boolean onMenuItemClick(MenuItem item) {

									switch (item.getItemId()) {
										case R.id.new_play_list: {
											Addplaylist(position);
											break;
										}
										case R.id.del:
											Delplaylist(position);
											break;
										default:
											break;
									}
									return true;
								}
							});
							break;
						default:
							break;
					}
				}
			});
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return convertView;
	}
	void Delplaylist(int position){
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_del_layout);
		dialog.setTitle("Add New");
		dialog.show();
		Button btnDel = (Button) dialog.findViewById(R.id.btnDel);
		final Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
		btnDel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.cancel();
			}
		});
	}
	void Addplaylist(int position){
		final int p = position;
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_list_layout);
		dialog.setTitle("New Playlist");
		dialog.show();
		Button btnAdd = (Button) dialog.findViewById(R.id.btnAdd);
		final Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

		btnAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.dialog_input_layout);
				dialog.setTitle("Add New");
				dialog.show();
				Button btnOk = (Button) dialog.findViewById(R.id.btnOK);
				final Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
				btnOk.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {

					}
				});
				btnCancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						dialog.cancel();
					}
				});

			}
		});
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.cancel();
			}
		});
	}
}
