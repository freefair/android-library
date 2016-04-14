package io.freefair.android.mvvm.realm;

import android.databinding.Observable;

import java.util.WeakHashMap;

import io.realm.RealmChangeListener;
import io.realm.RealmObject;

public class ObservableRealmObjectUtil {

    private static WeakHashMap<Observable.OnPropertyChangedCallback, RealmPropertyChangedListener> listenerStorage = new WeakHashMap<>();

    public static <T extends RealmObject & Observable> void addOnPropertyChangedCallback(T realmObject, Observable.OnPropertyChangedCallback onPropertyChangedCallback) {
        if(listenerStorage.containsKey(onPropertyChangedCallback)) {
            return;
        }
        RealmPropertyChangedListener<T> listener = new RealmPropertyChangedListener<>(onPropertyChangedCallback, realmObject);
        listenerStorage.put(onPropertyChangedCallback, listener);
        realmObject.addChangeListener(listener);
    }

    public static <T extends RealmObject & Observable> void removeOnPropertyChangedCallback(T realmObject, Observable.OnPropertyChangedCallback onPropertyChangedCallback) {
        RealmPropertyChangedListener listener = listenerStorage.get(onPropertyChangedCallback);
        realmObject.removeChangeListener(listener);
        listenerStorage.remove(onPropertyChangedCallback);
    }

    public static class RealmPropertyChangedListener<T extends RealmObject & Observable> implements RealmChangeListener {

        private final Observable.OnPropertyChangedCallback onPropertyChangedCallback;
        private final T obj;

        public RealmPropertyChangedListener(Observable.OnPropertyChangedCallback onPropertyChangedCallback, T obj) {
            this.onPropertyChangedCallback = onPropertyChangedCallback;
            this.obj = obj;
        }

        @Override
        public void onChange() {
            onPropertyChangedCallback.onPropertyChanged(obj, 0);
        }
    }
}
