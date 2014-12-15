package de.larsgrefer.android.library.ui.qr_code.view_attributes;

import android.content.Context;

import de.larsgrefer.android.library.ui.qr_code.QRScannerView;
import eu.livotov.zxscan.ScannerView;
import gueei.binding.Command;
import gueei.binding.ViewAttribute;

public class CodeRecognizedViewAttribute extends ViewAttribute<QRScannerView, Command> {
	public CodeRecognizedViewAttribute(QRScannerView view) {
		super(Command.class, view, "codeRecognized");
	}

	private String oldData = null;
	@Override
	protected void doSetAttributeValue(Object o) {
		final Command cmd = (Command)o;
		getView().setScannerViewEventListener(new ScannerView.ScannerViewEventListener() {
			@Override
			public boolean onCodeScanned(String s) {
				if (s!=null)
				{
					if(s.equals(oldData))
						return false;
					oldData = s;
					cmd.Invoke(getView(), s);
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public Command get() {
		return null;
	}
}
