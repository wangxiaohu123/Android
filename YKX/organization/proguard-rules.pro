# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/wangxiaohu/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#-libraryjars libs/alipaySdk20170407.jar
#
#-keep class com.alipay.android.app.IAlixPay{*;}
#-keep class com.alipay.android.app.IAlixPay$Stub{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
#-keep class com.alipay.sdk.app.PayTask{ public *;}
#-keep class com.alipay.sdk.app.AuthTask{ public *;}

-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**

-keepattributes InnerClasses
-dontoptimize
-keepattributes EnclosingMethod
#签名打包问题 虽然不是很影响，一片红色也很难看
-keepattributes InnerClasses-dontoptimize
#优化显示配置 It's probable that incompatible optimizations are applied (that probably causes the last line of the error log)
-optimizations optimization_filter

