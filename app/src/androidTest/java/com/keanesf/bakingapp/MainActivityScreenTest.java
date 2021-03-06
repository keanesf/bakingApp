/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.keanesf.bakingapp;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static android.view.View.VISIBLE;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.AllOf.allOf;

import android.support.design.widget.AppBarLayout;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.keanesf.bakingapp.activties.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.contrib.RecyclerViewActions;

/**
 * This test demos a user clicking on a GridView item in MenuActivity which opens up the
 * corresponding RecipeDetailActivity.
 *
 * This test does not utilize Idling Resources yet. If idling is set in the MenuActivity,
 * then this test will fail. See the IdlingResourcesTest for an identical test that
 * takes into account Idling Resources.
 */


@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    public static final String RECIPE_NAME = "Nutella Pie";

    public static final String RECIPE_NAME2 = "Cheesecake";

    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested will be launched before each test that's annotated
     * with @Test and before methods annotated with @Before. The activity will be terminated after
     * the test and methods annotated with @After are complete. This rule allows you to directly
     * access the activity during the test.
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Clicks on a Scroll item and checks it opens up the RecipeDetailActivity with the correct details.
     */
    @Test
    public void clickScrollViewItem_OpensRecipeDetailActivity() {

        onView(ViewMatchers.withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, scrollTo()));
        onView(withText(RECIPE_NAME)).perform(click());
        onView(withId(R.id.title_text_view)).check(matches(withText(RECIPE_NAME)));

    }

    @Test
    public void clickOnLastRecipe_LoadFirstStep() {
        // Scroll to 4th recipe and click it
        onView(ViewMatchers.withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(3, scrollTo()));
        onView(withText(RECIPE_NAME2)).perform(click());

        // In the new activity, click on the first step of the recipe
        onView(withId(R.id.step_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Check if description matches expected test
        onView(withId(R.id.step_long_description)).check(matches(withText("Recipe Introduction")));

    }

}