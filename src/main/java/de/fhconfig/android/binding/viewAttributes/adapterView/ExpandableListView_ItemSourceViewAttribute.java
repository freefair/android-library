package de.fhconfig.android.binding.viewAttributes.adapterView;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.BindingLog;
import de.fhconfig.android.binding.BindingType;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.Observer;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.collections.ExpandableCollectionAdapter;
import de.fhconfig.android.binding.viewAttributes.templates.Layout;

import java.util.Collection;

import android.widget.Adapter;
import android.widget.ExpandableListView;

/**
 * Item Source of Expandable List Views
 * 
 * @name itemSource
 * @widget ExpandableListView
 * @type Object
 * @accepts	Object
 * @category expandable-list
 * @related 
 * 
 * @author andy
 */
public class ExpandableListView_ItemSourceViewAttribute 
	extends ViewAttribute<ExpandableListView, Object> {
	
	Layout template, childItemTemplate;
	String childItemSource;
	
	private Observer attrObserver = new Observer(){
		public void onPropertyChanged(IObservable<?> prop,
				Collection<Object> initiators) {
			createAdapter();
		}
	};
	
	public  ExpandableListView_ItemSourceViewAttribute 
		(ExpandableListView view) {
		super(Object.class,view, "itemSource");
		try{
			ViewAttribute<?,?> attrItemTemplate = Binder.getAttributeForView(getView(), "itemTemplate");
			ViewAttribute<?,?> attrChildItemTemplate = Binder.getAttributeForView(getView(), "childItemTemplate");
			ViewAttribute<?,?> attrChildItemSource = Binder.getAttributeForView(getView(), "childItemSource");
			attrItemTemplate.subscribe(attrObserver);
			attrChildItemTemplate.subscribe(attrObserver);
			attrChildItemSource.subscribe(attrObserver);
		}catch(Exception e){
			BindingLog.exception("ExpandableListView_ItemSourceViewAttribute", e);
		}
		createAdapter();
	}

	private void getAttributeValue() throws Exception {
		template = ((Layout)Binder.getAttributeForView(getView(), "itemTemplate").get());
		childItemTemplate = ((Layout)Binder.getAttributeForView(getView(), "childItemTemplate").get());
		childItemSource = (String)(Binder.getAttributeForView(getView(), "childItemSource").get());
	}

	private Object mValue;
	
	@Override
	protected void doSetAttributeValue(Object newValue) {
		mValue = newValue;
		if(getView()==null) return;
		createAdapter();
	}

	private void createAdapter(){
		if (mValue==null) return;

		try {
			getAttributeValue();
			if ((template==null) || (childItemTemplate==null) || (childItemSource==null))
				return;
			
			Adapter groupAdapter = 
				de.fhconfig.android.binding.collections.Utility.getSimpleAdapter
					(getView().getContext(), mValue, template, template, null);
			ExpandableCollectionAdapter adapter = new ExpandableCollectionAdapter
				(getView().getContext(), groupAdapter, childItemSource, childItemTemplate);
			getView().setAdapter(adapter);
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	@Override
	public Object get() {
		// Set only attribute
		return null;
	}

	@Override
	protected BindingType AcceptThisTypeAs(Class<?> type) {
		return BindingType.OneWay;
	}
}
