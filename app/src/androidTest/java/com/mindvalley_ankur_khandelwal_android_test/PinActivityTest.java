package com.mindvalley_ankur_khandelwal_android_test;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class PinActivityTest {
    @Rule
    public ActivityTestRule<MyPinActivity> myPinActivityRule = new ActivityTestRule<>(MyPinActivity.class);

    @Test
    public void onViewClick(){
        onView(withText("nature")).perform(click());
        onView(withText("people")).perform(click());
        onView(withText("objects")).perform(click());
    }

    @Test
    public void onRecyclerViewClick(){
        onView(withId(R.id.recyclerStaggered))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
    }

    @Test
    public void onFabClick(){
        onView(withId(R.id.fabUser)).perform(click());
    }
}
