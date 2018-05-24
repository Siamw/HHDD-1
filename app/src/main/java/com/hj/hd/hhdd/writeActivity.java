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

    String context;

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


        if (intentData[0].equals("W"))
        {// 새로 작성
            dateText.setText(intentData[1]);
        }
        else if (intentData[0].equals("M"))
        {// 기존 내용 수정
            Log.d("elsif", intentData[0]);
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
            }
        });

        confirmText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v)
            {
                context = editContext.getText().toString();
                Intent confirmIntent = new Intent(v.getContext(), MainActivity.class);
                //confirmIntent.putExtra("data", context);

                context = context.replace("\n", "\\n");

                try {
                    String tempDate = intentData[1].substring(0,4);

                    if (intentData[0].equals("W"))
                    {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "/userdata/" + "dataOf" + tempDate + ".txt", true));
                        bw.write(intentData[1] + "+" + context + "\n");
                        bw.close();


                    }
                    else if (intentData[0].equals("M"))
                    {
                        BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/userdata/" + "dataOf" + tempDate + ".txt"));

                        String str;
                        String dummy = "";

                        while (((str = br.readLine()) != null))
                        {
                            if (str.substring(0,19).equals(intentData[1]))
                            {
                                str = intentData[1] + "+" + context + "\n";
                                dummy = dummy + str;
                            }
                            else
                            {
                                dummy = dummy + str + "\n";
                            }
                        }
                        br.close();

                        BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "/userdata/" + "dataOf" + tempDate + ".txt"));
                        dummy = dummy;
                        bw.write(dummy);
                        bw.close();
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                ((MainActivity)MainActivity.mContext).renewScreen();
                finish();
                startActivity(confirmIntent);
            }
        });

    }
}
