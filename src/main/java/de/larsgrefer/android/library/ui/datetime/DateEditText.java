package de.larsgrefer.android.library.ui.datetime;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateEditText extends EditText implements View.OnClickListener
{
	public DateEditText(Context context) {
		super(context);
		init();
	}

	public DateEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DateEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@TargetApi(21)
	public DateEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
		DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);
				DateEditText.this.setText(DateFormat.getDateFormat(getContext()).format(cal.getTime()));
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.show();
	}
}
