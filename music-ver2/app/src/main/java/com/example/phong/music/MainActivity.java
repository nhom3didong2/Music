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
    //private boolean isShuffle= false;
    private float currentTime = 0;
    private float finalTime = 0;
    private Handler myHandler = new Handler();
    private SeekBar seekBar;
    private Button btnSearch;
    private ImageView back;
    private ImageButton btnPlay;
    private ImageButton btnRepeat;
    //private ImageButton btnShuffle;
    private TextView txtStartTime;
    private TextView txtFinalTime;
   // private ArrayList<File> arrSongFile;
    private int position = 0;
    private TextView songName;
    private ImageView iv;
    private Animation animation;
    String musicPatch;
    private ArrayList<Music> listSongs ;
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

        final Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animfadeout);
        final ImageView iv = (ImageView) findViewById(R.id.ivMidImage);
        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate2);
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
//        //xử lý việc hát đè lên nhau
        if(mPlayler!=null){
            mPlayler.stop();
            mPlayler.release();
        }
        //truyền dữ liệu
        listSongs = new ArrayList<Music>();
        Intent i = getIntent();
        if (i != null) {
            Bundle b = i.getExtras();
            position = b.getInt("position");
        }

        Database db = new Database(this);
        db.readMusics(listSongs);

        musicPatch = listSongs.get(position).getPatch();

        txtStartTime = (TextView) findViewById(R.id.txtStart);
        txtFinalTime = (TextView) findViewById(R.id.txtEnd);
        Intent intent = new Intent(getApplicationContext(), MusicService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

        //show name
        songName = (TextView) findViewById(R.id.txtitem_song);
        Log.d("test",musicPatch);
        //mPlayler = new MediaPlayer();

          buidMediaPlayer(musicPatch);
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
        //phát nhạc mặc định
        mPlayler.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                flagPlay = false;
               // btnPlay.setImageResource(R.drawable.play_1);
                myHandler.removeCallbacks(updateTime);
                txtStartTime.setText(String.format("%d:%d", 0, 00));
                    if (position < listSongs.size() - 1) {
                        position += 1;
                        musicPatch = listSongs.get(position).getPatch();
                        moveSong(musicPatch);
                    }
            }
        });
        playMusic();
        /*//shuffle
        btnShuffle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(isShuffle == false){
                   noShuffle();
                }else{
                    Shuffle();
                }
            }
        });*/
        //reply
       btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flagReply==true) {
                    Repeat();
                } else{
                    noRepeat();
                }
            }
        });
        //play
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flagPlay) {
                    playMusic();
                } else {
                    pauseMusic();
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
                if (position < listSongs.size() - 1) {
                    position += 1;
                    musicPatch = listSongs.get(position).getPatch();
                    moveSong(musicPatch);
                } else {
                    musicPatch = listSongs.get(0).getPatch();
                    position = 0;
                    moveSong(musicPatch);
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
    private void buidMediaPlayer(String uriL) {
        mPlayler= MediaPlayer.create(this,Uri.parse(musicPatch));
        mPlayler.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                flagPlay = false;
              //  btnPlay.setImageResource(R.drawable.play_1);
                myHandler.removeCallbacks(updateTime);
                txtStartTime.setText(String.format("%d:%d", 0, 00));
                //auto next
                    if (position < listSongs.size() - 1) {
                        position += 1;
                        musicPatch = listSongs.get(position).getPatch();
                        moveSong(musicPatch);
                    }
            }
        });
    }

    // play music
    private void playMusic() {
        if (!flagPlay) {
            btnPlay.setImageResource(R.drawable.pause_1);
            finalTime = mPlayler.getDuration();
            updateTextFinal();
            flagPlay = true;
            mService.playMusic(mPlayler);
            //show name
            String txtitem_song = listSongs.get(position).getMusic_name();
            songName.setText(txtitem_song);
            seekBar.setMax((int) finalTime);
            myHandler.postDelayed(updateTime, 100);
        }
    }

    private void pauseMusic() {
        mService.pauseMusic(mPlayler);
        btnPlay.setImageResource(R.drawable.play_1);
        flagPlay = false;
        myHandler.removeCallbacks(updateTime);
    }
    /*//shuffle
    private void noShuffle() {
        if (flagReply == false) {
            btnShuffle.setImageResource(R.drawable.noshuffle);
            if (position < arrSongFile.size() - 1) {
                position += 1;
                uri = Uri.parse(arrSongFile.get(position).toString());
                moveSong(uri);
                playMusic(position);
            }
        }
    }

    private void Shuffle() {
        if (isShuffle == true){
            btnShuffle.setImageResource(R.drawable.shuffle);
            Random random = new Random();
            position = random.nextInt((arrSongFile.size() - 1) - 0 + 1 ) + 0;
            playMusic(position);
        }
    }*/
    //reply
    private void Repeat() {
        if (flagReply==true) {
            btnRepeat.setImageResource(R.drawable.repeat_1);
            playMusic();
        }
    }

    private void noRepeat() {
        if (flagReply) {
            if (position < listSongs.size() - 1) {
                btnRepeat.setImageResource(R.drawable.norepeat);
                position += 1;
                musicPatch = listSongs.get(position).getPatch();
                moveSong(musicPatch);
            }
        }
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
}
