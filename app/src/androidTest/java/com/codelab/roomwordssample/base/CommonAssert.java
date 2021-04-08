package com.codelab.roomwordssample.base;

import android.content.res.Resources;

/**
 * Interface for all asserting with Espresso.
 *
 */
public interface CommonAssert {

    void assertIsDisplayed(final int id);
    void assertIsNotDisplayed(final int id);
    void assertIsEnabled(final int id);
    void assertIsNotEnabled(final int id);
    void assertIsNotClickable(final int id);
    void assertIsClickable(final int id);
    void assertWithText(final int id, final String expected);
    void assertWithText(final int id, final int expected);
    void assertWithHint(final int id, final String expected);
    void assertWithHint(final int id, final int expected);
    void assertWithTextColor(final int id, final int expected, Resources resources);
    void assertIsDialogIsDisplayed(final int id);
    void assertIsDialogWithText(final int id, final String expected);
    void assertIsDialogWithText(final int id, final int expected);
    void assertStartActivity(final String expected);
    void assertHasDescendantOnItem(final int id, final int position, final String expected);
    void assertHasDescendantOnItemId(final int id, final int itemId, final String expected);
    void assertWithBackGroundColor(final int id, final int color);
    void assertIsChecked(final int id);
    void assertIsNotChecked(final int id);

}

