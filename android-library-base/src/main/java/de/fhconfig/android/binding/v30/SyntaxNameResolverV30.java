package de.fhconfig.android.binding.v30;

import de.fhconfig.android.binding.kernel.ISyntaxNameResolver;
import de.fhconfig.android.library.Logger;

public class SyntaxNameResolverV30 implements ISyntaxNameResolver {
	@Override
	public Class getClass(String name) {
		try {
			return Class.forName("de.fhconfig.android.binding.converters." + name);
		} catch (ClassNotFoundException e) {
			Logger.error(this, "Error while creating class", e);
		}
		return null;
	}

	@Override
	public boolean canHandle(String name) {
		return name.equals("GO_UP");
	}
}
