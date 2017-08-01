package com.lethalskillzz.mobigur.gallery;

import android.graphics.drawable.ColorDrawable;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.lethalskillzz.imgurllery.R;
import com.lethalskillzz.mobigur.mobigur.presentation.gallery.GalleryActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;

/**
 * Created by ibrahimabdulkadir on 23/04/2017.
 */

@RunWith(AndroidJUnit4.class)
public class GalleryActivtyTest {

    @Rule
    public ActivityTestRule activityTestRule =
            new ActivityTestRule<>(GalleryActivity.class);

    @Test
    public void testToolbarDesign() {

        onView(withId(R.id.gallery_toolbar)).check(matches(isDisplayed()));

        onView(withText(R.string.app_name)).check(matches(withParent(withId(R.id.gallery_toolbar))));

        onView(withId(R.id.gallery_toolbar)).check(matches(withToolbarBackGroundColor()));
    }


    private Matcher<? super View> withToolbarBackGroundColor() {
        return new BoundedMatcher<View, View>(View.class) {
            @Override
            public boolean matchesSafely(View view) {
                final ColorDrawable buttonColor = (ColorDrawable) view.getBackground();

                return ContextCompat
                        .getColor(activityTestRule.getActivity(), R.color.colorPrimary) ==
                        buttonColor.getColor();
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }



    @Test
    public void testSideNavigation() {

        // Click on the hamburger menu
        onView(androidHomeMatcher()).perform(click());

        // Check that the navigation drawer is now displayed
        onView(withId(R.id.gallery_drawer_layout)).check(matches(isDisplayed()));

    }

    public static Matcher<View> androidHomeMatcher() {
        return allOf(
                withParent(withClassName(is(Toolbar.class.getName()))),
                withClassName(anyOf(
                        is(ImageButton.class.getName()),
                        is(AppCompatImageButton.class.getName())
                )));
    }
}
