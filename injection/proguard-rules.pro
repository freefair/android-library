# Needed for Reflection
-keepattributes Signature,*Annotation*
-keepclassmembers class * {
    @io.freefair.android.injection.annotation.Inject* *;
}
-keepclassmembernames class * extends io.freefair.android.injection.ui.Injection* {
    *;
}