-dontpreverify
-allowaccessmodification

-obfuscationdictionary obf-dict.txt
-classobfuscationdictionary obf-dict.txt
-packageobfuscationdictionary obf-dict.txt

-overloadaggressively
-renamesourcefileattribute 'obf'
-repackageclasses 'me.hd.wexpt.obf'

-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    public static void check*(...);
    public static void throw*(...);
}
-assumenosideeffects class java.util.Objects {
    ** requireNonNull(...);
}

-keep class me.hd.wexpt.hook.HookEntry

-dontwarn java.lang.reflect.AnnotatedType
