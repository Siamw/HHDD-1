package com.hj.hd.hhdd;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by hwangil on 2018-03-09.
 */

public class thirdFragment extends Fragment{

    // 리스트뷰 관련
    private ListView listView;
    //private ArrayList<HashMap<String, String>> Data = new ArrayList<HashMap<String, String>>();
    //private HashMap<String, String> listItem;
    StringTokenizer st;
    String strDate;
    String strContext;


    // 날짜 텍스트뷰 관련
    long now;
    Date date;
    TextView period;
    SimpleDateFormat CurDateFormat;
    String strCurDate;


    // prev, next를 위한 날짜 변수
    String nowYear;
    String nowMonth;
    int i_nowYear;
    int i_nowMonth;

    int sysYear;
    int sysMonth;


    // prev, next 이미지 관련
    ImageView prevImage;
    ImageView nextImage;

    Toast mToast = null;


    // userdata 저장 폴더 경로
    String folderPath;

    // 날짜 변경시 연도까지 변경되면 1, 월만 변경될 경우 0
    int yearFlag = 0;


    public int getYear()
    {
        return sysYear;
    }
    public int getMonth ()
    {
        return sysMonth;
    }
    public thirdFragment()
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
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.third_view, container, false);

        folderPath = getActivity().getFilesDir() + "/userdata/";
        File file = new File(folderPath);

        if (!file.exists())
        {
            file.mkdirs();
        }

        listView = (ListView)layout.findViewById(R.id.third_view_list);


        // 현재 시간을 가져와 현재 날짜를 기준으로 리스트뷰 갱신하기 위함
        now = System.currentTimeMillis();
        date = new Date(now);

        CurDateFormat = new SimpleDateFormat("yyyy년 MM월");

        strCurDate = CurDateFormat.format(date);

        period= (TextView)layout.findViewById(R.id.third_view_period);
        period.setText(strCurDate);


        // 현재 날짜에서 년, 월 추출
        nowYear = strCurDate.substring(0, 4);
        i_nowYear = Integer.parseInt(nowYear);
        nowMonth = strCurDate.substring(6, 8);
        i_nowMonth = Integer.parseInt(nowMonth);

        sysYear = i_nowYear;
        sysMonth = i_nowMonth;
        Log.d("prev",String.valueOf(sysYear) + " + " + String.valueOf(sysMonth));

        // 데이터 불러오기
        loadData(String.valueOf(sysYear), String.valueOf(sysMonth));


        // prev, next 이미지 onClick 이벤트
        prevImage = (ImageView)layout.findViewById(R.id.third_view_prev);
        prevImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v)
            {
                if (i_nowMonth == 1)
                {
                    i_nowMonth = 12;
                    i_nowYear --;
                    yearFlag = 1;
                }
                else
                {
                    i_nowMonth --;
                    yearFlag = 0;
                }

                nowYear = i_nowYear + "년";
                nowMonth = String.format("%02d", i_nowMonth) + "월";
                period.setText(nowYear + " " + nowMonth);
                Log.d("prev",nowYear + " + " + nowMonth);

                if (yearFlag == 1)
                {
                    loadData(nowYear, nowMonth);
                }
                else
                {
                    renewData(nowYear, nowMonth);
                }

                yearFlag = 0;

            }
        });

        nextImage = (ImageView)layout.findViewById(R.id.third_view_next);
        nextImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v)
            {
                if (i_nowYear == sysYear && i_nowMonth == sysMonth)
                {
                    if (mToast == null)
                    {
                        mToast = Toast.makeText(getActivity(), "아직 일어나지 않은 일입니다.", Toast.LENGTH_SHORT);
                    }
                    else
                    {
                        mToast.setText("아직 일어나지 않은 일입니다.");
                    }
                    mToast.show();

                }
                else
                {
                    if (i_nowMonth == 12)
                    {
                        i_nowMonth = 1;
                        i_nowYear ++;
                        yearFlag = 1;
                    }
                    else
                    {
                        i_nowMonth ++;
                        yearFlag = 0;
                    }

                    nowYear = i_nowYear + "년";
                    nowMonth = String.format("%02d", i_nowMonth) + "월";
                    period.setText(nowYear + " " + nowMonth);
                    Log.d("prev",nowYear + " + " + nowMonth);

                    if (yearFlag == 1)
                    {
                        loadData(nowYear, nowMonth);
                    }
                    else
                    {
                        renewData(nowYear, nowMonth);
                    }

                    yearFlag = 0;

                }
            }
        });


        return layout;
    }

    // 년도는 바뀌지 않고 월만 변경되었을 경우
    public void renewData (String nowYear, String nowMonth)
    {
        try{
            BufferedReader br = new BufferedReader(new FileReader(folderPath + "userdata.txt"));// nowYear + ".txt"));
            String readStr = "";
            String str = null;

            ArrayList<listItem> listData = new ArrayList<>();

            while (((str = br.readLine()) != null))
            {
                Log.d("string", str);
                st = new StringTokenizer(str, "+");
                strDate = st.nextToken();
                strContext = st.nextToken();

                strContext = strContext.replace("\\n", "\n");

                listItem newData = new listItem();

                newData.strDate = strDate;
                newData.strContent = strContext;

                listData.add(newData);
            }
            ListAdapter listAdapter = new ListAdapter(listData);
            listView.setAdapter(listAdapter);

            br.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 앱 실행시, 혹은 년도와 월 모두 변경되었을 경우
    public void loadData (String nowYear, String nowMonth)
    {
        try{
            BufferedReader br = new BufferedReader(new FileReader(folderPath + "userdata.txt"));// nowYear + ".txt"));
            String readStr = "";
            String str = null;
            //StringBuffer data = new StringBuffer();
            //FileInputStream fis = getActivity().openFileInput ("userdata.txt");
            //BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
            //String str = buffer.readLine();

            ArrayList<listItem> listData = new ArrayList<>();

            while (((str = br.readLine()) != null))
            {
                Log.d("string", str);
                st = new StringTokenizer(str, "+");
                strDate = st.nextToken();
                strContext = st.nextToken();

                strContext = strContext.replace("\\n", "\n");

                listItem newData = new listItem();

                newData.strDate = strDate;
                newData.strContent = strContext;

                listData.add(newData);


                //listItem = new HashMap<>();
                //listItem.put("date", strDate);;
                //listItem.put("context", strContext);

                //Log.d("date", strDate);
                //Log.d("context", strContext);
                //Data.add(listItem);

                //data.append(str + "\n");
                //str = buffer.readLine();
            }
            ListAdapter listAdapter = new ListAdapter(listData);
            listView.setAdapter(listAdapter);
            //SimpleAdapter adapter = new SimpleAdapter(getActivity(), Data, android.R.layout.simple_list_item_2, new String[]{"date","context"}, new int[]{android.R.id.text1, android.R.id.text2});
            //listView.setAdapter(adapter);
            br.close();
            //buffer.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
