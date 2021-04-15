package com.codelab.roomwordssample.view;

import android.content.Context;
import android.content.Intent;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.codelab.roomwordssample.R;
import com.codelab.roomwordssample.base.MyTestBase;
import com.codelab.roomwordssample.room.Word;
import com.codelab.roomwordssample.room.WordDao;
import com.codelab.roomwordssample.room.WordRoomDatabase;
import com.codelab.roomwordssample.util.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends MyTestBase {

    private WordDao dao;
    private WordRoomDatabase database;

    @Rule
    public IntentsTestRule<MainActivity> SUT =  new IntentsTestRule(MainActivity.class, true, false);

    @Before
    public void setUp(){
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.databaseBuilder(context, WordRoomDatabase.class,"word_database").build();
        //Todo: seems like inMemoryDatabaseBuilder cannot work with UI test?
        dao = database.wordDao();
        //note: use idling resource
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource);
    }

    //Activity migration
    //01.clickOn_Item_moveTo_newWordActivity
    @Test
    public void ClickOn_Item_moveTo_newWordActivity() throws Exception {
        //Arrange
        insertData("case01");
        SUT.launchActivity(new Intent());
        //Act
        onPerformActionOnItemAtPosition(R.id.recyclerview, 0, click());
        //Assert
        assertStartActivity(NewWordActivity.class.getName());
    }

    //List operation
    //02.swipeTo_left_delete_word
    @Test
    public void SwipeTo_left_delete_word() throws Exception {
        //Arrange
        insertData("case02");
        SUT.launchActivity(new Intent());
        //Act
        //onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        onPerformActionOnItemAtPosition(R.id.recyclerview,0,swipeLeft());
        //Assert-UI
        assertHasNoDescendant(R.id.recyclerview,"case02");
        //Assert-db
        Word[] word = dao.getWordByName("case02");
        assertThat(word.length, equalTo(0));
    }
    //03.swipeTo_right_delete_word
    //04.clickOn_settings_sortByTimeDesc
    //05.clickOn_settings_sortByIdAsc
    //06.clickOn_settings_sortByWordAsc

    @After
    public void closeDb() throws IOException {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource);
        database.clearAllTables();
        database.close();
    }

    private void insertData(String testWord){
        Word word = new Word(testWord,System.currentTimeMillis());
        dao.insert(word);
    }



}