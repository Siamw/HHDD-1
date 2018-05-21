package com.hj.hd.hhdd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

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
    TextView personText;
    private TimerTask second;
    private final Handler tHandler = new Handler();


    TextView writeText;

    ImageView treeImage;

    int wiseArySize = 0;
    String wiseSaying[] = {
            "난 어떠한 상황에도 쾌활하고 행복하기로 단단히 마음을 먹었다. 우리 불행의 많은 부분이 상황이 아니라, 우리 태도에 의해 결정된다는 걸 살면서 깨달았기 때문이다.+- Martha Washington"
            ,"행복은 장소가 아니라 방향이다.+- Sydney J. Harris"
            ,"인생의 목적이 행복이라고 단정짓지 말아야 행복할 수 있다.+- George Orwell"
            ,"성공은 당신이 원하는 걸 얻는 것이고, 행복은 당신이 얻은 것을 원하는 것이다.+- Dale Carnegie"
            ,"행복은 저항할 수 없는 해프닝의 연속을 의미한다.+- Deepak Chopra"
            ,"행복을 잃어버리는 가장 확실한 방법은 모든 걸 희생하면서 단 하나를 원하는 것이다.+- Bette Davis"
            ,"가끔 행복은 당신이 열어놓았는지 깨닫지도 못한 문을 통해 슬그머니 들어온다.+- John Barrymore"
            ,"행복은 키스와 같다. 행복을 즐기기 위해선 나눌 대상이 있어야 한다.+- Bernard Meltzer"
            ,"큰 행복을 느끼기 위해선 큰 고통과 불행을 먼저 가져야 한다. 그렇지 않으면 이게 행복인지 어떻게 알겠는가?+- Leslie Caron"
            ,"헌법은 행복을 추구할 권리를 국민에게 부여한다. 그러나 행복을 낚아채는 건 당신 몫이다.+- Benjamin Franklin"
            ,"행복을 기억하긴 참 어렵다. 행복은 '섬광'이기 때문이다.+- Frank McCourt"
            ,"행복은 나비와 같다. 따가가려 하면 자꾸 당신 손아귀를 벗어난다. 하지만 당신이 가만히 앉아있으면 아마 당신 위에 살포시 앉을 것이다.+- Nathaniel Hawthorne"
            ,"행복이란 우리가 시간을 들여 열중하는 모든 것이다.+- Albert Camus"
            ,"행복은 축복의 횟수가 아니라 행복을 대하는 우리의 태도일 뿐이다.+- Alexandre Solj´enitsyne"
            ,"행복은 잠깐 나타나서 우리를 한순간 기쁘게 해주는 나비 같다.+- Anna Pavlova"
            ,"행복은 손으로 물을 잡으려는 것과 같다.+- Michelangelo Antonioni"
            ,"행복은 햇살과 같아서 아주 작은 그림자로도 차단된다.+- Chinese proverb"
            ,"행복은 붙잡자마자 사라지는 에우리디케 같다.+- Denis De Rougemont"
            ,"행복은 붙잡지 않는 손에만 내려앉는 천국의 새다.+- John Barry"
            ,"행복은 다른 일을 하는 과정에서 얻게 되는 부산물이다.+- Aldous Leonard Huxley"
            ,"행복은 우연히 온다. 행복을 추구의 대상으로 삼으면 결코 달성할 수 없다.+- Nathaniel Hawthorne"
            ,"행복은 배움이며 소득이자 열망이다.+- Lillian Diana Gish"
            ,"행복은 행복에 있는 것이 아니라 그 성취에 있다.+- Fyodor Mikhailovich Dostoevskii"
            ,"행복은 한 면에서만 기대해서는 안 된다.+- Sigmund Freud"
            ,"행복은 우리 중 어느 누구도 같은 길로 인도하지 않는다.+- Charles Caleb Colton"
            ,"행복은 다양하고 유쾌한 의식 속에 있다.+- Samuel Johnson"
            ,"행복은 사소한 일에서 곧바로 즐거움을 알아채는 것이다.+- Hugh Walpole"
            ,"행복은 사소한 것에 있다.+- John Ruskin"
            ,"행복은 절제된 습관과 소박한 소망에서 비롯된다.+- James Woods"
            ,"행복은 다른 사람들도 행복해하는 모습을 보는 데 자신을 바치는 것이다.+- Bertrand Russell"
            ,"자신을 위해서만 찾는 행복은 절대로 발견될 수가 없다.+- Thomas Merton"
            ,"행복은 바닷가에 홀로 평화로이 남겨져 있는 것이다.+- Louis+-Ferdinand Céline"
            ,"행복은 부도 화려함도 아닌 평온과 일이다.+- Thomas Jefferson"
            ,"행복은 마음이 평온에 있다.+- Marcus Tullius Cicero"
            ,"행복은 무엇보다도 순진무구의 평온하고 만족스러운 현실성이다.+- Henrik Johan Ibsen"
            ,"행복은 순진무구다.+- Marguerite Yourcenar"
            ,"가장 행복한 사람들은 행복을 더 많이 가지려는 자가 아니라, 더 많이 주려는 자들이다.+- H. Jackson Brown, Jr."
            ,"행동이 항상 행복을 가져오지 않을 수도 있다. 하지만 행동을 취하지 않으면 행복은 없다.+- Benjamin Disraeli"
            ,"행복은 문제가 없는 것이 아니라, 문제들을 다루는 능력이다.+- Steve Maraboli"
            ,"만약 당신이 한 시간의 행복을 원한다면, 낮잠을 자라. 만약 당신이 하루의 행복을 원한다면, 낚시를 가라. 만약 당신이 일 년의 행복을 원한다면, 재산을 물려받아라. 만약 당신이 평생의 행복을 원한다면, 다른 사람을 도와라.+- Chinese proverb"
            ,"행복한 사람들은 아름답다. 그들은 거울처럼 되고 그 행복을 다시 반사한다.+- Drew Barrymore"
            ,"모든 사람들이 산 정상에서 살기를 원한다. 하지만 모든 행복과 성장은 당신이 산을 오르고 있을 때 발생한다.+- Andy Rooney"
            ,"행복은 목적지가 아니다. 행복은 잘 살고 있는 삶의 부산물이다.+- Anna Eleanor Roosevelt"
            ,"사막이 아름다운 것은 어딘가에 샘이 숨겨져 있기 때문이다.+- Antoine Marie Jean+-Baptiste Roger de Saint+-Exupéry"
            ,"행복의 한 쪽 문이 닫히면 다른 쪽 문이 열린다. 그러나 흔히 우리는 닫혀진 문을 오랫동안 보기 때문에 우리를 위해 열려 있는 문을 보지 못한다.+- Helen Adams Keller"
            ,"당신이 인생의 주인공이기 때문이다. 그사실을 잊지마라. 지금까지 당신이 만들어온 의식적 그리고 무의식적 선택으로 인해 지금의 당신이 있는것이다.+- Barbara Hall"
            ,"세상은 고통으로 가득하지만 그것을 극복하는 사람들로도 가득하다.+- Helen Adams Keller"
    };

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
        personText = (TextView)layout.findViewById(R.id.second_view_person);

        wiseArySize = wiseSaying.length;

        printWiseText();

        timeMethod();
        changeWiseText();

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

                CurDateFormat = new SimpleDateFormat("yyyy.MM.dd");
                CurTimeFormat = new SimpleDateFormat("hh:mm a");

                strCurDate = CurDateFormat.format(date);
                strCurTime = CurTimeFormat.format(date);
                strNow = strCurDate + " " + strCurTime;

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

    public void changeWiseText() {

        second = new TimerTask() {

            @Override
            public void run() {
                Update();
            }
        };
        Timer timer = new Timer();
        timer.schedule(second, 0, 5000);
    }

    protected void Update() {
        Runnable updater = new Runnable() {
            public void run() {
                printWiseText();
            }
        };
        tHandler.post(updater);
    }

    public void printWiseText()
    {
        Random rand = new Random();

        int loc = rand.nextInt(wiseArySize);

        String strPerson = null;
        String strSaying = null;

        StringTokenizer st = new StringTokenizer(wiseSaying[loc], "+");
        strSaying = st.nextToken();
        strPerson = st.nextToken();

        strSaying = strSaying.replace("\\n", "\n");

        strTotalText = "<b>" + strPerson + "</b>";
        personText.setText(Html.fromHtml(strTotalText));
        totalText.setText(strSaying);
    }

}
