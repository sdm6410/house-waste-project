package com.example.cameraproject.Util.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {
    private boolean canScroll = false;
    public MyViewPager(@NonNull Context context) {
        super(context);
    }
    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) { super(context, attrs); }

    @Override
    public boolean onTouchEvent(MotionEvent ev) { return canScroll && super.onTouchEvent(ev); }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) { return canScroll && super.onInterceptTouchEvent(ev); }
}
