package com.codelab.roomwordssample.base;

import android.view.View;

import androidx.test.espresso.ViewAction;

/**
 * Interface for all performing with Espresso.
 *
 */
public interface CommonPerform {
    void onPerformClick(final int id);
    void onPerformDoubleClick(final int id);
    void onPerformLongClick(final int id);
    void onPerformPressBack(final int id);
    void onPerformSetText(final int id, final String value);
    void onPerformActionOnItemAtPosition(final int id, final int position, final ViewAction action);
    void onPerformActionOnClickDecorView(final String text, final View v);
    void onPerformActionListViewOnItemAtPosition(final int id, final int position);
    void onPerformPressKey(final int id, final int key);
    void onPerformClearText(final int id);
    void onPerformPickerActions(final int id, final int year, final int monthOfYear, final int dayOfMonth);
}
