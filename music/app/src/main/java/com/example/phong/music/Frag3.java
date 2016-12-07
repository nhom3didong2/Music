package com.example.phong.music;
/**
 * Created by phong on 12/5/2016.
 */
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Frag3 extends Fragment {
	private MediaPlayer mPlayler = new MediaPlayer();
	private MusicService mService = new MusicService();
	private boolean flagPlay = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_3, container, false);
		return view;
	}
}
