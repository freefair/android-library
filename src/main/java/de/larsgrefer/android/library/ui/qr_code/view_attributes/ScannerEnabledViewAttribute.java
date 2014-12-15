package de.larsgrefer.android.library.ui.qr_code.view_attributes;

import android.view.View;

import de.larsgrefer.android.library.ui.qr_code.QRScannerView;
import gueei.binding.ViewAttribute;

public class ScannerEnabledViewAttribute extends ViewAttribute<QRScannerView, Boolean> {
	public ScannerEnabledViewAttribute(QRScannerView qrScannerView) {
		super(Boolean.class, qrScannerView, "scannerEnabled");
	}

	@Override
	protected void doSetAttributeValue(Object o) {
		Boolean b = (Boolean)o;
		if(b != null)
		{
			if(b) {
				getView().startScanner();
			} else {
				getView().stopScanner();
			}
		}
	}

	@Override
	public Boolean get() {
		return getView().isScannerEnabled();
	}
}
