package com.cookandroid.to_da_project;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class Calendar_MySelectDecorator implements DayViewDecorator {
    private final Drawable drawable;

    public Calendar_MySelectDecorator(Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.calendar_my_selector);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}
