package com.hj.hd.hhdd;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hwangil on 2018-03-09.
 */

public class secondFragment extends Fragment{
    public secondFragment()
    {
    }

    // 시간 텍스트 관련
    long now;
    Date date;
    TextView mainDateText;
    SimpleDateFormat CurDateFormat;
    SimpleDateFormat CurTimeFormat;
    String strCurDate;
    String strCurTime;
    String strNow;

    // total 텍스트 관련
    String strTotalText;
    TextView totalText;

    TextView writeText;

    ImageView treeImage;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.second_view, container, false);

        mainDateText = (TextView)layout.findViewById(R.id.second_view_date);
        totalText = (TextView)layout.findViewById(R.id.second_view_total);
        //mainDateText.setText(strCurDate + strCurTime);
        strTotalText = "<b>Boled text</b>, <i>Italic text</i> <br> even <u>underlined!</u>";
        totalText.setText(Html.fromHtml(strTotalText));

        timeMethod();

        writeText = (TextView)layout.findViewById(R.id.second_view_write);
        writeText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v)
            {
                Intent writeIntent = new Intent(v.getContext(), writeActivity.class);
                writeIntent.putExtra("date", strNow);
                //getActivity().finish();
                startActivity(writeIntent);
            }
        });



        treeImage = (ImageView)layout.findViewById(R.id.second_view_tree);
        treeImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v)
            {
                Intent writeIntent = new Intent(v.getContext(), writeActivity.class);
                writeIntent.putExtra("date", strNow);
                //getActivity().finish();
                startActivity(writeIntent);
            }
        });


        return layout;
    }
    public void timeMethod()
    {
        final Handler handler  = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                now = System.currentTimeMillis();
                date = new Date(now);

                CurDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 ");
                CurTimeFormat = new SimpleDateFormat("HH시 mm분 ss초");

                strCurDate = CurDateFormat.format(date);
                strCurTime = CurTimeFormat.format(date);
                strNow = strCurDate + strCurTime;

                mainDateText.setText(strNow);
            }
        };

        Runnable task = new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                    handler.sendEmptyMessage(1);
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
}
