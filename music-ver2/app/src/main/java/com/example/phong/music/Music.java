package com.example.phong.music;

/**
 * Created by phong on 12/5/2016.
 */

public class Music {
    private String Music_name;
    private String Folder_name;
    private String patch;

    public Music(){
        this.Music_name = "unKnow";
        this.Folder_name = "default";
        this.patch = null;
    }

    public Music(String Music_name, String Folder_id, String patch) {
        this.patch = patch;
        this.Music_name = Music_name;
        this.Folder_name = Folder_id;
    }


    public String getMusic_name() {
        return Music_name;
    }

    public void setMusic_name(String music_name) {
        Music_name = music_name;
    }

    public String getFolder_name() {
        return Folder_name;
    }

    public void setFolder_id(String folder_name) {
        Folder_name = folder_name;
    }

    public String getPatch() {
        return patch;
    }

    public void setPatch(String patch) {
        this.patch = patch;
    }
}

