package de.fhconfig.android.binding.v30;

import android.util.Log;

import de.fhconfig.android.binding.kernel.ISyntaxNameResolver;

public class SyntaxNameResolverV30 implements ISyntaxNameResolver {
	@Override
	public Class getClass(String name) {
		try {
			return Class.forName("de.fhconfig.android.binding.converters." + name);
		} catch (ClassNotFoundException e) {
			Log.e("de.fhconfig.android.binding.v30.DefaultKernel", "Error while creating class", e);
		}
		return null;
	}

	@Override
	public boolean canHandle(String name) {
		return name.equals("GO_UP");
	}
}
