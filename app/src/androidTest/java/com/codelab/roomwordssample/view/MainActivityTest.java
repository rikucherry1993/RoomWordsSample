package com.codelab.roomwordssample.view;

import android.content.Intent;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.codelab.roomwordssample.R;
import com.codelab.roomwordssample.base.MyTestBase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends MyTestBase {

    @Rule
    public IntentsTestRule<MainActivity> SUT =  new IntentsTestRule(MainActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        //TODO insert values into room database
    }

    //Activity migration
    //01.clickOn_Item_moveTo_newWordActivity
    public void ClickOn_Item_moveTo_newWordActivity() throws Exception {
        //Arrange
        launchActivityWithIntent();
        //Act
        onPerformActionOnItemAtPosition(R.id.recyclerview, 0);
        //Assert
        assertStartActivity(NewWordActivity.class.getName());
    }

    //List operation
    //03.swipeTo_left_delete_word
    //04.swipeTo_right_delete_word
    //05.clickOn_settings_sortByTimeDesc
    //06.clickOn_settings_sortByIdAsc
    //07.clickOn_settings_sortByWordAsc

    private void launchActivityWithIntent() {
        Intent intent = new Intent();
        SUT.launchActivity(intent);
    }



}