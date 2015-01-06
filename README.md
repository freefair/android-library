android-library
===============

by
<table>
	<tr>
		<td><a href="//github.com/Frisch12/" target="_blank"><img src="https://de.gravatar.com/userimage/63287007/45a67c3e3d367da058c3f3a8d61b3a5b.jpg" /></a></td>
		<td><a href="//github.com/larsgrefer/" target="_blank"><img src="http://0.gravatar.com/avatar/0bc5394f410f232a835c831656b2c542"/></a></td>
	</tr>
	<tr>
		<td>Dennis Fricke</td>
		<td>Lars Grefer</td>
	</tr>
</table>

Latest available Version: [ ![Download](https://api.bintray.com/packages/larsgrefer/maven/android-library/images/download.svg) ](https://bintray.com/larsgrefer/maven/android-library/_latestVersion)

##How to include:
<h6>Gradle:</h6>
```gradle
dependencies {
    ...
    compile 'de.larsgrefer:android-library:0.1'
    ...
}
```
##Features:
<h5>All material colors (via "@color/material_red_A200" )</h5>
<h5>minSdk 7 (it´s based on AppCompat)</h5>
<h5>Dependency injection with annotations:</h5>
<h6>Acitivy:</h6>
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
<h6>Fragment:</h6>
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
<h6>Non-UI Classes:</h6>
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
<pre>Copyright (c) 2014 Dennis Fricke (dennis-fricke.de) and Lars Grefer (larsgrefer.de)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.</pre>
