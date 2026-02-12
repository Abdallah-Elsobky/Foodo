package iti.student.foodo.features.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Locale;

public class MyDatePicker {

    public interface OnDateSelectedListener {
        void onDateSelected(String date);
    }

    private Context context;
    private OnDateSelectedListener listener;

    public MyDatePicker(Context context, OnDateSelectedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void show() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {

                    selectedMonth = selectedMonth + 1;

                    String formattedDate = String.format(Locale.getDefault(),
                            "%02d/%02d/%d", selectedDay, selectedMonth, selectedYear);

                    listener.onDateSelected(formattedDate);

                }, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        datePickerDialog.show();
    }
}
