package com.example.phong.music;

/**
 * Created by phong on 12/8/2016.
 */
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class Database extends SQLiteOpenHelper{
    static int DB_VERSION = 1;
    static String DB_NAME = "music.db";
    static SQLiteDatabase db = null;
    Context context;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String sqlQuery1 = "CREATE TABLE music(Music_patch TEXT PRIMARY KEY, Music_name TEXT, Music_folderName)";
        db.execSQL(sqlQuery1);
    }

    boolean isDatabaseExist() {
        return (db != null);
    }

    void createOrOpenDatabase() {
        // if(!isDatabaseExist()){
        db = getWritableDatabase();
        // Log.d("test", "create DB");
        // }
    }
    //write folder, update and delete,find,read
//    long writeFolder(Music folder) {
//        ContentValues values = new ContentValues();
//        values.put("Folder_id", folder.getFolder_id());
//        values.put("Folder_name", folder.getFolder_name());
//        long ok = db.insert("folder", null, values);
//        if (ok == -1) {
//            Toast.makeText(
//                    context,
//                    "The folder: " + folder.getFolder_id()
//                            + " is exist on the Database", Toast.LENGTH_LONG)
//                    .show();
//        }
//        // Log.d("test", "insert "+ok);
//        return ok;
//    }
//
//    public void writeAFolder(Music folder) {
//        createOrOpenDatabase();
//        writeFolder(folder);
//        this.close();
//    }
//    public void writeFolders(ArrayList<Music> folders) {
//        createOrOpenDatabase();
//        boolean ok = true;
//
//        for (Music afolder : folders) {
//            long position = writeFolder(afolder);
//            if (position == -1)
//                ok = false;
//        }
//
//        if (ok) {
//            Toast.makeText(context,
//                    "All of students are saved on the Database",
//                    Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(
//                    context,
//                    "Some of students are saved on the Database, some are not!",
//                    Toast.LENGTH_LONG).show();
//        }
//
//        this.close();
//    }
//    public long updateFolder(Music old, Music newvalue) {
//        createOrOpenDatabase();
//        ContentValues values = new ContentValues();
//        values.put("Folder_id", newvalue.getFolder_id());
//        values.put("Folder_name", newvalue.getFolder_name());
//        long ok = db.update("folder", values, "Folder_id = ?",
//                new String[] { old.getFolder_id() });
//        if (ok != -1) {
//            Toast.makeText(context, "The folder is updated on the Database",
//                    Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(context,
//                    "The folder is NOT updated on the Database",
//                    Toast.LENGTH_LONG).show();
//        }
//        this.close();
//        return ok;
//    }
//
//    public void deleteFolder(String Folder_id) {
//        createOrOpenDatabase();
//        db.delete("music", "Folder_id = ?", new String[] {  Folder_id });
//        this.close();
//    }
    /*public Music findFolder(Music folder) {
        Music mFolder = new Music();
        createOrOpenDatabase();

        String query = "Select * from folder where Folder_id =" + "\""
                + folder.getFolder_id() + "\"";
        Cursor cur = db.rawQuery(query, null);
        if (cur.moveToFirst()) {
            mFolder.setFolder_id(cur.getString(cur.getColumnIndex("Folder_id")));
            mFolder.setFolder_name(cur.getString(cur.getColumnIndex("Folder_name")));
        }

        this.close();
        return mFolder;
    }

    public void readFolders(ArrayList<Music> folders) {
        createOrOpenDatabase();
        String query = "Select * from folder";
        Cursor cur = db.rawQuery(query, null);
        if (cur.moveToFirst()) {
            do {
                Music mFolder = new Music();
                mFolder.setFolder_id(cur.getString(cur
                        .getColumnIndex("Folder_id")));
                mFolder.setFolder_name(cur.getString(cur
                        .getColumnIndex("Folder_name")));
                folders.add(mFolder);
            } while (cur.moveToNext());
        }

        this.close();
    }*/
    //write music, update and delete and find and read
    long writeMusic(Music music) {
        this.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        values.put("Music_patch", music.getPatch());
        values.put("Music_name", music.getMusic_name());
        values.put("Music_folderName",music.getFolder_name());
        long ok = db.insert("music", null, values);
        return ok;
    }


    public void writeMusics(ArrayList<Music> musics) {
        createOrOpenDatabase();
        boolean ok = true;

        for (Music amusic : musics) {
            long position = writeMusic(amusic);
            if (position == -1)
                ok = false;
        }

        if (ok) {
            Toast.makeText(context,
                    "All of Songs are saved on the Database",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(
                    context,
                    "Some of Songs are saved on the Database, some are not!",
                    Toast.LENGTH_LONG).show();
        }

        this.close();
    }

    public void readMusics(ArrayList<Music> musics) {
        createOrOpenDatabase();
        String query = "select * from music"; /// vkl vkl

        Cursor cur = db.rawQuery(query, null);
      //  Toast.makeText(context,cur.toString(),Toast.LENGTH_LONG).show();
        if (cur.moveToFirst()) {
            do {
                Music mMusic = new Music();
                mMusic.setPatch(cur.getString(cur
                        .getColumnIndex("Music_patch")));
                mMusic.setMusic_name(cur.getString(cur
                        .getColumnIndex("Music_name")));
                musics.add(mMusic);    //fuck

            } while (cur.moveToNext());

        }
       // Toast.makeText(context,"null",Toast.LENGTH_LONG).show();
        this.close();
    }
    public void deleteMusic(String patch) {
        createOrOpenDatabase();
        db.delete("music", "Music_patch = ?", new String[] { patch });
        this.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }
}
