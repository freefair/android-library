android-library
===============
|master                                                                                                                                                                    |develop                                                                                                                                 |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------|
|[![Build Status](https://travis-ci.org/larsgrefer/android-library.svg?branch=master)](https://travis-ci.org/larsgrefer/android-library)                                   |[![Build Status](https://travis-ci.org/larsgrefer/android-library.svg?branch=develop)](https://travis-ci.org/larsgrefer/android-library)|
|[![Download](https://api.bintray.com/packages/larsgrefer/maven/android-library/images/download.svg) ](https://bintray.com/larsgrefer/maven/android-library/_latestVersion)|[oss.jfrog.org](http://oss.jfrog.org/artifactory/webapp/browserepo.html?pathId=oss-snapshot-local%3Ade%2Flarsgrefer%2Fandroid-library)  |

IMPORTANT
=========

Please use new feature data-binding implemented in Android-SDK since 28.05.2015 insteat until we've updated the library to use better implemented Google behaviour. You can find the documentation here: <a href="https://developer.android.com/tools/data-binding/guide.html">https://developer.android.com/tools/data-binding/guide.html</a>

##How to include:
<h6>Gradle:</h6>
```gradle
dependencies {
    ...
    compile 'de.larsgrefer:android-library:0.7.2'
    ...
}
```
##Features:
- All material colors (via ```@color/material_red_A200``` )
- minSdk 7 (it´s based on AppCompat)
- Dependency injection with annotations:
	- Acitivy: 
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
	- Fragment:
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
	- Non-UI Classes:
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
