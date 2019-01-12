package com.eccard.frontend;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class JokeActivityTest {

    @Rule
    public IntentsTestRule mActivityTestRule =
            new IntentsTestRule<>(JokeActivity.class, false, false);


    @Test
    public void joke_passed_as_Intent_is_Displayed() {

        String joke = "If you saw this sentence passed the test";
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_TEXT, joke);

        mActivityTestRule.launchActivity(intent);

        onView(withId(R.id.textView_joke)).check(matches(withText(joke)));
    }

}