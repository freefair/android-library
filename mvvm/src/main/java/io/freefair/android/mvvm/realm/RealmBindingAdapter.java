package io.freefair.android.mvvm.realm;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * @param <T> Type of the {@link RealmObject RealmObjects} to display
 * @param <B> Type of the {@link ViewDataBinding Binding} for the Items
 */
@SuppressWarnings("unused")
@UiThread
public class RealmBindingAdapter<T extends RealmObject, B extends ViewDataBinding> extends RealmBaseAdapter<T> {

    private int bindingVariableId;
    private Class<B> bindingClass;

    /**
     * @param context
     * @param realmResults The realmResults to display
     * @param automaticUpdate
     * @param bindingClass {@code MyItemLayoutBinding.class}
     * @param bindingVariableId {@code BR.item}
     */
    public RealmBindingAdapter(Context context, RealmResults<T> realmResults, boolean automaticUpdate, Class<B> bindingClass, int bindingVariableId) {
        super(context, realmResults, automaticUpdate);
        this.bindingClass = bindingClass;
        this.bindingVariableId = bindingVariableId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        B listItemBinding;

        if(convertView != null) {
            listItemBinding = DataBindingUtil.getBinding(convertView);

            if (listItemBinding == null || !bindingClass.isInstance(listItemBinding)) {
                listItemBinding = createBinding(parent);
                convertView = listItemBinding.getRoot();
            }
        } else {
            listItemBinding = createBinding(parent);
            convertView = listItemBinding.getRoot();
        }

        listItemBinding.setVariable(bindingVariableId, getItem(position));

        return convertView;
    }

    @SuppressWarnings("unchecked")
    private B createBinding(ViewGroup parent) {

        //inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot)
        try {
            Method inflate = bindingClass.getMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            return (B) inflate.invoke(null, inflater, parent, false);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException instanceof RuntimeException)
                throw (RuntimeException) targetException;
            else
                throw new RuntimeException(targetException);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
