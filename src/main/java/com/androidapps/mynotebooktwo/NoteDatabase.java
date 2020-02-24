package com.androidapps.mynotebooktwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {


    //Create the database structure
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "notedb";
    private static final String DATABASE_TABLE = "notestable";

    // columns name for the database table
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";


    //The constructor
    NoteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // create the TABLE tableName(id INT PRIMARY KEY, title TEXT, content TEXT, date TEXT, time TEXT);

        String query= "CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ID + " INT PRIMARY KEY, " +
               KEY_TITLE + "TEXT, " +
               KEY_CONTENT +  "TEXT, " +
               KEY_DATE +  "TEXT, " +
               KEY_TIME +  "TEXT " +")";

        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion >= newVersion)

            return;

        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    //Add note method

    public long addNote(Notes notes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, notes.getTitle());
        contentValues.put(KEY_CONTENT, notes.getContent());
        contentValues.put(KEY_DATE, notes.getDate());
        contentValues.put(KEY_TIME, notes.getTime());

    long ID = db.insert(DATABASE_TABLE, null, contentValues);
        Log.d("Inserted db", "ID -> " + ID);

        return ID;
    }

    public Notes getNote(long id){
        // select * from db where id = 1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_TITLE, KEY_CONTENT, KEY_DATE, KEY_TIME}, KEY_ID + "=?",
        new String[] {String.valueOf(id)},null, null, null);
        if(cursor !=null)
            cursor.moveToFirst();

        return new Notes(cursor.getLong(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3),cursor.getString(4));

    }
    // return list of notes
    public List<Notes> getNotes(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Notes> allNotes = new ArrayList<>();
        // select * from db
        String query = "SELECT * FROM " + DATABASE_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Notes notes = new Notes();
                    notes.setID(cursor.getLong(0));
                    notes.setTitle(cursor.getString(1));
                    notes.setContent(cursor.getString(2));
                    notes.setDate(cursor.getString(3));
                    notes.setTime(cursor.getString(4));

                    allNotes.add(notes);

                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return allNotes;
    }
}
