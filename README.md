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
<h3>All material colors (via "@color/material_red_A200" )</h3>
<h3>minSdk 7 (it´s based on AppCompat)</h3>
<h3>Dependency injection with annotations:</h3>
<h4>Acitivy:</h4>
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
<h4>Fragment:</h4>
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
<h4>Non-UI Classes:</h4>
```java
public class MyModule extends RoboModule {
	@Override
    public void configure(RoboBuilder builder)
    {
        builder.registerType(MySuperImportantService.class).to(IMySuperImportantService.class);
    }
}

public class MyApplication extends InjectionApplication {
	@Override
	public void onCreate() {
        addModule(new MyModule());
        super.onCreate();
    }
}

public class MyActivity extends InjectionActivity {
	@Inject
	private IMySuperImportantService mService;
}
```

## Copyright
Copyright (C) 2014 Dennis Fricke (dennis-fricke.de) and Lars Grefer (larsgrefer.de)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.