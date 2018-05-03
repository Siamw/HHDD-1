package com.hj.hd.hhdd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedWriter;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_view);

        Intent intent = getIntent();
        final String date = intent.getStringExtra("date");

        dateText = findViewById(R.id.write_view_date);

        dateText.setText(date);

        backText = findViewById(R.id.write_view_back);
        confirmText = findViewById(R.id.write_view_confirm);

        editContext = findViewById(R.id.write_view_edit);

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
                    String tempDate = date.substring(0,4);
                    BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "/userdata/" + tempDate + "년.txt", true));
                    bw.write(date + "+" + context + "\n");
                    bw.close();
                    Log.d ("check", String.valueOf(getFilesDir()));
                    //FileOutputStream fos = openFileOutput("userdata.txt", Context.MODE_APPEND);
                    Log.d("check", String.valueOf(getFilesDir()));
                    Log.d("파일","생성");
                    //PrintWriter out  = new PrintWriter(fos);
                    //out.println(data + "+" + context + "\n");
                    //out.close();
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
