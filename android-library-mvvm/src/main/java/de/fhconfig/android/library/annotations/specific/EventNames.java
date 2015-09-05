package de.fhconfig.android.library.annotations.specific;

public enum EventNames
{
	NONE("NONE"),
	OnClick("onClick"),
	OnItemClick("onItemClick");

	private String name;
	EventNames(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
