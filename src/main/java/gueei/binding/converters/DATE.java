package gueei.binding.converters;

import java.text.SimpleDateFormat;
import java.util.Date;

import gueei.binding.Converter;
import gueei.binding.IObservable;

/**
 * Created by Administrator on 15.01.2015.
 */
public class DATE extends Converter<CharSequence> {
    public DATE(IObservable<?>[] dependents) {
        super(CharSequence.class, dependents);
    }

    @Override
    public CharSequence calculateValue(Object... objects) throws Exception {
        Object object = null;
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
        if(object instanceof Date)
        {
            return simpleDateFormat.format((Date) object);
        }
        else if (object instanceof Long)
        {
            return simpleDateFormat.format(new Date((Long) object));
        }
        return null;
    }
}
