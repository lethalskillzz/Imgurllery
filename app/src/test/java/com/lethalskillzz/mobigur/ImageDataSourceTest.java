package com.lethalskillzz.mobigur;

import android.content.Context;

import com.lethalskillzz.imgurllery.BuildConfig;
import com.lethalskillzz.mobigur.mobigur.data.database.dao.ImageDataSource;
import com.lethalskillzz.mobigur.mobigur.data.model.Image;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by ibrahimabdulkadir on 22/04/2017.
 */


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class ImageDataSourceTest {

    private ImageDataSource dao;
    private Image imageReal;

    @Before
    public void setup() {
        Context context = RuntimeEnvironment.application;
        dao = new ImageDataSource(context);
        dao.open();
    }


    @After
    public void cleanup(){
        dao.close();
    }


    private void setupImage() {
        imageReal = new Image();
        imageReal.setId("745Wn");
        imageReal.setTitle("Possibly a repost but this is just so pure.");
        imageReal.setDescription("My girlfriend is always home first, " +
                "and I just want to impress her in the bedroom. don't worry this will die in USERSUB since its OC");
        imageReal.setDatetime("1492804989");
        imageReal.setCover("RV7624q");
        imageReal.setUps(314);
        imageReal.setDowns(24);
        imageReal.setScore(1612);
        imageReal.setIsAlbum(true);
    }

    /**
     * Testing Create Image
     */
    @Test
    public void createImageTest() {
        setupImage();
        Image imageInserted = dao.createImage(imageReal);
        assertNotNull(imageInserted);
        assertEquals(imageReal.getId(), imageInserted.getId());
    }



    /**
     * Testing Read All Images
     */
    @Test
    public void readAllImageTest() {
        setupImage();
        Image imageInserted = dao.createImage(imageReal);

        List<Image> images = dao.readAllImages();
        for(Image user : images) {
            assertNotNull(user);
            assertEquals(user.getId(), imageInserted.getId());
        }
    }



    /**
     * Testing Read Image by Id
     */
    @Test
    public void readImageByIdTest() {
        setupImage();
        Image imageInserted = dao.createImage(imageReal);

        Image image = dao.readImageById(imageInserted.getId());
        assertNotNull(image);
        assertEquals(image.getId(), imageInserted.getId());
    }



    /**
     * Testing Delete Image
     */
    @Test
    public void deleteImageTest() {
        setupImage();
        Image imageInserted = dao.createImage(imageReal);

        long delResult = dao.deleteImage( imageInserted );
        assertEquals(1, delResult);
    }
}
