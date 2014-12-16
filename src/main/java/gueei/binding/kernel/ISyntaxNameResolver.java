package gueei.binding.kernel;

public interface ISyntaxNameResolver
{
	Class getClass(String name);
	boolean canHandle(String name);
}
