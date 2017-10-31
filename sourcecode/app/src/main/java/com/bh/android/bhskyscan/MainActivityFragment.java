package com.bh.android.bhskyscan;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    private Context mContext = null;
    private Button mStartDateButton = null;
    private Button mEndDateButton = null;
    private Time mStartTime = null;
    private Time mEndTime = null;

    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = this.getContext();
        if (mStartTime == null) {
            mStartTime = new Time();
            mStartTime.setToNow();
        }
        if (mEndTime == null) {
            mEndTime = new Time();
            mEndTime.setToNow();
        }

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mStartDateButton = view.findViewById(R.id.btn_start_date);
        mStartDateButton.setOnClickListener(new DateClickListener(mStartTime));
        mEndDateButton = view.findViewById(R.id.btn_end_date);
        mEndDateButton.setOnClickListener(new DateClickListener(mEndTime));

        return view;
    }

    private class DateClickListener implements View.OnClickListener {
        private final Time mTime;

        public DateClickListener(Time time) {
            mTime = time;
        }

        public void onClick(View v) {
            new DatePickerDialog(mContext, new DateListener(v), mTime.year,
                    mTime.month, mTime.monthDay).show();
        }
    }

    private class DateListener implements DatePickerDialog.OnDateSetListener {

        private View mView = null;

        public DateListener(View v) {
            mView = v;
        }


        @Override
        public void onDateSet(DatePicker view, int year, int month, int monthDay) {
            // Cache the member variables locally to avoid inner class overhead.
            Time startTime = mStartTime;
            Time endTime = mEndTime;

            // Cache the start and end millis so that we limit the number
            // of calls to normalize() and toMillis(), which are fairly
            // expensive.
            long startMillis;
            long endMillis;
            if (mView == mStartDateButton) {
                // The start date was changed.
                int yearDuration = endTime.year - startTime.year;
                int monthDuration = endTime.month - startTime.month;
                int monthDayDuration = endTime.monthDay - startTime.monthDay;

                startTime.year = year;
                startTime.month = month;
                startTime.monthDay = monthDay;
                startMillis = startTime.normalize(true);

                // Also update the end date to keep the duration constant.
                endTime.year = year + yearDuration;
                endTime.month = month + monthDuration;
                endTime.monthDay = monthDay + monthDayDuration;
                endMillis = endTime.normalize(true);

                // If the start date has changed then update the repeats.
                //populateRepeats();
            } else {
                // The end date was changed.
                startMillis = startTime.toMillis(true);
                endTime.year = year;
                endTime.month = month;
                endTime.monthDay = monthDay;
                endMillis = endTime.normalize(true);

                // Do not allow an event to have an end time before the start time.
                if (endTime.before(startTime)) {
                    endTime.set(startTime);
                    endMillis = startMillis;
                }
            }

            setDate(mStartDateButton, startMillis);
            setDate(mEndDateButton, endMillis);
        }
    }

    private void setDate(Button view, long millis) {
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR |
                DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_MONTH |
                DateUtils.FORMAT_ABBREV_WEEKDAY;
        view.setText(DateUtils.formatDateTime(mContext, millis, flags));
    }
}

