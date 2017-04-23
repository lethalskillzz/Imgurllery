package com.lethalskillzz.mobigur;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lethalskillzz.imgurllery.BuildConfig;
import com.lethalskillzz.mobigur.mobigur.data.database.DatabaseHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.junit.Assert.assertThat;

/**
 * Created by lethalskillzz on 22/04/2017.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class DatabaseHelperTest {

    private Context context;
    private DatabaseHelper helper;
    private SQLiteDatabase db;



    @Before
    public void setup(){
        context = RuntimeEnvironment.application;
        helper = new DatabaseHelper(context);
        db = helper.getReadableDatabase();
    }

    @After
    public void cleanup(){
        db.close();
    }

    @Test
    public void testDBCreated(){
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
        // Verify is the DB is opening correctly
        assertTrue("DB didn't open", db.isOpen());
    }


    @Test
    public void testImageTableCols() {
        Cursor c = db.query(DatabaseHelper.TABLE_IMAGE, null, null, null, null, null, null);
        assertNotNull( c );

        String[] cols = c.getColumnNames();
        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_ID,
                cols, hasItemInArray(DatabaseHelper.COLUMN_ID));

        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_TITLE,
                cols, hasItemInArray(DatabaseHelper.COLUMN_TITLE));

        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_DESCRIPTION,
                cols, hasItemInArray(DatabaseHelper.COLUMN_DESCRIPTION));

        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_DATE_TIME,
                cols, hasItemInArray(DatabaseHelper.COLUMN_DATE_TIME));

        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_COVER,
                cols, hasItemInArray(DatabaseHelper.COLUMN_COVER));

        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_UPS,
                cols, hasItemInArray(DatabaseHelper.COLUMN_UPS));

        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_DOWNS,
                cols, hasItemInArray(DatabaseHelper.COLUMN_DOWNS));

        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_SCORE,
                cols, hasItemInArray(DatabaseHelper.COLUMN_SCORE));

        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_IS_ALBUM,
                cols, hasItemInArray(DatabaseHelper.COLUMN_IS_ALBUM));

        c.close();
    }


    @Test
    public void testDBDelete(){
        assertTrue(context.deleteDatabase(DatabaseHelper.DATABASE_NAME));
    }

}