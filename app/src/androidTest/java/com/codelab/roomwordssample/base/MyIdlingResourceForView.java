package com.codelab.roomwordssample.base;

import android.app.Activity;
import android.os.Handler;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.test.espresso.IdlingResource;

import java.lang.ref.WeakReference;

/**
 * 通过View的visibility变化判断资源何时空闲
 */
public class MyIdlingResourceForView implements IdlingResource {

    private static final int IDLE_POLL_DELAY_MILLIS = 100;

    private final WeakReference<View> mView;
    private final int mVisibility;
    private ResourceCallback mResourceCallback;

    public MyIdlingResourceForView(@NonNull Activity activity, @IdRes int viewId, int visibility) {
        this(activity.findViewById(viewId), visibility);
    }

    public <T extends View> MyIdlingResourceForView(View view, int visibility) {
        mView = new WeakReference<View>(view);
        mVisibility = visibility;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isIdleNow() {
        View view = mView.get();
        final boolean isIdle = view == null || view.getVisibility() == mVisibility;
        // 如果view处在传入的指定状态，则进入资源空闲判定
        if (isIdle) {
            if (mResourceCallback != null) {
                mResourceCallback.onTransitionToIdle(); // 将资源视为正在转为空闲
            }
        } else {
            new Handler().postDelayed(() -> isIdleNow(), IDLE_POLL_DELAY_MILLIS);
        }
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mResourceCallback = callback;

    }


}
