package com.hj.hd.hhdd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    // 다른 액티비티에서 thirdFragment의 메소드 호출을 위해

    ListAdapter listAdapter;



    ArrayList<listItem> listData = new ArrayList<>();

    ListDialogFragment e;

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

        e = new ListDialogFragment();

        folderPath = getActivity().getFilesDir() + "/userdata/";
        Log.d("loadCheck", folderPath);
        File file = new File(folderPath);

        if (!file.exists())
        {
            file.mkdirs();
        }

        listView = (ListView)layout.findViewById(R.id.third_view_list);


        // 현재 시간을 가져와 현재 날짜를 기준으로 리스트뷰 갱신하기 위함
        now = System.currentTimeMillis();
        date = new Date(now);

        CurDateFormat = new SimpleDateFormat("yyyy.MM");

        strCurDate = CurDateFormat.format(date);

        period= (TextView)layout.findViewById(R.id.third_view_period);
        period.setText(strCurDate);


        // 현재 날짜에서 년, 월 추출
        nowYear = strCurDate.substring(0, 4);
        i_nowYear = Integer.parseInt(nowYear);
        nowMonth = strCurDate.substring(5, 7);
        i_nowMonth = Integer.parseInt(nowMonth);

        sysYear = i_nowYear;
        sysMonth = i_nowMonth;
        Log.d("oncreate",String.valueOf(sysYear) + " + " + String.valueOf(sysMonth));

        // 데이터 불러오기
        loadData();


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

                nowYear = i_nowYear + ".";
                nowMonth = String.format("%02d", i_nowMonth);
                period.setText(nowYear + nowMonth);
                Log.d("prev",nowYear + "+" + nowMonth);

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

                    nowYear = i_nowYear + ".";
                    nowMonth = String.format("%02d", i_nowMonth);
                    period.setText(nowYear + nowMonth);
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


        // 리스트 아이템 롱클릭 이벤트 리스너
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
            public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id)
           {
               // 리스트 아이템에 관한 메뉴 출력 (수정, 제거 등)
               //e = ListDialogFragment.getInstance();

               String longClickData[] = {null, null};

               String str = listData.get(position).strDate;

               longClickData[0] = str;
               longClickData[1] = String.valueOf(position);

               Bundle sendData = new Bundle();
               sendData.putString("date",str);
               sendData.putStringArray("longClick", longClickData);
               e.setTargetFragment(thirdFragment.this, 0);
               e.show(getFragmentManager(), "EVENT_DIALOG");
               //e.show(getActivity().getFragmentManager(), ListDialogFragment.TAG_EVENT_DIALOG);
               e.setArguments(sendData);
               //TextView tv;// = getActivity().findViewById(R.id.dialog_date_text);
               //tv = getActivity().findViewById (R.id.dialog_date_text);

               //tv.setText(str);
               //Log.d("OnItemLongClickListener", tv.getText().toString());
               // e.setDateText(tv, str);   error~~~~~~~~~~~~~~~~~~~~~~~~~~~
               //mToast = Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT);
               //mToast.show();

               return true;
           }

        });




        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String flag[] = data.getStringArrayExtra("dialogData");
        int pos = Integer.valueOf(flag[1]);
        // 수정했을 경우
        if (flag[0] == "1")
        {
            Toast.makeText(getActivity(), flag[1], Toast.LENGTH_SHORT).show();

            String modify[] = {null, null, null};
            modify[0] = "M";
            modify[1] = listData.get(pos).strDate;
            modify[2] = listData.get(pos).strContent;

            Intent modifyWriteActivity = new Intent(getContext(), writeActivity.class);
            modifyWriteActivity.putExtra("write",modify);
            startActivity(modifyWriteActivity);



            listAdapter.setItemList(listData);
            listAdapter.notifyDataSetChanged();
        }

        // 제거했을 경우
        else if (flag[0] == "2")
        {
            Toast.makeText(getActivity(), flag[1], Toast.LENGTH_SHORT).show();
            listData.remove(pos);

            listAdapter.setItemList(listData);
            listAdapter.notifyDataSetChanged();

        }

        // 아무것도 아닐 경우
        else
        {

        }

    }

    // 년도는 바뀌지 않고 월만 변경되었을 경우
    public void renewData (String nowYear, String nowMonth)
    {
        try{
            BufferedReader br = new BufferedReader(new FileReader(folderPath + "dataOf" + nowYear + "txt"));
            String readStr = "";
            String str = null;

            listData.clear();

            while (((str = br.readLine()) != null))
            {
                if (str.substring(5,7).equals(nowMonth))
                {
                    st = new StringTokenizer(str, "+");
                    strDate = st.nextToken();
                    strContext = st.nextToken();

                    strContext = strContext.replace("\\n", "\n");

                    listItem newData = new listItem();

                    strDate = strDate.substring(0, 19);

                    newData.strDate = strDate;
                    newData.strContent = strContext;

                    listData.add(newData);
                }
            }
            listAdapter = new ListAdapter(listData);
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
            BufferedReader br = new BufferedReader(new FileReader(folderPath + "dataOf" + nowYear + "txt"));
            String readStr = "";
            String str = null;

            listData.clear();

            while (((str = br.readLine()) != null))
            {
                if (str.substring(5,7).equals(nowMonth))
                {
                    st = new StringTokenizer(str, "+");
                    strDate = st.nextToken();
                    strContext = st.nextToken();

                    strContext = strContext.replace("\\n", "\n");

                    listItem newData = new listItem();

                    strDate = strDate.substring(0, 19);

                    newData.strDate = strDate;
                    newData.strContent = strContext;

                    listData.add(newData);
                }
            }
            listAdapter = new ListAdapter(listData);
            listView.setAdapter(listAdapter);

            br.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData ()
    {
        try{
            BufferedReader br = new BufferedReader(new FileReader(folderPath + "dataOf" + String.valueOf(sysYear) + ".txt"));
            String readStr = "";
            String str = null;

            listData.clear();

            String str_sysMonth = String.format("%02d", sysMonth);

            while (((str = br.readLine()) != null))
            {
                if (str.substring(5,7).equals(str_sysMonth))
                {
                    st = new StringTokenizer(str, "+");
                    strDate = st.nextToken();
                    strContext = st.nextToken();

                    strContext = strContext.replace("\\n", "\n");

                    listItem newData = new listItem();

                    strDate = strDate.substring(0, 19);

                    newData.strDate = strDate;
                    newData.strContent = strContext;

                    listData.add(newData);

                }
            }
            listAdapter = new ListAdapter(listData);
            listView.setAdapter(listAdapter);
            br.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

