package de.fhconfig.android.binding;

public interface IReferenceObservableProvider {
	public IObservable<?> getReferenceObservable(int referenceId, String field);
}
