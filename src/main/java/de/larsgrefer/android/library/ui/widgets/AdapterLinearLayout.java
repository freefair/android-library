package de.larsgrefer.android.library.ui.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;

import de.fhconfig.android.binding.IBindableView;
import de.fhconfig.android.binding.ViewAttribute;

/**
 * Created by Administrator on 27.01.2015.
 */
public class AdapterLinearLayout extends LinearLayout implements IBindableView<AdapterLinearLayout> {
    public AdapterLinearLayout(Context context) {
        super(context);
    }

    public AdapterLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(11)
    public AdapterLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public AdapterLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public ViewAttribute<? extends View, ?> createViewAttribute(String s) {
        if("adapter".equals(s))
            return new AdapterViewAttribute(this);
        return null;
    }

    public void setAdapter(Adapter adapter) {
        this.removeAllViews();
        for(int i = 0; i < adapter.getCount(); i++)
        {
            this.addView(adapter.getView(i, null, this));
        }
    }

    private class AdapterViewAttribute extends ViewAttribute<AdapterLinearLayout, Adapter> {
        public AdapterViewAttribute(AdapterLinearLayout adapterLinearLayout) {
            super(Adapter.class, adapterLinearLayout, "adapter");
        }

        @Override
        protected void doSetAttributeValue(Object newValue) {
            if(getView() == null) return;
            if(newValue instanceof Adapter)
            {
                getView().setAdapter((Adapter) newValue);
            }
        }

        @Override
        public Adapter get() {
            return null;
        }
    }
}
