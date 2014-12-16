package gueei.binding.viewAttributes.view;

import android.view.MotionEvent;
import android.view.View;
import gueei.binding.BindingType;
import gueei.binding.ViewAttribute;

public class DisallowInterceptAttribute extends ViewAttribute<View, Boolean> {
	public DisallowInterceptAttribute(View view) {
		super(Boolean.class, view, "disallowIntercept");
	}

	@Override
	protected void doSetAttributeValue(final Object newValue) {
		View host = getHost();
		host.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
	}

	@Override
	public Boolean get() {
		return null;
	}

	@Override
	protected BindingType AcceptThisTypeAs(Class<?> type) {
		return BindingType.OneWay;
	}
}
