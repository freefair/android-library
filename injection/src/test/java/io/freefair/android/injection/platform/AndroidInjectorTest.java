package io.freefair.android.injection.platform;

import android.app.Activity;
import android.content.Context;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by larsgrefer on 20.10.15.
 */
public class AndroidInjectorTest {

	@Test
	public void typeTest(){
		assertTrue(Context.class.isAssignableFrom(Activity.class));
	}

}