package kr.ac.kumoh.s20151155.travelapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInsatanceState){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return  new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
       // TextView dateTx = (TextView)getActivity().findViewById(R.id.date);
        //dateTx.setText(view.getYear()+"년 " + (view.getMonth()+1) + "월 " + view.getDayOfMonth() + "일");
       // dateTx.setText(view.getYear()+"");
        TextView yearTx = (TextView) getActivity().findViewById(R.id.date_year);
        TextView monthTx = (TextView) getActivity().findViewById(R.id.date_month);
        TextView dayTx = (TextView) getActivity().findViewById(R.id.date_day);

        yearTx.setText(view.getYear()+"");
        monthTx.setText((view.getMonth()+1)+"");
        dayTx.setText(view.getDayOfMonth()+"");


    }
}
