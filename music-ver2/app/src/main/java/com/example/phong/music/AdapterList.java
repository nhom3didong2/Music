package com.example.phong.music;
/**
 * Created by phong on 12/5/2016.
 */

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterList extends ArrayAdapter<String> {

	private Activity context;
	private int idLayout;
	private ArrayList<String> arr;

	public AdapterList(Activity context, int idLayout, ArrayList<String> arr) {
		super(context, idLayout, arr);
		this.context = context;
		this.idLayout = idLayout;
		this.arr = arr;
	}
	private class ViewHolder{
		TextView songName;
		ImageView menu2;
	}
	ViewHolder holder = null;
	@NonNull
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = context.getLayoutInflater().inflate(idLayout, null);
			holder = new ViewHolder();
			holder.menu2 = (ImageView) convertView.findViewById(R.id.menu2);
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
			holder.menu2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					switch (view.getId()) {
						case R.id.menu2:

							PopupMenu popup = new PopupMenu(context, view);
							popup.getMenuInflater().inflate(R.menu.select_list_layout_drawer,
									popup.getMenu());
							popup.show();
							popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
								@Override
								public boolean onMenuItemClick(MenuItem item) {

									switch (item.getItemId()) {
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
		dialog.setTitle("Do you want delete");
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
}
