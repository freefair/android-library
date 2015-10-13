package io.freefair.android.easing;

public interface Easing<TType>
{
	TType calculate(double time, TType start, TType delta, double totalTime);
}
