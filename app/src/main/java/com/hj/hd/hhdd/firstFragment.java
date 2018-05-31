package com.hj.hd.hhdd;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;

/**
 * Created by hwangil on 2018-03-09.
 */

public class firstFragment extends Fragment {


    MaterialCalendarView materialCalendarView;


    // 리스트뷰 관련
    private ListView listView;
    //private ArrayList<HashMap<String, String>> Data = new ArrayList<HashMap<String, String>>();
    //private HashMap<String, String> listItem;
    StringTokenizer st;
    String strDate;
    String strContext;

    String[] dateData;

    //for prev, next
    String nowMonth;
    String nowYear;

    int i_nowYear;
    int i_nowMonth;
    TextView period;

    int sysYear;
    int sysMonth;
    int dataNum=0;
    int dataLength;//마지막 입력값이 출력이 되지 않는 것을 고치기 위한 속임수


    //현재 날짜 불러오기 위함
    long now;
    Date date;
    SimpleDateFormat CurDateFormat;
    String strCurDate;

    // prev, next 이미지 관련
    ImageView prevImage;
    ImageView nextImage;

    Toast mToast = null;


    // userdata 저장 폴더 경로
    String folderPath;

    // 날짜 변경시 연도까지 변경되면 1, 월만 변경될 경우 0
    int yearFlag = 0;


    public firstFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.first_view, container, false);
        materialCalendarView = layout.findViewById(R.id.calendarView);

        folderPath = getActivity().getFilesDir() + "/userdata/";
        Log.d("loadCheck", folderPath);
        File file = new File(folderPath);

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new OneDayDecorator()
        );

        now = System.currentTimeMillis();
        date = new Date(now);

        CurDateFormat = new SimpleDateFormat("yyyy.MM");

        strCurDate = CurDateFormat.format(date);
        Log.d("lalalalalalaalalalalal",strCurDate);

        //현재 날짜에서 년, 월 추출
        nowYear = strCurDate.substring(0, 4);
        i_nowYear = Integer.parseInt(nowYear);
        nowMonth = strCurDate.substring(5, 7);
        i_nowMonth = Integer.parseInt(nowMonth);

        sysYear = i_nowYear;
        sysMonth = i_nowMonth;


        Log.d("now year and month  :  ", String.valueOf(sysYear) + " , " + String.valueOf(sysMonth));



        // 데이터 불러오기

        try {
            BufferedReader br = new BufferedReader(new FileReader(folderPath +"dataOf" + String.valueOf(sysYear) + ".txt"));
            String readStr = "";
            String str = null;
            int num = -1;
            dateData = new String[32];


            String str_sysMonth = String.format("%02d", sysMonth);

            while (((str = br.readLine()) != null)) {
                if (str.substring(5, 7).equals(str_sysMonth)) {
                    num++;
                    dataNum++;//data의 개수

                    dateData[num]= str.substring(0,4)+','+str.substring(5,7)+','+str.substring(8,10);
                    Log.d("데이터어어어어어어엉", str.substring(0,4)+','+str.substring(5,7)+','+str.substring(8,10));

                }
            }


            dateData[dataNum]="3000,12,25";

            Log.d("마지막데이터어어어어어어어엉+nnn", dateData[dataNum]+dataNum);


            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] result = dateData;

       new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());


       materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
           @Override
           public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

               Log.d("달을넘겻다 빠바밤 빠바밤 어딜체크하알까", date.toString());








           }
       });

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected (@NonNull MaterialCalendarView widget, @NonNull CalendarDay date,boolean selected){


                //날짜를 눌렀을 때 , 이미 있는 내용 보여지도록 하는 코드 여기에 작성
            }

        });
        return layout;
    }

    public class MonthChange implements OnMonthChangedListener{

        @Override
        public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

        }
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        String[] Time_Result;

        ApiSimulator(String[] Time_Result) {
            this.Time_Result = Time_Result;
        }



        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            Log.d("calendar라는데 뭐가출력되나 봐봅시당" , calendar.toString());

             /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < dataNum+1; i++) {//처음에 현재 날짜가 일이가 없음애도 들어가서 점이 찍힘
                CalendarDay day = CalendarDay.from(calendar);
                Log.d("day라는데 뭐가출력되나 봐봅시당" , day.toString());
                String[] time = Time_Result[i].split(",");
                Log.d("time에 뭐가출력되나 봐봅시당." , time.toString());
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                dates.add(day);
                if(i!=0)
                {calendar.set(year, month - 1, dayy);}
            }


            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);


            Drawable d = getResources().getDrawable(R.drawable.more);

            materialCalendarView.addDecorator(new EventDecorator(Color.RED, calendarDays, d));


        }

    }
}

