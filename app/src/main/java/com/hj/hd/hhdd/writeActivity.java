package com.hj.hd.hhdd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by hwangil on 2018-03-12.
 */

public class writeActivity extends AppCompatActivity {

    TextView dateText;

    TextView backText;
    TextView confirmText;

    String context="";

    EditText editContext;

    String intentData[] = {null, null, null};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_view);

        Intent intent = getIntent();
        intentData = intent.getStringArrayExtra("write");

        dateText = findViewById(R.id.write_view_date);
        backText = findViewById(R.id.write_view_back);
        confirmText = findViewById(R.id.write_view_confirm);
        editContext = findViewById(R.id.write_view_edit);


        // intentData[1]이 2018.05.24 12:11 오후 이런 식으로 되어있걸랑여?
        // 그러니까 4월달에 입력하고 프면
        intentData[1] = intentData[1].replace("29", "07");
        // 위에 코드 바로 여기에다가 추가하면 4월달에 입력됩니더.
        // 만약 4월이 아닌 2월을 하고프면 "04"를 "02"로 바꾸면 됩니다.
        // 만일 년도를 변경하고싶다. 그러면
        // intentData[1] = intentData[1].replace("2018", "2017");
        // 이런 식으로 변경하면 됩니다.
        // 첫번재 파라미터가 타겟, 두번째 파라미터가 대체할 문자열 입니더.
        if (intentData[0].equals("W"))
        {// 새로 작성
            dateText.setText(intentData[1]);
        }
        else if (intentData[0].equals("M"))
        {// 기존 내용 수정
            dateText.setText(intentData[1]);
            editContext.setText(intentData[2]);
            editContext.setSelection(editContext.length());
        }


        backText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v)
            {
                Intent mainIntent = new Intent(v.getContext(), MainActivity.class);
                finish();

                startActivity(mainIntent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
            }
        });

        confirmText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                Intent confirmIntent = new Intent(v.getContext(), MainActivity.class);
                context = editContext.getText().toString();

                if (context.getBytes().length <= 0) {
                    Log.d("no", "nooo");
                    finish();
                    startActivity(confirmIntent);
                }


                //confirmIntent.putExtra("data", context);

                else {

                    context = context.replace("\n", "\\n");

                    try {
                        String tempDate = intentData[1].substring(0, 4);

                        if (intentData[0].equals("W")) {
                            BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "/userdata/" + "dataOf" + tempDate + ".txt", true));
                            bw.write(intentData[1] + "+" + context + "\n");

                            bw.close();
                        } else if (intentData[0].equals("M")) {
                            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/userdata/" + "dataOf" + tempDate + ".txt"));

                            String str;
                            String dummy = "";

                            while (((str = br.readLine()) != null)) {
                                if (str.substring(0, 22).equals(intentData[1])) {
                                    str = intentData[1] + "+" + context + "\n";
                                    dummy = dummy + str;
                                } else {
                                    dummy = dummy + str + "\n";
                                }
                            }
                            br.close();

                            BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "/userdata/" + "dataOf" + tempDate + ".txt"));
                            dummy = dummy;
                            bw.write(dummy);
                            bw.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ((MainActivity) MainActivity.mContext).renewScreen();
                    finish();
                    startActivity(confirmIntent);
                }
            }
        });

    }
}
