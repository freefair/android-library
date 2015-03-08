package de.fhconfig.android.binding.kernel;

import android.app.Application;

import de.fhconfig.android.binding.AttributeBinder;
import de.fhconfig.android.binding.ISyntaxResolver;
import de.fhconfig.android.binding.bindingProviders.AbsSpinnerViewProvider;
import de.fhconfig.android.binding.bindingProviders.AdapterViewProvider;
import de.fhconfig.android.binding.bindingProviders.AutoCompleteTextViewProvider;
import de.fhconfig.android.binding.bindingProviders.CompoundButtonProvider;
import de.fhconfig.android.binding.bindingProviders.ExpandableListViewProvider;
import de.fhconfig.android.binding.bindingProviders.GenericViewAttributeProvider;
import de.fhconfig.android.binding.bindingProviders.ImageViewProvider;
import de.fhconfig.android.binding.bindingProviders.ListViewProvider;
import de.fhconfig.android.binding.bindingProviders.ProgressBarProvider;
import de.fhconfig.android.binding.bindingProviders.RatingBarProvider;
import de.fhconfig.android.binding.bindingProviders.SeekBarProvider;
import de.fhconfig.android.binding.bindingProviders.TabHostProvider;
import de.fhconfig.android.binding.bindingProviders.TextViewProvider;
import de.fhconfig.android.binding.bindingProviders.ViewAnimatorProvider;
import de.fhconfig.android.binding.bindingProviders.ViewProvider;

public class DefaultKernel extends KernelBase {

	@Override
	protected void registerProviders(AttributeBinder attrBinder) {
		attrBinder.registerProvider(new TabHostProvider());
		attrBinder.registerProvider(new SeekBarProvider());
		attrBinder.registerProvider(new RatingBarProvider());
		attrBinder.registerProvider(new ProgressBarProvider());
		attrBinder.registerProvider(new ViewAnimatorProvider());
		attrBinder.registerProvider(new CompoundButtonProvider());
		attrBinder.registerProvider(new ImageViewProvider());
		attrBinder.registerProvider(new ExpandableListViewProvider());
		attrBinder.registerProvider(new AbsSpinnerViewProvider());
		attrBinder.registerProvider(new ListViewProvider());
		attrBinder.registerProvider(new AdapterViewProvider());
		attrBinder.registerProvider(new AutoCompleteTextViewProvider());
		attrBinder.registerProvider(new TextViewProvider());
		attrBinder.registerProvider(new ViewProvider());
		attrBinder.registerProvider(new GenericViewAttributeProvider());
	}

	@Override
	protected AttributeBinder createAttributeBinder(Application application) {
		return AttributeBinder.getInstance();
	}

	@Override
	protected ISyntaxResolver createSyntaxResolver(Application application) {
		return new DefaultSyntaxResolver();
	}
}
