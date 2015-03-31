package de.fhconfig.android.binding.converters;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.fhconfig.android.binding.Converter;
import de.fhconfig.android.binding.IObservable;

public class DATE extends Converter<CharSequence> {
    public DATE(IObservable<?>[] dependents) {
        super(CharSequence.class, dependents);
    }

    @Override
    public CharSequence calculateValue(Object... objects) throws Exception {
        Object object;
        if(objects.length > 0)
            object = objects[0];
        else
            object = new Date();


        String format = "dd.MM.yyyy HH:mm";

        if(objects.length > 1 && objects[1] instanceof String)
        {
            format = (String)objects[1];
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		Date date = null;
        if(object instanceof Date)
        {
	        date = (Date) object;
        }
        else if (object instanceof Long)
        {
	        date = new Date((Long) object);
        }
	    if(date != null) {
		    return simpleDateFormat.format(date);
	    }
        return null;
    }
}
