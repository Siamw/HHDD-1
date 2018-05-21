package com.hj.hd.hhdd;

import android.app.DialogFragment;
import android.os.Bundle;
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


        mConfirmBtn.setOnClickListener (this);

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
