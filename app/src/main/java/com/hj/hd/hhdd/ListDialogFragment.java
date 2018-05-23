package com.hj.hd.hhdd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by hwangil on 2018-05-10.
 */

public class ListDialogFragment extends DialogFragment implements View.OnClickListener{


    public static final String TAG_EVENT_DIALOG = "dialog_event";

    TextView dialogDate;

    Button rewriteButton;
    Button deleteButton;

    String sendData[] = {"0", "0"};

    public ListDialogFragment() {}
    public static ListDialogFragment getInstance (){
        ListDialogFragment e = new ListDialogFragment();
        return e;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LinearLayout v = (LinearLayout) inflater.inflate(R.layout.dialog_view, container, false);

        Log.d("listDialogFragment", "before dialogDate");
        dialogDate = (TextView) v.findViewById(R.id.dialog_date_text);
        Button mConfirmBtn = (Button) v.findViewById(R.id.btn_confirm);

        String str[] = getArguments().getStringArray("longClick");

        str[0] = str[0] + " 일기";
        int pos = Integer.valueOf(str[1]);
        dialogDate.setText(str[0]);
        sendData[1] = String.valueOf(pos);

        mConfirmBtn.setOnClickListener (this);

        rewriteButton = (Button)v.findViewById(R.id.dialog_rewrite);
        deleteButton = (Button)v.findViewById(R.id.dialog_delete);

        // 해당 일기 수정하기
        // writeActivity를 불러와야함
        rewriteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v)
            {
                //Intent writeIntent = new Intent(v.getContext(), writeActivity.class);
                // writeIntent.putExtra("date", strNow);

                //startActivity(writeIntent);
                sendData[0] = "1";

                Intent data = new Intent(v.getContext(), MainActivity.class);
                data.putExtra("dialogData", sendData);
                getTargetFragment().onActivityResult(0, 0, data);


                dismiss();
            }
        });

        // 해당 일기 제거하기
        // 리스트에서 해당 일기를 제거하고 데이터 파일에서도 제거해야함
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v)
            {
                //Bundle sendData = new Bundle();
                //sendData.putString("cc", "ccpcpcppc");
                //thirdFragment f = new thirdFragment();

                //f.setArguments(sendData);

                sendData[0] = "2";

                Intent data = new Intent(v.getContext(), MainActivity.class);
                data.putExtra("dialogData", sendData);
                getTargetFragment().onActivityResult(0, 0, data);


                dismiss();
            }
        });


        return v;
    }
    @Override
    public void onClick (View v)
    {
        dismiss();
    }
    public void setDateText (TextView tv, String date_str)
    {
        Log.d("setDateText", date_str);

        tv.setText(date_str);
    }
}
