package com.jordic.bathtubtest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jordic.bathtubtest.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
/**
 * Created by Jordi on 02/01/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void coldTapClickTest()
    {
        int waitingTime = 3 * 1000; //Three seconds

        //Starts Cold Tap
        onView(withId(R.id.coldTapButton)).perform(click());

        try {
            Thread.sleep(waitingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Stop
        onView(withId(R.id.coldTapButton)).perform(click());

        onView(withId(R.id.litersTextView)).check(matches(withText("0.6/150")));
        onView(withId(R.id.temperatureTextView)).check(matches(withText("10º")));

    }

    @Test
    public void hotTapClickTest()
    {
        int waitingTime =  1000; //One seconds

        //Starts Hot Tap
        onView(withId(R.id.hotTapButton)).perform(click());

        try {
            Thread.sleep(waitingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Stop
        onView(withId(R.id.hotTapButton)).perform(click());

        onView(withId(R.id.litersTextView)).check(matches(withText("0.17/150")));
        onView(withId(R.id.temperatureTextView)).check(matches(withText("50º")));
    }




}
