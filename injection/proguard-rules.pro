-keepattributes Signature,*Annotation*
-keepclassmembers class * {
    @io.freefair.android.injection.annotation.Inject* *;
}


-keeppackagenames io.freefair.**
-keepclassmembernames class * extends io.freefair.android.injection.ui.Injection* {
    *;
}