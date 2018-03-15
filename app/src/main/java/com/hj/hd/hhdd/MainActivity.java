package com.hj.hd.hhdd;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.view.ViewDebug;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    ViewPager vp;

    ImageButton fb;
    ImageButton sb;
    ImageButton tb;

    RelativeLayout mainLayout;

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp = (ViewPager)findViewById(R.id.viewp);
        mainLayout = (RelativeLayout)findViewById(R.id.main_view_layout);
        fb = (ImageButton)findViewById(R.id.first_view_btn);
        sb = (ImageButton)findViewById(R.id.second_view_btn);
        tb = (ImageButton)findViewById(R.id.third_view_btn);

        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setOffscreenPageLimit(2);
        vp.setCurrentItem(1);

        fb.setOnClickListener(movePageListener);
        fb.setTag(0);
        sb.setOnClickListener(movePageListener);
        sb.setTag(1);
        tb.setOnClickListener(movePageListener);
        tb.setTag(2);

        sb.setSelected(true);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
            }

            @Override
            public void onPageSelected(int position)
            {
                int i = 0;
                while(i<3)
                {
                    if(position==i)
                    {
                        mainLayout.findViewWithTag(i).setSelected(true);
                    }
                    else
                    {
                        mainLayout.findViewWithTag(i).setSelected(false);
                    }
                    i++;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
            }
        });
    }

    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();
            Log.d("tag", String.valueOf(tag));
            int i = 0;
            while(i<3)
            {
                if(tag==i)
                {
                    mainLayout.findViewWithTag(i).setSelected(true);
                }
                else
                {
                    mainLayout.findViewWithTag(i).setSelected(false);
                }
                i++;
            }
            vp.setCurrentItem(tag);
        }
    };


    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    return new firstFragment();
                case 1:
                    return new secondFragment();
                case 2:
                    return new thirdFragment();
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 3;
        }

    }

    @Override
    public void onBackPressed()
    {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(this, "한번 더 누르시면 종료됩니다", Toast.LENGTH_SHORT).show();
        }
    }

}
