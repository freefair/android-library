android-library [![Release](https://img.shields.io/github/release/freefair/android-library.svg?label=JitPack)](https://jitpack.io/#freefair/android-library)
===============
##Structure

The library consist of different modules:
- [injection](injection)
- [mvvm](mvvm)
- [orm](orm)
- [playground](playground)

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
    compile 'io.freefair:android-library:0.9.0'
}
```
####Only one module
```gradle
dependencies {
     compile 'io.freefair.android-library:MODULE-NAME:0.9.0'
}
```
