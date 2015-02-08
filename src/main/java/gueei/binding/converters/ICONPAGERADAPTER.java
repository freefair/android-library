package gueei.binding.converters;

import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

import de.larsgrefer.android.library.ui.PagerSlidingTabStrip;
import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.BindingLog;
import de.fhconfig.android.binding.CollectionChangedEventArg;
import de.fhconfig.android.binding.CollectionObserver;
import de.fhconfig.android.binding.Converter;
import de.fhconfig.android.binding.DynamicObject;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.IObservableCollection;
import de.fhconfig.android.binding.v30.viewAttributes.adapterView.viewPager.PagerAdapterObservable;
import de.fhconfig.android.binding.viewAttributes.templates.Layout;

/**
 * PAGERADAPTER accepts unlimited number of DynamicObjects
 * It will return a PagerAdapter
 *
 * @usage params
 *
 * @arg params DynamicObject
 *
 * @item template gueei.binding.viewAttributes.templates.Layout
 * @item source ObservableCollection
 * @item @optional width Float
 * @item @optional widthField String When Set, ignore width
 * @item @optional title String
 * @item @optional titleField String When Set, ignore title
 *
 * @return PagerAdapterObservable
 *
 * @author andy
 *
 */
public class ICONPAGERADAPTER extends Converter<PagerAdapterObservable> {
    public ICONPAGERADAPTER(IObservable<?>[] dependents) {
        super(PagerAdapterObservable.class, dependents);
    }

    @Override
    public PagerAdapterObservable calculateValue(Object... args) throws Exception {
        return new ObsPagerAdapter((DynamicObject)args[0]);
    }

    private class ObsPagerAdapter extends PagerAdapterObservable implements CollectionObserver, PagerSlidingTabStrip.IconTabProvider {
        @Override
        public CharSequence getPageTitle(int position) {
            if (dobj.observableExists("titleField")){
                String titleField = dobj.tryGetObservableValue("titleField", null);
                return Binder.getSyntaxResolver().tryEvaluateValue(getContext(), titleField, col.getItem(position), "");
            } else
                return dobj.tryGetObservableValue("title", "");
        }

        @Override
        public float getPageWidth(int position) {
            if (dobj.observableExists("widthField")){
                String widthField = dobj.tryGetObservableValue("widthField", null);
                return Binder.getSyntaxResolver().tryEvaluateValue(getContext(), widthField, col.getItem(position), 1.0f);
            }else
                return dobj.tryGetObservableValue("width", 1.0f);
        }

        private final DynamicObject dobj;
        private final IObservableCollection<?> col;

        public ObsPagerAdapter(DynamicObject obj){
            dobj = obj;
            try {
                col = (IObservableCollection<?>)obj.getObservableByName("source").get();
                col.subscribe(this);
            } catch (Exception e) {
                BindingLog.exception("PAGERADAPTER.ObsPagerAdapter.Constructor", e);
                throw new RuntimeException();
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            try {
                Layout layout;
                layout = (Layout)dobj.getObservableByName("template").get();
                Binder.InflateResult result =
                        Binder.inflateView(container.getContext(), layout.getDefaultLayoutId(), container, false);
                View root =
                        Binder.bindView(container.getContext(), result, col.getItem(position));
                container.addView(root);
                return root;
            } catch (Exception e) {
                BindingLog.exception("PAGERADAPTER", e);
                return null;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
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
            for(int i=0; i<col.size(); i++){
                if (object.equals(col.getItem(i))) return i;
            }
            return POSITION_NONE;
        }

        @Override
        public int getPageIconResId(int position) {
            if (dobj.observableExists("iconField")){
                String titleField = dobj.tryGetObservableValue("iconField", null);
                return Binder.getSyntaxResolver().tryEvaluateValue(getContext(), titleField, col.getItem(position), 0);
            } else
                return dobj.tryGetObservableValue("icon", 0);
        }
    }
}
