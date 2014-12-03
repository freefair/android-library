android-library
===============

Latest available Version: [ ![Download](https://api.bintray.com/packages/larsgrefer/maven/android-library/images/download.svg) ](https://bintray.com/larsgrefer/maven/android-library/_latestVersion)

##How to include:
```gradle
dependencies {
    ...
    compile 'de.larsgrefer:android-library:0.1'
    ...
}
```
##Features:
- All material colors (via "@color/material_red_A200" )
- minSdk 7 (it´s based on AppCompat)
- Dependency injection with annotations:
```java
@XmlLayout(R.layout.my_awesome_layout)
@XmlMenu(R.menu.my_awesome_menu)
public class MyAwesomeActivty extends InjectionActionBarActivity{
	
	@XmlView(R.id.my_awesome_text_field_id)
	EditText myTextField;
	
	@XmlView
	Button my_awesome_button_id;
}
```
```java
@XmlLayout(R.layout.my_awesome_layout)
@XmlMenu(R.menu.my_awesome_menu)
public class MyAwesomeFragment extends InjectionFragment{
	
	@XmlView(R.id.my_awesome_text_field_id)
	EditText myTextField;
	
	@XmlView
	Button my_awesome_button_id;
}
```
