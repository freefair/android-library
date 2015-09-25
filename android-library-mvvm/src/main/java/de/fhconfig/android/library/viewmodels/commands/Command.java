package de.fhconfig.android.library.viewmodels.commands;

public abstract class Command
{
	public static final Command empty = new Command() { @Override public void execute() { } };

	public abstract void execute();
}
