package de.fhconfig.android.library.annotations.specific;

public enum EventNames
{
	NONE("NONE"),
	OnItemClick("onItemClick");

	private String name;
	EventNames(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
