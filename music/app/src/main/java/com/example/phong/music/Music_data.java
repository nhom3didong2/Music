package com.example.phong.music;

/**
 * Created by phong on 12/5/2016.
 */

public class Music_data {
    private int Music_id;
    private String Music_name;

    public  Music_data(){
        this.Music_id = -1;
        this.Music_name = "Unknow";
    }
    public Music_data(int Music_id, String Music_name) {
        this.Music_id = Music_id;
        this.Music_name = Music_name;
    }

    public int getMusic_id() {
        return Music_id;
    }

    public void setMusic_id(int music_id) {
        Music_id = music_id;
    }

    public String getMusic_name() {
        return Music_name;
    }

    public void setMusic_name(String music_name) {
        Music_name = music_name;
    }
}

