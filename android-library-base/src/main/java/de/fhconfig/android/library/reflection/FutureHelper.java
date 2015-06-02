package de.fhconfig.android.library.reflection;

import java.util.concurrent.ExecutionException;

import java8.util.concurrent.CompletableFuture;
import java8.util.function.Supplier;

public class FutureHelper
{
	public static <TType> TType get(CompletableFuture<TType> f, Supplier<TType> supplier) {
		try {
			return f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return supplier.get();
	}
}
