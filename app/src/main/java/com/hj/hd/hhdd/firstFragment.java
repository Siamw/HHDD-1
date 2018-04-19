package com.hj.hd.hhdd;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.RelativeLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

/**
 * Created by hwangil on 2018-03-09.
 */

public class firstFragment extends Fragment{
    public firstFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


    }
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.first_view, container, false);
        MaterialCalendarView materialCalendarView = layout.findViewById(R.id.calendarView);


        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new OneDayDecorator());


        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected){
                //날짜를 눌렀을 때 , 내용 작성칸 / 이미 있는 내용 보여지도록 하는 코드 여기에 작성
            }
        });

        return layout;
    }


}
