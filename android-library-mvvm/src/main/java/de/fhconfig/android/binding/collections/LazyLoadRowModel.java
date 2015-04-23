package de.fhconfig.android.binding.collections;

import de.fhconfig.android.binding.IObservableCollection;

public abstract class LazyLoadRowModel implements ILazyLoadRowModel {
	protected boolean mapped = false;
	private boolean displaying = false;

	@Override
	public void display(IObservableCollection<?> collection, int index) {
		if (displaying) return;
		// if (!mapped) return;
		displaying = true;
		onDisplay(collection, index);
	}

	@Override
	public void hide(IObservableCollection<?> collection, int index) {
		if (!displaying) return;
		displaying = false;
		onHide(collection, index);
	}

	public abstract void onDisplay(IObservableCollection<?> collection, int index);

	public abstract void onHide(IObservableCollection<?> collection, int index);

	@Override
	public boolean isMapped() {
		return this.mapped;
	}

	@Override
	public void setMapped(boolean mapped) {
		this.mapped = mapped;
	}
}
