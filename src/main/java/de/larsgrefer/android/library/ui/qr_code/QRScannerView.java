package de.larsgrefer.android.library.ui.qr_code;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import de.larsgrefer.android.library.R;
import de.larsgrefer.android.library.ui.qr_code.view_attributes.CodeRecognizedViewAttribute;
import de.larsgrefer.android.library.ui.qr_code.view_attributes.ScannerEnabledViewAttribute;
import eu.livotov.zxscan.ScannerView;
import gueei.binding.IBindableView;
import gueei.binding.ViewAttribute;

public class QRScannerView extends ScannerView implements IBindableView<QRScannerView> {
	public QRScannerView(Context context) {
		super(context);
	}

	public QRScannerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public QRScannerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(21)
	public QRScannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	private Boolean mEnabled = false;

	@Override
	public void stopScanner() {
		try {
			super.stopScanner();
		} catch (Exception ex) {
			Toast.makeText(this.getContext(), this.getContext().getString(R.string.qr_error, ex.getMessage()), Toast.LENGTH_LONG).show();
			Log.e(this.getClass().getName(), "Error while startScanner", ex);
		}
		mEnabled = false;
	}

	@Override
	public void startScanner() {
		try {
			super.startScanner();
		} catch (Exception ex) {
			Toast.makeText(this.getContext(), this.getContext().getString(R.string.qr_error, ex.getMessage()), Toast.LENGTH_LONG).show();
			Log.e(this.getClass().getName(), "Error while startScanner", ex);
		}
		mEnabled = true;
	}

	public Boolean isScannerEnabled()
	{
		return mEnabled;
	}

	@Override
	public ViewAttribute<? extends View, ?> createViewAttribute(String s) {
		if("codeRecognized".equals(s))
			return new CodeRecognizedViewAttribute(this);
		if("scannerEnabled".equals(s))
			return new ScannerEnabledViewAttribute(this);
		return null;
	}
}
