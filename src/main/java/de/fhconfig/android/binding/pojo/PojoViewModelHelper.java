package de.fhconfig.android.binding.pojo;

public class PojoViewModelHelper {
	PojoViewModelWrapper<?> mWrapper;

	void registerWrapper(PojoViewModelWrapper<?> wrapper) {
		mWrapper = wrapper;
	}

	void unregisterWrapper() {
		mWrapper = null;
	}

	PojoViewModelWrapper<?> getWrapper() {
		return mWrapper;
	}

	public void notifyPropertyChanged(String propertyName) {
		mWrapper.onPropertyChanged(propertyName);
	}
}
