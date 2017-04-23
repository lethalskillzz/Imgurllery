package com.lethalskillzz.mobigur.gallery;

import android.content.pm.ActivityInfo;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.lethalskillzz.imgurllery.R;
import com.lethalskillzz.mobigur.mobigur.presentation.gallery.GalleryActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
/**
 * Created by ibrahimabdulkadir on 22/04/2017.
 */

@RunWith(AndroidJUnit4.class)
public class GalleryActivityOrientationChangeTest {

    private static final String TAG = "OrientationChangeTest";
    @Rule
    public ActivityTestRule<GalleryActivity> mActivityRule = new ActivityTestRule<>(GalleryActivity.class);

    @Test
    public void testRandomBehavior() {
        changeOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        clickOnRandomItem(R.id.gallery_recycler_view);
        onView(withId(R.id.item_image_card)).perform(click());

        pressBack();


        changeOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    }

    private void changeOrientation(int orientation) {
        mActivityRule.getActivity().setRequestedOrientation(orientation);
    }

    private void clickOnRandomItem(int viewId) {
        int x = getRandomRecyclerPosition(viewId);

        Log.d(TAG, "clickOnRandomItem: " + x);

        onView(withId(viewId))
                .perform(RecyclerViewActions.scrollToPosition(x), RecyclerViewActions.actionOnItemAtPosition(x, click()));
    }

    private int getRandomRecyclerPosition(int recyclerId) {
        Random ran = new Random();
        RecyclerView recyclerView = (RecyclerView) mActivityRule.getActivity().findViewById(recyclerId);

        int n = (recyclerView == null || recyclerView.getAdapter().getItemCount() <= 0)
                ? 1
                : recyclerView.getAdapter().getItemCount() / 2;

        int next;

        Log.d(TAG, "getRandomRecyclerPosition: " + n);

        next = (n != 0)
                ? ran.nextInt(n)
                : 0;

        return next;
    }

}
