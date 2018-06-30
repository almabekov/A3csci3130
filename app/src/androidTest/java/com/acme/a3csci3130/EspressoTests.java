package com.acme.a3csci3130;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

public class EspressoTests {
    @Rule
    public ActivityTestRule<MainActivity> myActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);


    //since test of contact creation, update read and delete should be performed in order,
    //and espresso can perform tests in a random order,
    //I decided to put all operations and assertions for CRUD into the one test,
    //To make sure that the order of operations will be followed
    //There should be no contacts in the database before the testing!!!
    @Test
    public void CRUD_test() {
        //CREATE new contact
        onView(withId(R.id.submitButton)).perform(click());
        try {Thread.sleep(1000);}
        catch (Exception e) {System.out.println(e);}
        onView(withId(R.id.name)).perform(typeText("Test1234"));
        onView(withId(R.id.businessNumber)).perform(typeText("987654321"));
        onView(withId(R.id.primaryBusiness)).perform(typeText("Distributor"));
        onView(withId(R.id.province)).perform(typeText("NS"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.submitButton)).perform(click());
        try {Thread.sleep(1000);}
        catch (Exception e) {System.out.println(e);}
        //https://stackoverflow.com/questions/37825896/how-to-select-a-listview-in-espresso
        //basically we need empty list to perform tests
        onData(anything()).atPosition(0).check(matches(withText("Test1234")));

        //UPDATE contact
        try {Thread.sleep(1000);}
        catch (Exception e) {System.out.println(e);}
        onData(anything()).atPosition(0).perform(click());
        try {Thread.sleep(1000);}
        catch (Exception e) {System.out.println(e);}
        onView(withId(R.id.name)).perform(clearText());
        onView(withId(R.id.name)).perform(typeText("Updated Name"),ViewActions.closeSoftKeyboard());
        try {Thread.sleep(1000);}
        catch (Exception e) {System.out.println(e);}
        onView(withId(R.id.updateButton)).perform(click());
        try {Thread.sleep(2000);}
        catch (Exception e) {System.out.println(e);}
        onData(anything()).atPosition(0).check(matches(withText("Updated Name")));
        //READ updated contact details
        try {Thread.sleep(1000);}
        catch (Exception e) {System.out.println(e);}
        //onData(allOf(anything()).withContent("Updated Name")).perform(click());
        onData(anything()).atPosition(0).perform(click());
        try {Thread.sleep(1000);}
        catch (Exception e) {System.out.println(e);}
        onView(withId(R.id.name)).check(matches(withText("Updated Name")));
        onView(withId(R.id.businessNumber)).check(matches(withText("987654321")));
        onView(withId(R.id.primaryBusiness)).check(matches(withText("Distributor")));
        onView(withId(R.id.province)).check(matches(withText("NS")));

        //DELETE contact test
        try {Thread.sleep(1000);}
        catch (Exception e) {System.out.println(e);}
        onView(withId(R.id.deleteButton)).perform(click());
        try {Thread.sleep(1000);}
        catch (Exception e) {System.out.println(e);}
        onView(withId(R.id.listView))
                .check(matches(not(hasDescendant(withText("Updated Name")))));
    }
}
