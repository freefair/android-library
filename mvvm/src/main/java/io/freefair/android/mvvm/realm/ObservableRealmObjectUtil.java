package io.freefair.android.mvvm.realm;

import android.databinding.Observable;

import java.util.WeakHashMap;

import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmObject;

public class ObservableRealmObjectUtil {

    private static WeakHashMap<Observable.OnPropertyChangedCallback, RealmPropertyChangedListener> listenerStorage = new WeakHashMap<>();

    public static <T extends RealmModel & Observable> void addOnPropertyChangedCallback(T realmObject, Observable.OnPropertyChangedCallback onPropertyChangedCallback) {
        if(listenerStorage.containsKey(onPropertyChangedCallback)) {
            return;
        }
        RealmPropertyChangedListener<T> listener = new RealmPropertyChangedListener<>(onPropertyChangedCallback, realmObject);
        listenerStorage.put(onPropertyChangedCallback, listener);
        RealmObject.addChangeListener(realmObject, listener);
    }

    public static <T extends RealmModel & Observable> void removeOnPropertyChangedCallback(T realmObject, Observable.OnPropertyChangedCallback onPropertyChangedCallback) {
        RealmPropertyChangedListener listener = listenerStorage.get(onPropertyChangedCallback);
        RealmObject.removeChangeListener(realmObject, listener);
        listenerStorage.remove(onPropertyChangedCallback);
    }

    public static class RealmPropertyChangedListener<T extends RealmModel & Observable> implements RealmChangeListener<T> {

        private final Observable.OnPropertyChangedCallback onPropertyChangedCallback;
        private final T obj;

        public RealmPropertyChangedListener(Observable.OnPropertyChangedCallback onPropertyChangedCallback, T obj) {
            this.onPropertyChangedCallback = onPropertyChangedCallback;
            this.obj = obj;
        }

        @Override
        public void onChange(T element) {
            onPropertyChangedCallback.onPropertyChanged(element, 0);
        }
    }
}
