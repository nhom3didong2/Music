package com.example.phong.music;
/**
 * Created by phong on 12/5/2016.
 */
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phong.music.Frag1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private MusicService mService = new MusicService();
    static MediaPlayer mPlayler ;
    private boolean flagPlay = false;
    private boolean flagReply = false;
    private boolean isShuffle= false;
    private float currentTime = 0;
    private float finalTime = 0;
    private Handler myHandler = new Handler();
    private SeekBar seekBar;
    private Button btnSearch;
    private ImageView back;
    private ImageButton btnPlay;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;
    private TextView txtStartTime;
    private TextView txtFinalTime;
   // private ArrayList<File> arrSongFile;
    private int position = 0;
    private TextView songName;
    private ImageView iv;
    private Animation animation , animation2;
    String musicPatch;
    private ArrayList<Music> listSongs ;
    private int ranPositon;
    private String currentPatch = null;
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.MyBinder mBinder = (MusicService.MyBinder) iBinder;
            mService = mBinder.getService();

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_music_layout);

        animation2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animfadeout);
        iv = (ImageView) findViewById(R.id.ivMidImage);
         animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate2);
        iv.startAnimation(animation);



        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                Animation animation = new AlphaAnimation(1.0f,0.0f);
                animation.setDuration(50);
                btnSearch.startAnimation(animation);
            }
        });
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainLayoutActivity.class);
                startActivity(intent);
                Animation animation = new AlphaAnimation(1.0f,0.0f);
                animation.setDuration(50);
                back.startAnimation(animation);
                back.startAnimation(animation2);
            }
        });


        //getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            android.view.Window w = getWindow();
            w.setFlags(android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }
        listSongs = new ArrayList<Music>();
        Database db = new Database(this);
        db.readMusics(listSongs);
//        //xử lý việc hát đè lên nhau
        if(mPlayler!=null){
            mPlayler.stop();
            mPlayler.release();
        }
        //truyền dữ liệu

        Intent i = getIntent();
        if (i != null) {
            Bundle b = i.getBundleExtra("position");
            if(b != null)
                position = b.getInt("position");
            Bundle b1 = i.getBundleExtra("search");
                if(b1 != null){
                    String name = b1.getString("name");
                    Log.d("testB",name);
                    for(int j = 0; j<listSongs.size(); j++){
                        String nameSong = listSongs.get(j).getMusic_name();
                        if(nameSong.equals(name)){
                            position = j;
                            break;
                        }
                    }
                }
        }


        musicPatch = listSongs.get(position).getPatch();
        flagReply = false;
        //show name
        songName = (TextView) findViewById(R.id.txtitem_song);
        songName.setText(listSongs.get(position).getMusic_name());
        Log.d("test",position+"");
        Log.d("test",listSongs.get(position).getMusic_name());

        buidMediaPlayer(musicPatch);


        txtStartTime = (TextView) findViewById(R.id.txtStart);
        txtFinalTime = (TextView) findViewById(R.id.txtEnd);
        Intent intent = new Intent(getApplicationContext(), MusicService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);


        Log.d("test",musicPatch);
        //mPlayler = new MediaPlayer();


        //kéo thả seekbar
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(mPlayler.getCurrentPosition());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPlayler.seekTo(seekBar.getProgress());
            }
        });
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);

        btnRepeat.setImageResource(R.drawable.norepeat);
        flagPlay = false;
        playMusic();


        btnShuffle = (ImageButton)findViewById(R.id.btnShuffle);
        btnShuffle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!isShuffle){
                    btnShuffle.setImageResource(R.drawable.shuffle);
                    isShuffle = true;
                }else{
                    btnShuffle.setImageResource(R.drawable.noshuffle);
                    isShuffle = false;
                }
            }
        });
        //reply
       btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flagReply) {
                    Repeat();
                    btnRepeat.setImageResource(R.drawable.repeat_1);
                    flagReply = true;
                } else{
                    noRepeat();
                    btnRepeat.setImageResource(R.drawable.norepeat);
                    flagReply = false;
                }
                Log.d("test",flagReply+"");
                Log.d("test",mPlayler.isLooping()+"");
            }
        });
        //play
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flagPlay) {
                    playMusic();

                    flagPlay = true;
                } else {
                    pauseMusic();

                    flagPlay = false;
                }

            }
        });

        //prev
        final ImageButton btnPrev = (ImageButton) findViewById(R.id.btnPrev);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = new AlphaAnimation(1.0f,0.0f);
                animation.setDuration(50);
                btnPrev.startAnimation(animation);
                if (position > 0) {
                    position -= 1;
                    musicPatch = listSongs.get(position).getPatch();
                    moveSong(musicPatch);
                } else {
                    musicPatch = (listSongs.get(listSongs.size() - 1).getPatch());
                    position = listSongs.size() - 1;
                    moveSong(musicPatch);
                }
            }
        });
        //next
        final ImageButton btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = new AlphaAnimation(1.0f,0.0f);
                animation.setDuration(50);
                btnNext.startAnimation(animation);
                if(!isShuffle) {
                    if (position < listSongs.size() - 1) {
                        position += 1;
                        musicPatch = listSongs.get(position).getPatch();
                        moveSong(musicPatch);
                    } else {
                        musicPatch = listSongs.get(0).getPatch();
                        position = 0;
                        moveSong(musicPatch);
                    }
                }else{
                    autoNextRandom();
                }
            }
        });
    }
    private Runnable updateTime = new Runnable() {
        @Override
        public void run() {
            currentTime = mPlayler.getCurrentPosition();
            seekBar.setProgress((int) currentTime);
            updateTextStart();
            myHandler.postDelayed(this, 100);
        }
    };
    //bắt đầu
    private void updateTextStart() {
        long minuteStart = TimeUnit.MILLISECONDS.toMinutes((long) currentTime);
        long secondStart = TimeUnit.MILLISECONDS.toSeconds((long) currentTime)
                - TimeUnit.MINUTES.toSeconds(minuteStart);
        txtStartTime.setText(String.format("%d:%d", minuteStart, secondStart));
    }
    //kết thúc đếm thời gian
    private void updateTextFinal() {
        long minuteStart = TimeUnit.MILLISECONDS.toMinutes((long) finalTime);
        long secondStart = TimeUnit.MILLISECONDS.toSeconds((long) finalTime)
                - TimeUnit.MINUTES.toSeconds(minuteStart);
        txtFinalTime.setText(String.format("%d:%d", minuteStart, secondStart));
    }

    // buid MediaPlayer
    private void buidMediaPlayer(final String uriL) {
        mPlayler= MediaPlayer.create(this,Uri.parse(musicPatch));
        mPlayler.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                txtStartTime.setText(String.format("%d:%d", 0, 00));
                    if(!flagReply && !isShuffle){
                        autoNext();
                    }else{
                        if(isShuffle && !flagReply){
                            autoNextRandom();
                        }
                    }
            }
        });

        if(flagReply){
            Repeat();
        }else{
            noRepeat();
        }

    }

    // play music
    private void playMusic() {
        if (!flagPlay) {
            btnPlay.setImageResource(R.drawable.pause);
            finalTime = mPlayler.getDuration();
            updateTextFinal();
            mService.playMusic(mPlayler);
            String txtitem_song;
            //show name
            if(isShuffle)
                txtitem_song = listSongs.get(ranPositon).getMusic_name();
            else
                txtitem_song = listSongs.get(position).getMusic_name();
            songName.setText(txtitem_song);
            seekBar.setMax((int) finalTime);
            myHandler.postDelayed(updateTime, 100);
            iv.startAnimation(animation);
        }
    }

    private void pauseMusic() {
        btnPlay.setImageResource(R.drawable.play);
        mService.pauseMusic(mPlayler);
        myHandler.removeCallbacks(updateTime);
        iv.clearAnimation();

    }
    //reply
    private void Repeat() {
        mPlayler.setLooping(true);

    }

    private void noRepeat() {
        mPlayler.setLooping(false);

    }
    private void moveSong(String uri) {
        mPlayler.release();
        buidMediaPlayer(uri);
        flagPlay = false;
        playMusic();
    }
    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();

    }
    private  void autoNext(){
        //auto next
        if (position < listSongs.size() - 1) {
            position += 1;
            flagReply = false;
            musicPatch = listSongs.get(position).getPatch();
            moveSong(musicPatch);
        }
    }

    private void autoNextRandom(){
        Random random = new Random();
        ranPositon = random.nextInt(listSongs.size());
        musicPatch = listSongs.get(ranPositon).getPatch();
        position = ranPositon;
        moveSong(musicPatch);

        Log.d("test",ranPositon+"");
    }
}
