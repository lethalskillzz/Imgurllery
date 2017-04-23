package com.lethalskillzz.mobigur.mobigur.data.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lethalskillzz.mobigur.mobigur.data.database.DatabaseHelper;
import com.lethalskillzz.mobigur.mobigur.data.model.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibrahimabdulkadir on 17/04/2017.
 */

public class ImageDataSource {


    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = { DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_TITLE,
            DatabaseHelper.COLUMN_DESCRIPTION, DatabaseHelper.COLUMN_DATE_TIME,
            DatabaseHelper.COLUMN_COVER, DatabaseHelper.COLUMN_UPS, DatabaseHelper.COLUMN_DOWNS,
            DatabaseHelper.COLUMN_SCORE, DatabaseHelper.COLUMN_IS_ALBUM};


    public ImageDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }


    public void close() {
        dbHelper.close();
    }


    /**
     * Check if Images are empty
     */
    public boolean isImageEmpty() {

        boolean isEmpty = true;
        Cursor cursor = database.query(DatabaseHelper.TABLE_IMAGE,
                allColumns, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            isEmpty = false;
        }
        cursor.close();

        return isEmpty;
    }



    /**
     * Creating new Images
     */
    public Image createImage(Image image) {

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID, image.getId());
        values.put(DatabaseHelper.COLUMN_TITLE, image.getTitle());
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, image.getDescription());
        values.put(DatabaseHelper.COLUMN_DATE_TIME, image.getDatetime());
        values.put(DatabaseHelper.COLUMN_COVER, image.getCover());
        values.put(DatabaseHelper.COLUMN_UPS, image.getUps());
        values.put(DatabaseHelper.COLUMN_DOWNS, image.getDowns());
        values.put(DatabaseHelper.COLUMN_SCORE, image.getScore());
        values.put(DatabaseHelper.COLUMN_IS_ALBUM, image.getIsAlbum());

        // insert row
        database.insert(DatabaseHelper.TABLE_IMAGE, DatabaseHelper.COLUMN_ID, values);

        return image;
    }


    /**
     * Read All Images
     */
    public List<Image> readAllImages() {

        String whereClause = null;
        String[] whereArgs = null;
        List<Image> images = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_IMAGE,
                allColumns, whereClause, whereArgs, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Image image = cursorToImage(cursor);
            images.add(image);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return images;
    }


    /**
     * Read Image by Id
     */
    public List<Image> readImageById(String id) {

        String whereClause = DatabaseHelper.COLUMN_ID + " LIKE ?";
        String [] whereArgs = {"%"+id+"%"};
        List<Image> images = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_IMAGE,
                allColumns,whereClause, whereArgs, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Image image = cursorToImage(cursor);
            images.add(image);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return images;
    }



    /**
     * Delete Single Image
     */
    public long deleteSingleImage(Image image) {
        String id = image.getId();
        return database.delete(DatabaseHelper.TABLE_IMAGE, DatabaseHelper.COLUMN_ID
                + " = " + id, null);
    }

    /**
     * Clear All Images
     */
    public void clearAllImages() {
        database.delete(DatabaseHelper.TABLE_IMAGE, null, null);
    }



    private Image cursorToImage(Cursor cursor) {
        Image image = new Image();
        image.setId(cursor.getString(0));
        image.setTitle(cursor.getString(1));
        image.setDescription(cursor.getString(2));
        image.setDatetime(cursor.getString(3));
        image.setCover(cursor.getString(4));
        image.setUps(cursor.getInt(5));
        image.setDowns(cursor.getInt(6));
        image.setScore(cursor.getInt(7));
        image.setIsAlbum(cursor.getInt(8)==1);

        return image;
    }

}
