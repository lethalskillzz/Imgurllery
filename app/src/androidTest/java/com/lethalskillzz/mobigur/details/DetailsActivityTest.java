package com.lethalskillzz.mobigur.details;

import android.graphics.drawable.ColorDrawable;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.lethalskillzz.imgurllery.R;
import com.lethalskillzz.mobigur.mobigur.presentation.detail.DetailsActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by ibrahimabdulkadir on 23/04/2017.
 */

@RunWith(AndroidJUnit4.class)
public class DetailsActivityTest {

    @Rule
    public ActivityTestRule activityTestRule =
            new ActivityTestRule<>(DetailsActivity.class);

    @Test
    public void testToolbarDesign() {
        onView(withId(R.id.details_toolbar)).check(matches(isDisplayed()));

        onView(withText(R.string.title_activity_about)).check(matches(withParent(withId(R.id.details_toolbar))));

        onView(withId(R.id.details_toolbar)).check(matches(withToolbarBackGroundColor()));
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

}
