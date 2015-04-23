package de.fhconfig.android.library.ui.datetime;

import android.annotation.TargetApi;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeEditText extends EditText implements View.OnClickListener
{
	public TimeEditText(Context context) {
		super(context);
		init();
	}

	public TimeEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TimeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@TargetApi(21)
	public TimeEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	private void init()
	{
		this.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Calendar calendar = Calendar.getInstance();
		TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				GregorianCalendar cal = new GregorianCalendar(0, 0, 0, hourOfDay, minute);
				TimeEditText.this.setText(DateFormat.getTimeFormat(getContext()).format(cal.getTime()));
			}
		},calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE), true);
		timePickerDialog.show();
	}
}
