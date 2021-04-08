package com.codelab.roomwordssample.base;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables;
import androidx.test.espresso.intent.Checks;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.codelab.roomwordssample.match.ColorMatcher.withTextColor;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

public class MyTestBase implements CommonDatabase, CommonPerform, CommonAssert {

    /**
     * get current activity
     */
    public Activity getCurrentActivity() throws Throwable {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        final Activity[] activity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                java.util.Collection activities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                activity[0] = (Activity) Iterables.getOnlyElement(activities);
            }
        });
        return activity[0];
    }

    /**
     * swipe to left
     */
    public void leftSwipe(int id) throws InterruptedException {
        onView(withId(id)).perform(swipeLeft());

    }

    /**
     * swipe to right
     */
    public void rightSwipe(int id) throws InterruptedException {
        onView(withId(id)).perform(swipeRight());
    }


    /**
     * scroll down
     */
    public void downScroll(int id) throws InterruptedException {
        onView(withId(id)).perform(scrollTo());
    }


    /**
     * perform click list View item
     *
     * @param listViewId
     * @param touchViewId
     * @param position
     */
    protected void clickListViewByPosition(int listViewId, int touchViewId, int position) throws Throwable {
        onData(anything()).inAdapterView(withId(listViewId))
                .atPosition(position)
                .onChildView(withId(touchViewId))
                .perform(click());
    }

    /**
     * check listView item text
     *
     * @param listViewId
     * @param position
     * @param target
     * @param result
     * @throws Throwable
     */
    protected void checkListViewByPosition(int listViewId, int position, int target, String result) throws Throwable {
        onData(anything()).inAdapterView(withId(listViewId))
                .atPosition(position)
                .onChildView(withId(target))
                .check(matches(withText(result)));
    }

    /**
     * check text
     *
     * @param id
     * @param text
     * @throws Throwable
     */
    protected void checkTextById(int id, final String text) throws Throwable {
        onView(withId(id)).check(matches(withText(text)));
    }

    /**
     * check hint
     *
     * @param id
     * @param hint
     * @throws Throwable
     */
    protected void checkHintById(int id, final String hint) throws Throwable {
        onView(withId(id)).check(matches(withHint(hint)));
    }


    protected static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }


    /**
     * Find view in RecyclerView on current position
     *
     * @param position
     * @param itemMatcher
     * @return
     */
    protected static Matcher<View> atPosition(final int position, final Matcher<View> itemMatcher) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    /**
     * Find view in RecyclerView with current item id
     *
     * @param id
     * @param itemMatcher
     * @return
     */
    private static Matcher<View> atItemId(final int id, final Matcher<View> itemMatcher) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item with id " + id + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForItemId(id);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }


    @Override
    public <T> List<T> queryForAll(Class<T> entity) throws SQLException {
        return null;
    }

    @Override
    public <T> List<T> queryForEq(Class<T> entity, String fieldName, Object value) throws SQLException {
        return null;
    }

    @Override
    public <T> List<T> queryForFieldValues(Class<T> entity, Map<String, Object> fieldValues) throws SQLException {
        return null;
    }

    @Override
    public <T> void createOrUpdate(Class<T> clazz, T entity) throws SQLException {

    }

    /**
     * This is a convenience method for creating an item in the database if it does not exist.
     *
     * @param clazz    Entity Class
     * @param entities Collection of entity objects
     * @param <T>      Entity
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    @Override
    public <T> void createOrUpdate(Class<T> clazz, Collection<T> entities) throws SQLException {
        for (T entity : entities) {
            createOrUpdate(clazz, entity);
        }
    }

    /**
     * Query for SQL execution.
     *
     * @param clazz Entity Class
     * @param sql   SQL to execute
     * @param <T>   Entity
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    @Override
    public <T> void executeSQL(Class<T> clazz, String sql) throws SQLException {
    }

    /**
     * Query all records with specified order.
     *
     * @param entity Entity Class
     * @param order  columnName, ascending
     * @param <T>    Entity
     * @return All records in the specified order.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    @Override
    public <T> List<T> queryForAll(Class<T> entity, LinkedHashMap<String, Boolean> order) throws SQLException {
        return null;
    }


    /**
     * Clear all data out of the table.
     *
     * @param clazz Entity Class
     * @param <T>   Entity
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    @SafeVarargs
    @Override
    public final <T> void deleteAll(Class<T>... clazz) throws SQLException {
        deleteAll(Arrays.asList(clazz));
    }

    /**
     * Clear all data out of the table.
     *
     * @param clazz Collection of entity Class
     * @param <T>   Entity
     * @throws SQLException
     */
    @Override
    public <T> void deleteAll(Collection<Class<T>> clazz) throws SQLException {
    }

    /**
     * Set text to view.
     * <br></br><br></br>
     * <b>Use this instead of typeText or replaceText on TextView because the original method:
     * Action will not be performed because the target view does not match one or more of the
     * following constraints: EditText(Actual:AppCompatTextView)</b>
     *
     * @param value text to set
     * @return ViewAction
     */
    private ViewAction setText(final String value) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return AllOf.allOf(isDisplayed(), isAssignableFrom(TextView.class));
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TextView) view).setText(value);
            }

            @Override
            public String getDescription() {
                return "replace text";
            }
        };
    }

    // View Actions

    /**
     * Perform click
     *
     * @param id View's id
     */
    @Override
    public void onPerformClick(final int id) {
        onView(withId(id)).perform(click());
    }

    /**
     * Perform double click
     *
     * @param id View's id
     */
    @Override
    public void onPerformDoubleClick(final int id) {
        onView(withId(id)).perform(doubleClick());
    }

    /**
     * Perform long click
     *
     * @param id View's id
     */
    @Override
    public void onPerformLongClick(final int id) {
        onView(withId(id)).perform(longClick());
    }

    /**
     * Perform back press
     *
     * @param id View's id
     */
    @Override
    public void onPerformPressBack(final int id) {
        onView(withId(id)).perform(pressBack());
    }


    /**
     * Perform set text.
     *
     * @param id    View's id
     * @param value The text to set.
     */
    @Override
    public void onPerformSetText(final int id, final String value) {
        onView(withId(id)).perform(setText(value), closeSoftKeyboard());

    }

    /**
     * Perform action on item at position.
     *
     * @param id       RecyclerView's id
     * @param position Position of the item.
     */
    @Override
    public void onPerformActionOnItemAtPosition(final int id, final int position) {
        onView(withId(id)).perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));
    }


    /**
     * Perform action on click item in a decor view.
     *
     * @param text
     * @param v
     */
    @Override
    public void onPerformActionOnClickDecorView(final String text, final View v) {
        onView(withText(text))
                .inRoot(withDecorView(not(is(v))))
                .perform(click());
    }

    /**
     * Perform action on item in a ListView.
     *
     * @param id       ListView's id
     * @param position Position of the item to select
     */
    @Override
    public void onPerformActionListViewOnItemAtPosition(final int id, final int position) {

        onData(anything()).inAdapterView(withId(id)).atPosition(position).perform(click());

    }

    @Override
    public void onPerformPressKey(final int id, final int key) {
        onView(withId(id)).perform(pressKey(key), closeSoftKeyboard());

    }

    @Override
    public void onPerformClearText(int id) {
        onView(withId(id)).perform(clearText());
    }

    /**
     * Set date for DatePicker.
     *
     * @param id          DatePicker
     * @param year        Year
     * @param monthOfYear Month of year
     * @param dayOfMonth  Day of month
     */
    @Override
    public void onPerformPickerActions(int id, int year, int monthOfYear, int dayOfMonth) {
//        onView(withId(id)).perform(PickerActions.setDate(year, monthOfYear, dayOfMonth));
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, monthOfYear, dayOfMonth));

    }

    // View Matchers - View Assertions

    /**
     * Assert view that is currently displayed on the screen to the user.
     *
     * @param id View's id
     */
    @Override
    public void assertIsDisplayed(final int id) {
        onView(withId(id)).check(ViewAssertions.matches(isDisplayed()));
    }


    /**
     * Assert view that is currently not displayed on the screen to the user.
     *
     * @param id View's id
     */
    @Override
    public void assertIsNotDisplayed(final int id) {
        onView(withId(id)).check(ViewAssertions.matches(not(isDisplayed())));
    }

    /**
     * Assert view that is currently enabled.
     *
     * @param id View's id
     */
    public void assertIsEnabled(int id) {
        onView(withId(id)).check(ViewAssertions.matches(isEnabled()));
    }

    /**
     * Assert view that is currently not enabled.
     *
     * @param id View's id
     */
    public void assertIsNotEnabled(int id) {
        onView(withId(id)).check(ViewAssertions.matches(not(isEnabled())));
    }

    /**
     * Assert view that is currently clickable.
     *
     * @param id View's id
     */
    public void assertIsClickable(int id) {
        onView(withId(id)).check(ViewAssertions.matches(isClickable()));
    }

    /**
     * Assert view that is currently not clickable.
     *
     * @param id View's id
     */
    public void assertIsNotClickable(int id) {
        onView(withId(id)).check(ViewAssertions.matches(not(isClickable())));
    }

    /**
     * Asserts the view with expected text.
     *
     * @param id       View's id
     * @param expected Expected text
     */
    @Override
    public void assertWithText(final int id, final String expected) {
        onView(withId(id)).check(ViewAssertions.matches(withText(expected)));
    }

    /**
     * Asserts the view with expected text.
     *
     * @param id       View's id
     * @param expected Resource id - Expected text
     */
    @Override
    public void assertWithText(final int id, final int expected) {
        onView(withId(id)).check(ViewAssertions.matches(withText(expected)));
    }

    /**
     * Asserts the view with expected text.
     *
     * @param id       View's id
     * @param expected Expected text
     */
    @Override
    public void assertWithHint(final int id, final String expected) {
        onView(withId(id)).check(ViewAssertions.matches(withHint(expected)));
    }

    /**
     * Asserts the view with expected text.
     *
     * @param id       View's id
     * @param expected Resource id - Expected text
     */
    @Override
    public void assertWithHint(final int id, final int expected) {
        onView(withId(id)).check(ViewAssertions.matches(withHint(expected)));
    }

    /**
     * Asserts color of text
     *
     * @param id        View's id
     * @param expected  Resource id - Expected color
     * @param resources Resource context
     */
    @Override
    public void assertWithTextColor(final int id, final int expected, Resources resources) {
        onView(withId(id)).check(ViewAssertions.matches(withTextColor(expected, resources)));
    }

    /**
     * Asserts if the dialog is displayed.
     *
     * @param id The parent or title id.
     */
    @Override
    public void assertIsDialogIsDisplayed(final int id) {
        onView(withId(id))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }


    /**
     * Assert dialog message.
     *
     * @param id       View's id
     * @param expected Expected message
     */
    @Override
    public void assertIsDialogWithText(final int id, final String expected) {
        onView(withId(id))
                .inRoot(isDialog())
                .check(matches(withText(expected)));

    }

    /**
     * Assert dialog message.
     *
     * @param id       View's id
     * @param expected Expected message
     */
    @Override
    public void assertIsDialogWithText(final int id, final int expected) {
        onView(withId(id))
                .inRoot(isDialog())
                .check(matches(withText(expected)));

    }

    /**
     * Assert startActivity.
     *
     * @param expected The target activity class name.
     */
    @Override
    public void assertStartActivity(String expected) {
        intended(hasComponent(expected));
    }

    /**
     * Assert if the item has expected descendant Data
     *
     * @param id
     * @param itemId
     * @param expected
     */
    @Override
    public void assertHasDescendantOnItemId(final int id, final int itemId, final String expected) {
        onView(withId(id))
                .check(matches(atItemId(itemId, hasDescendant(withText(expected)))));
    }

    /**
     * Assert if the item has expected descendant Data
     *
     * @param id
     * @param position
     * @param expected
     */
    @Override
    public void assertHasDescendantOnItem(final int id, final int position, final String expected) {
        onView(withId(id))
                .check(matches(atPosition(position, hasDescendant(withText(expected)))));
    }


    /**
     * Asserts color of back ground.
     *
     * @param id
     * @param color
     */
    @Override
    public void assertWithBackGroundColor(final int id, final int color) {
        onView(withId(id))
                .check(matches(withBgColor(color)));
    }

    /**
     * Asserts if checkbox is not checked
     *
     * @param id Checkbox
     */
    @Override
    public void assertIsChecked(final int id) {
        onView(withId(id)).check(matches(isChecked()));
    }

    /**
     * Asserts if checkbox is not checked
     *
     * @param id Checkbox
     */
    @Override
    public void assertIsNotChecked(final int id) {
        onView(withId(id)).check(matches(isNotChecked()));

    }


    /**
     * check background color
     *
     * @param color
     * @return
     */
    private Matcher<View> withBgColor(final int color) {
        Checks.checkNotNull(color);
        return new BoundedMatcher<View, View>(View.class) {
            @Override
            public boolean matchesSafely(View textView) {
                return ContextCompat.getColor(getTargetContext(), color) == ((ColorDrawable) textView.getBackground()).getColor();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with text color: ");
            }
        };
    }

}

