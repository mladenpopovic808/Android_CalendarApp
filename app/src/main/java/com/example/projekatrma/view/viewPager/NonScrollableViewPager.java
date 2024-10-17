package com.example.projekatrma.view.viewPager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class NonScrollableViewPager extends ViewPager {

    public NonScrollableViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    } //ugasili smo to da mozemo prstom da slajdujemo,jer bismo time presli na drugi tab ali to se ne bi
    //odrazilo na bottom navigation(jer tako jednostavno radi) bar,i zato gasimo.

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
