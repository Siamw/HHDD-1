package com.hj.hd.hhdd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by hwangil on 2018-03-13.
 */

public class IntroActivity extends AppCompatActivity{


    TextView introText;

    int flow = 0;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.intro_view);

        introText = (TextView)findViewById(R.id.intro_text);

        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(2000);
        final Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(2000);

        Log.d("first","1");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        introText.startAnimation(in);
        introText.setText("안녕하세요.");

        Log.d("second","2");

        in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                introText.startAnimation(out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                switch (flow)
                {
                    case 0:
                        introText.setText("오늘 하루 어떻게 보냈나요?");
                        introText.startAnimation(in);
                        flow ++;
                        break;
                    case 1:
                        introText.setText("네잎클로버의 이름은 행운이에요.\n\n그런데 그거 알고있어요?\n\n세잎클로버도 이름이 있다는걸.");
                        introText.startAnimation(in);
                        flow ++;
                        break;
                    case 2:
                        introText.setText("세잎클로버의 이름은 행복이에요.\n\n혹시 오늘 우리가 네잎클로버라는 행운을 찾으려다 세잎클로버를 지나친건 아닐까요.");
                        introText.startAnimation(in);
                        flow ++;
                        break;
                    case 3:
                        introText.setText("");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        moveToMain();
                    default:
                        break;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void moveToMain ()
    {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }


}
