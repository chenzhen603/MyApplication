package com.example.flower.myapplication;

import android.content.Context;

import com.ldf.calendar.interf.IDayRenderer;
import com.ldf.calendar.view.DayView;

/**
 * Created by FLOWER on 2017/8/16.
 */

public class CustomDayView extends DayView {

    public CustomDayView(Context context, int layoutResource) {
        super(context, layoutResource);
    }

    @Override
    public IDayRenderer copy() {
        return new CustomDayView(context , layoutResource);
    }

    @Override
    public void refreshContent() {
        //你的代码 你可以在这里定义你的显示规则
        super.refreshContent();
    }


}
