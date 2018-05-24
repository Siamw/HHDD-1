package com.hj.hd.hhdd;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
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

        CurDateFormat = new SimpleDateFormat("yyyy년 MM월");

        strCurDate = CurDateFormat.format(date);

        //현재 날짜에서 년, 월 추출
        nowYear = strCurDate.substring(0, 4);
        i_nowYear = Integer.parseInt(nowYear);
        nowMonth = strCurDate.substring(6, 8);
        i_nowMonth = Integer.parseInt(nowMonth);

        sysYear = i_nowYear;
        sysMonth = i_nowMonth;


        Log.d("now year and month  :  ", String.valueOf(sysYear) + " , " + String.valueOf(sysMonth));



        //setOnMonthChangedListener


        // 데이터 불러오기

        try {
            BufferedReader br = new BufferedReader(new FileReader(folderPath + String.valueOf(sysYear) + "년.txt"));
            String readStr = "";
            String str = null;
            int num = -1;
            dateData = new String[32];


            String str_sysMonth = String.format("%02d", sysMonth);

            while (((str = br.readLine()) != null)) {
                if (str.substring(6, 8).equals(str_sysMonth)) {
                    num++;
                    dataNum++;//data의 개수
                    int length=dateData.length;
                    dateData[num]= str.substring(0,4)+','+str.substring(6,8)+','+str.substring(10,12);
                    Log.d("데이터어어어어어어엉", str.substring(0,4)+','+str.substring(6,8)+','+str.substring(10,12));

                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] result = dateData;

       new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected (@NonNull MaterialCalendarView widget, @NonNull CalendarDay date,boolean selected){


                //날짜를 눌렀을 때 , 이미 있는 내용 보여지도록 하는 코드 여기에 작성
            }

        });
        return layout;
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


             /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < dataNum; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                String[] time = Time_Result[i].split(",");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                dates.add(day);
                calendar.set(year, month - 1, dayy);
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

    /*
        // prev, next 이미지 onClick 이벤트
        prevImage = (ImageView) layout.findViewById(R.id.third_view_prev);
        prevImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i_nowMonth == 1) {
                    i_nowMonth = 12;
                    i_nowYear--;
                    yearFlag = 1;
                } else {
                    i_nowMonth--;
                    yearFlag = 0;
                }

                nowYear = i_nowYear + "년";
                nowMonth = String.format("%02d", i_nowMonth) + "월";
                period.setText(nowYear + " " + nowMonth);
                Log.d("prev", nowYear + " + " + nowMonth);

                if (yearFlag == 1) {
                    loadData(nowYear, nowMonth);
                } else {
                    renewData(nowYear, nowMonth);
                }

                yearFlag = 0;

            }
        });

        nextImage = (ImageView) layout.findViewById(R.id.third_view_next);
        nextImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i_nowYear == sysYear && i_nowMonth == sysMonth) {
                    if (mToast == null) {
                        mToast = Toast.makeText(getActivity(), "아직 일어나지 않은 일입니다.", Toast.LENGTH_SHORT);
                    } else {
                        mToast.setText("아직 일어나지 않은 일입니다.");
                    }
                    mToast.show();

                } else {
                    if (i_nowMonth == 12) {
                        i_nowMonth = 1;
                        i_nowYear++;
                        yearFlag = 1;
                    } else {
                        i_nowMonth++;
                        yearFlag = 0;
                    }

                    nowYear = i_nowYear + "년";
                    nowMonth = String.format("%02d", i_nowMonth) + "월";
                    period.setText(nowYear + " " + nowMonth);
                    Log.d("prev", nowYear + " + " + nowMonth);

                    if (yearFlag == 1) {
                        loadData(nowYear, nowMonth);
                    } else {
                        renewData(nowYear, nowMonth);
                    }

                    yearFlag = 0;

                }
            }
        });


        return layout;
    }

    // 년도는 바뀌지 않고 월만 변경되었을 경우
    public void renewData(String nowYear, String nowMonth) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(folderPath + nowYear + ".txt"));
            String readStr = "";
            String str = null;

            ArrayList<listItem> listData = new ArrayList<>();

            while (((str = br.readLine()) != null)) {
                Log.d("nowMonth", nowMonth);
                Log.d("substring", str.substring(6, 9));
                if (str.substring(6, 9).equals(nowMonth)) {

                    Log.d("string", str);
                    st = new StringTokenizer(str, "+");
                    strDate = st.nextToken();
                    strContext = st.nextToken();

                    strContext = strContext.replace("\\n", "\n");

                    listItem newData = new listItem();

                    strDate = strDate.substring(6, 21);

                    newData.strDate = strDate;
                    newData.strContent = strContext;

                    listData.add(newData);
                }
            }
            ListAdapter listAdapter = new ListAdapter(listData);
            listView.setAdapter(listAdapter);

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 앱 실행시, 혹은 년도와 월 모두 변경되었을 경우

    public void loadData(String nowYear, String nowMonth) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(folderPath + nowYear + ".txt"));
            String readStr = "";
            String str = null;
            //StringBuffer data = new StringBuffer();
            //FileInputStream fis = getActivity().openFileInput ("userdata.txt");
            //BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
            //String str = buffer.readLine();

            ArrayList<listItem> listData = new ArrayList<>();

            while (((str = br.readLine()) != null)) {
                if (str.substring(6, 9).equals(nowMonth)) {

                    Log.d("string", str);
                    st = new StringTokenizer(str, "+");
                    strDate = st.nextToken();
                    strContext = st.nextToken();

                    strContext = strContext.replace("\\n", "\n");

                    listItem newData = new listItem();

                    strDate = strDate.substring(6, 21);

                    newData.strDate = strDate;
                    newData.strContent = strContext;

                    listData.add(newData);

                }

            }
            ListAdapter listAdapter = new ListAdapter(listData);
            listView.setAdapter(listAdapter);

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    public void loadData(String[] dateData) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(folderPath + String.valueOf(sysYear) + "년.txt"));
            String readStr = "";
            String str = null;
            String nowDate = null;
            dateData = new String[32];


            String str_sysMonth = String.format("%02d", sysMonth);

            while (((str = br.readLine()) != null)) {
                if (str.substring(6, 8).equals(str_sysMonth)) {
                    nowDate = str.substring(10,12);
                    int length=dateData.length;
                    dateData[length]= str.substring(0,4)+","+str.substring(6,8)+","+str.substring(10,12);
                    //Log.d("string", str + nowDate);

                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


