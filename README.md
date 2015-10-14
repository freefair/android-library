android-library
===============
##Structure

The library consist of different modules:
- [appcompat-preference](appcompat-preference)
- [injection](injection)
- [mvvm](mvvm)
- [orm](orm)
- [util](util)
- [playground](playground)
- [material-colors](material-colors)
- [holo-colors](holo-colors)

##How to include:
###Gradle
```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```
####All at once
```gradle
dependencies {
    compile 'com.github.freefair:android-library:0.9.0'
}
```
####Only one module
```gradle
dependencies {
     compile 'com.github.freefair.android-library:MODULE-NAME:0.9.0'
}
```
