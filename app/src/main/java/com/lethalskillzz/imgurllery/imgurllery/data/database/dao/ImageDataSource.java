package com.lethalskillzz.imgurllery.imgurllery.data.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lethalskillzz.imgurllery.imgurllery.data.database.DatabaseHelper;
import com.lethalskillzz.imgurllery.imgurllery.data.model.Image;

import java.util.ArrayList;
import java.util.List;

import static com.lethalskillzz.imgurllery.imgurllery.data.database.DatabaseHelper.COLUMN_COVER;
import static com.lethalskillzz.imgurllery.imgurllery.data.database.DatabaseHelper.COLUMN_DATE_TIME;
import static com.lethalskillzz.imgurllery.imgurllery.data.database.DatabaseHelper.COLUMN_DESCRIPTION;
import static com.lethalskillzz.imgurllery.imgurllery.data.database.DatabaseHelper.COLUMN_DOWNS;
import static com.lethalskillzz.imgurllery.imgurllery.data.database.DatabaseHelper.COLUMN_ID;
import static com.lethalskillzz.imgurllery.imgurllery.data.database.DatabaseHelper.COLUMN_IS_ALBUM;
import static com.lethalskillzz.imgurllery.imgurllery.data.database.DatabaseHelper.COLUMN_SCORE;
import static com.lethalskillzz.imgurllery.imgurllery.data.database.DatabaseHelper.COLUMN_TITLE;
import static com.lethalskillzz.imgurllery.imgurllery.data.database.DatabaseHelper.COLUMN_UPS;
import static com.lethalskillzz.imgurllery.imgurllery.data.database.DatabaseHelper.TABLE_IMAGE;

/**
 * Created by ibrahimabdulkadir on 17/04/2017.
 */

public class ImageDataSource {


    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = { COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_DATE_TIME,
            COLUMN_COVER, COLUMN_UPS, COLUMN_DOWNS, COLUMN_SCORE, COLUMN_IS_ALBUM};

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
    public Image createUser(Image image) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, image.getId());
        values.put(DatabaseHelper.COLUMN_TITLE, image.getTitle());
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, image.getDescription());
        values.put(DatabaseHelper.COLUMN_DATE_TIME, image.getDatetime());
        values.put(DatabaseHelper.COLUMN_COVER, image.getCover());
        values.put(DatabaseHelper.COLUMN_UPS, image.getUps());
        values.put(DatabaseHelper.COLUMN_DOWNS, image.getDowns());
        values.put(DatabaseHelper.COLUMN_SCORE, image.getScore());
        values.put(DatabaseHelper.COLUMN_IS_ALBUM, image.getIsAlbum());

        // insert row
        database.insert(DatabaseHelper.TABLE_IMAGE, COLUMN_ID, values);

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

        String whereClause = COLUMN_ID + " LIKE ?";
        String [] whereArgs = {"%"+id+"%"};
        List<Image> images = new ArrayList<>();

        Cursor cursor = database.query(TABLE_IMAGE,
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
    public long deleteUserItem(Image image) {
        String id = image.getId();
        return database.delete(DatabaseHelper.TABLE_IMAGE, COLUMN_ID
                + " = " + id, null);
    }

    /**
     * Clear All Images
     */
    public void clearAllUser() {
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
