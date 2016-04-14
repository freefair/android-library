-keepattributes Exceptions,Signature,*Annotation*
-keepclassmembers class * {
    @io.freefair.android.mvvm.annotations.** *;
}

-keepclassmembers class * extends android.databinding.ViewDataBinding {
    public static * inflate(android.view.LayoutInflater, android.view.ViewGroup, boolean);
    public boolean setVariable(int, Object);
}