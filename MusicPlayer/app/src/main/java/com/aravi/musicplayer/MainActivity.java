package com.aravi.musicplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public Button playbutton;
    public SeekBar mnact_range,mnact_sound;
    public MediaPlayer mediaPlayer;
    public TextView strttmng,remngtmng;
    private int totaltime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playbutton=(Button)findViewById(R.id.playbtn);
        mnact_range=(SeekBar)findViewById(R.id.seekbar_range);
        mnact_sound=(SeekBar)findViewById(R.id.seekbar_sound);
        strttmng=(TextView)findViewById(R.id.startingtime);
        remngtmng=(TextView)findViewById(R.id.remaininingtime);
        mediaPlayer=MediaPlayer.create(MainActivity.this, R.raw.anaganaganaga);
      //  mediaPlayer=MediaPlayer.create(MainActivity.this, R.raw.files);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        mediaPlayer.setVolume(0.5f,0.5f);
        totaltime=mediaPlayer.getDuration();
        mnact_range.setMax(totaltime);
        mnact_range.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mediaPlayer.seekTo(progress);
                    mnact_range.setProgress(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mnact_sound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volumenumber=progress/50f;
                mediaPlayer.setVolume(volumenumber,volumenumber);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer!=null){
                    try {

                        Message message=new Message();
                        message.what=mediaPlayer.getCurrentPosition();
                        handler.sendMessage(message);
                        Thread.sleep(1000);

                    }
                    catch (InterruptedException e){

                    }
                }
            }
        });
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int currentposition=msg.what;
            mnact_range.setProgress(currentposition);
            String elaspedTime=createtimelable(currentposition);
            strttmng.setText(elaspedTime);

            String remaingtm=createtimelable(totaltime-currentposition);
            remngtmng.setText("-"+remaingtm);
        }
    };

    private String createtimelable(int time){
        String timelable="";
        int min=time/1000/60;
        int sec=time/1000%60;

        timelable=min+":";
        if (sec<10){
            timelable+="0";
            timelable+=sec;

        }

        return timelable;
    }

    public void playbutton(View view) {
        if (!mediaPlayer.isPlaying()){
            mediaPlayer.start();
            playbutton.setBackgroundResource(R.drawable.soundmode);
        }
        else {
            mediaPlayer.pause();
            playbutton.setBackgroundResource(R.drawable.pausebutton);
        }
    }
}
