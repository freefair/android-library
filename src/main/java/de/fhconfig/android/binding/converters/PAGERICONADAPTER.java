package de.fhconfig.android.binding.converters;

import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

import de.fhconfig.android.binding.*;
import de.fhconfig.android.binding.Binder.InflateResult;
import de.fhconfig.android.binding.v30.viewAttributes.adapterView.viewPager.PagerAdapterObservable;
import de.fhconfig.android.binding.viewAttributes.templates.Layout;

/**
 * PAGERADAPTER accepts unlimited number of DynamicObjects
 * It will return a PagerAdapter
 *
 * @author andy
 * @usage params
 * @arg params DynamicObject
 * @item template de.fhconfig.android.binding.viewAttributes.templates.Layout
 * @item source ObservableCollection
 * @item @optional width Float
 * @item @optional widthField String When Set, ignore width
 * @item @optional title String
 * @item @optional titleField String When Set, ignore title
 * @return PagerAdapterObservable
 */
public class PAGERICONADAPTER extends Converter<PagerAdapterObservable> {
	public PAGERICONADAPTER(IObservable<?>[] dependents) {
		super(PagerAdapterObservable.class, dependents);
	}

	@Override
	public PagerAdapterObservable calculateValue(Object... args) throws Exception {
		return new ObsPagerIconAdapter((DynamicObject) args[0]);
	}

	private class ObsPagerIconAdapter extends PagerAdapterObservable implements CollectionObserver {


		private final DynamicObject dobj;
		private final IObservableCollection<?> col;

		public ObsPagerIconAdapter(DynamicObject obj) {
			dobj = obj;
			try {
				col = (IObservableCollection<?>) obj.getObservableByName("source").get();
				if (col == null) {
					BindingLog.warning("PAGERICONADAPTER.ObsPagerAdaopter.Constructor", "source is null");
					return;
				}
				col.subscribe(this);
			} catch (Exception e) {
				BindingLog.exception("PAGERICONADAPTER.ObsPagerIconAdapter.Constructor", e);
				throw new RuntimeException();
			}
		}

		@Override
		public CharSequence getPageTitle(int position) {
			if (dobj.observableExists("iconField")) {
				String titleField = dobj.tryGetObservableValue("iconField", null);
				return Binder.getSyntaxResolver().tryEvaluateValue(getContext(), titleField, col.getItem(position), "");
			} else
				return dobj.tryGetObservableValue("icon", "");
		}

		@Override
		public float getPageWidth(int position) {
			if (dobj.observableExists("widthField")) {
				String widthField = dobj.tryGetObservableValue("widthField", null);
				return Binder.getSyntaxResolver().tryEvaluateValue(getContext(), widthField, col.getItem(position), 1.0f);
			} else
				return dobj.tryGetObservableValue("width", 1.0f);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			try {
				Layout layout;
				layout = (Layout) dobj.getObservableByName("template").get();
				InflateResult result =
						Binder.inflateView(container.getContext(), layout.getDefaultLayoutId(), container, false);
				View root =
						Binder.bindView(container.getContext(), result, col.getItem(position));
				container.addView(root);
				return root;
			} catch (Exception e) {
				BindingLog.exception("PAGERICONADAPTER", e);
				return null;
			}
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			return col.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0.equals(arg1);
		}

		@Override
		public void onCollectionChanged(IObservableCollection<?> collection,
		                                CollectionChangedEventArg args, Collection<Object> initiators) {
			this.notifyDataSetChanged();
		}

		@Override
		public int getItemPosition(Object object) {

			// TODO: This is quite inefficient, need to improve this
			for (int i = 0; i < col.size(); i++) {
				if (object.equals(col.getItem(i))) return i;
			}
			return POSITION_NONE;
		}
	}
}
